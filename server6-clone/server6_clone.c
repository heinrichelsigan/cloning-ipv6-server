#define _GNU_SOURCE 

#include "socketv6.h"
#include <err.h> 
#include <fcntl.h>
#include <sched.h>
#include <signal.h>
#include <stdint.h>
#include <stdlib.h> 
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/utsname.h>
#include <sys/wait.h>
#include <unistd.h>
#include <arpa/inet.h>

/* defining global variables */
char *progname = NULL;          // program name will later be set to argv[0]
char outmsg[MSGBUFLEN]; 
int c_sd = -1;                  // c_sd client socket descriptor

/* 
 * usemsg:
 *  msg ...     message to printf
 *  exitcode ...        exit code
 *  noexit ... if       noexit == 1 program will not print extra usage and resume by not exiting
 *
 * usemsg err and usage in one functions.
 * prints error message (if not NULL) to stderr in any case 
 * if noexit == 1 program socket server continues execution 
 */
void usemsg(char *msg, int exitcode, short noexit)
{
        if (msg != NULL)
                (void) fprintf(stderr, "%s\n", msg);
        
        if (noexit)
                return;
        
        fprintf(stderr, "usage: %s [port-number]\n", progname);
        
        exit(exitcode);
}


/* 
 * childfunc:
 *    arg ... void pointer to an array of any type
 *    returns childs exit code (exit status of child process / thread 
 *
 * childfunc needed by clone(2) processes connected client socket request
 */
static int childfunc(void *arg) 
{
        int exitcode = 0;
        int bytesum = 0;
        
        if (c_sd > 0) 
        {
                bytesum = handle_client(c_sd); /* received bytes got from handle client socket */
        
                sprintf(outmsg, "%s:\tclient request handled, closing now client socket descriptor %d after sending/receiving %d bytes total.", progname, c_sd, bytesum);
                usemsg(outmsg, 0, 1);
        
                close(c_sd); /* close c_sd client socket descriptor */       
                usemsg("closed client socket descriptor now, exiting in 2 seconds", 0, 1);
        }
        else 
        {
                exitcode = -1;
                sprintf(outmsg, "%s:\tclient socket descriptor c_sd returns -1; client socket is'nt availible anymore!\nclosing cloned child in 2 sec.",
                                                progname);
                usemsg(outmsg, 0, 1);
        }

        sleep(1); /* Keep child thread for 1s alive, before extting child thread. */                        

        return exitcode;
}

/*
 * handle_client:
 *  csd ... client socket descriptor 
 *  returns total processed MSGBUFLEN (mostly 8192) bytes blocks
 * handles client socket connect and send & receive data *
 */
int handle_client(int csd)
{
        char buf[MSGBUFLEN];
        char *lastpos;
        int rsize;
        int bytestotal = 0;

        while (c_sd > 0 && bytestotal <= 64)
        {
                for (int ic=0; ic<MSGBUFLEN; buf[ic++] = '\0');
                rsize = recv(c_sd, buf, MSGBUFLEN, 0); 
                if (rsize == 0) 
                { 
                        break; 
                } 
                send(c_sd, buf, strlen(buf), 0); 
                bytestotal += strlen(buf);
        }
        
        return bytestotal;
}


/*
 * main:
 *  argc ... argument counter
 *  argv ... array of arguments
 *  returns     program exit code
 * main entry point for server6_clone program
 */
int main(int argc, char *argv[])
{
        /* variables needed for ipv6 server sockets */                   
        int s_sd;                       /* s_sd server socket descriptor */
        struct sockaddr_in6 addr;                       /* ipv6 server socket addr */
        uint16_t tcp6port = TCPv6_PORT;              /* tcp v6 port default to TCPv6_PORT 7777 */
        int reuseaddr = 1;    
        /* pid needed for both fork() and clone(...) */
        pid_t pid;                          
        /* variables needed for stack and cloning */
        char *stack, *stacktop;                              
        off_t offset, pa_offset; 
        long pagesze_offet;
        struct utsname uts;
        
        progname = argv[0];                             
        offset = (off_t)MMAP_OFFSET;
        pagesze_offet = sysconf(_SC_PAGE_SIZE) - 1;
        pa_offset = offset & ~(sysconf(_SC_PAGE_SIZE) - 1);
        
        if (argc > 1)
                if ((tcp6port = (uint16_t)atol(argv[1])) < 2)
                        tcp6port = TCPv6_PORT;
        
        sprintf(outmsg, "setting server listening tcpv6 port: %d", (int) tcp6port);
        usemsg(outmsg, 0, 1);

        // create socket
        s_sd = socket(AF_INET6, SOCK_STREAM, 0);
        setsockopt(s_sd, SOL_SOCKET, SO_REUSEADDR, &reuseaddr, sizeof(reuseaddr));

        addr.sin6_family = AF_INET6; 
        addr.sin6_port = htons(tcp6port);
        addr.sin6_addr =  in6addr_any;

        // bind socket addr
        bind(s_sd, (struct sockaddr *)&addr, sizeof(addr));
        // listen on socket, max paralell connections 10
        listen(s_sd, 10);                                                                                       
                
        /* Allocate memory to be used for the stack of the child. */
        stack = mmap(NULL, STACKSZE, PROT_READ | PROT_WRITE,
                                MAP_PRIVATE | MAP_ANONYMOUS | MAP_STACK, -1, 0);

        if (stack == MAP_FAILED) 
        {               
                sprintf(outmsg, "mmap(NULL, %d, PROT_READ|PROT_WRITE, MAP_PRIVATE|MAP_ANONYMOUS|MAP_SHARED|MAP_STACK, -1, 0)  failed",
                                        STACKSZE);
                close(s_sd);                                            /* close server socket descriptor     */
                usemsg(outmsg, EXIT_FAILURE, 0);                        /* exit with usemsg(outmsg, EXIT_FAILURE, noExit = 0)   */
        }
        
        stacktop = stack + STACKSZE;                                    /* Assume stack grows downward */      
                
        while (1) // server runs forever
        {
                /*
                 * // unmap stack 
                 *
                 * munmap(stack, STACKSZE);
                 *
                 */
                
                c_sd = accept(s_sd, NULL, NULL);        // accept client connections to socket
                                        
                /*
                 * // Allocate stack again
                 *
                 * stack = mmap(NULL, STACKSZE, PROT_READ | PROT_WRITE, 
                 *                 MAP_PRIVATE | MAP_ANONYMOUS | MAP_SHARED | MAP_FIXED | MAP_STACK | MAP_SYNC, c, pa_offset);
                 * stack = mmap(NULL, STACKSZE, PROT_READ | PROT_WRITE,         
                 *                 MAP_PRIVATE | MAP_ANONYMOUS | MAP_SHARED | MAP_FIXED | MAP_STACK | MAP_SYNC, -1, 0);
                 *
                 * if (stack == MAP_FAILED) 
                 * {
                 *       sprintf(outmsg, "mmap(NULL, %d, PROT_READ|PROT_WRITE, MAP_PRIVATE|MAP_ANONYMOUS|MAP_SHARED|MAP_STACK, %d, %ld) failed",
                 *                                              STACKSZE, -1, 0);
                 *      close(s);                                               
                 *      close(c);                                                               
                 *      usemsg(outmsg, EXIT_FAILURE, 0);
                 * 
                 * else
                 *      stacktop = stack + STACKSZE;                    
                 *
                 */
         
                 // execute clone instead of pid=fork()
                 pid = clone(childfunc, stacktop, CLONE_NEWUTS | SIGCHLD, argv[0]);
                 if (pid == -1)
                 {
                         usemsg("cannot clone(2), trying to fork(2)", 0, 1);
                         pid = fork();
                         if (pid == -1)
                         {
                                 close(s_sd);  /* close server socket descriptor */
                                 close(c_sd);  /* close client socket descriptor */
                                 munmap(stack, STACKSZE); /* unmap program stack */
                                 usemsg("clone(2) and fork(2) failed and returned -1. Can neither clone(2) nor fork(2) process!",
                                         EXIT_FAILURE, 0);
                         }
                         else if (pid == 0)
                         {
                                 close(s_sd);
                                 int exitchild = childfunc(argv[0]);
                                 exit(exitchild);
                         }
                         else
                         {
                                 close(c_sd);
                         }

                 }


                sprintf(outmsg, "\toffset: %ld,\tpagesze_offet: %ld,\tpa_offset: %ld,\nsockets:\ts_sd=%d,\tc_sd=%d\nchild pid: %ld",
                                                (off_t) offset, (long) pagesze_offet, (off_t) pa_offset, s_sd, c_sd, (intmax_t) pid);
                usemsg(outmsg, 0, 1);

                sleep(1);                        /* sleep 1s to not permanently occupy scheduler */


                if (waitpid(pid, NULL, 0) == -1)  /* Wait for child */
                {
                        sprintf(outmsg, "%s:\twaitpid(%ld, NULL, 0) returned -1.\nExiting -1", progname, (intmax_t) pid);
                        
                        close(s_sd);                                 /* close server socket descriptor */
                        close(c_sd);                                 /* close client socket descriptor */
                        munmap(stack, STACKSZE);                     /* unmap program stack */
                        
                        usemsg(outmsg, EXIT_FAILURE, 0);
                }
            
        }

}
