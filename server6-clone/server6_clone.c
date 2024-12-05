#define _GNU_SOURCE 

#include <err.h> 
#include <fcntl.h>
#include <sched.h>
#include <signal.h>
#include <stdint.h>
#include <stdio.h> 
#include <stdlib.h> 
#include <string.h> 
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/utsname.h>
#include <sys/wait.h>
#include <unistd.h>
#include <arpa/inet.h>

#define TCPv6_PORT	7777
#define STACKSZE 	(1024 * 1024)    /* Stack size for cloned child */
#define NMAP_OFFSET	1024
#define RECEIVE_BUFFER 8192

/* defining global variables */
char progname[128], outmsg[4096];	// program name argv[0] and outmsg
int c = -1;							// client socket descriptor

/* 
 * usage(...) prints out a error or info message and might close and exit program with an errorcode or success status code.
 * char *msg 		message to printf
 * int exitCode 	exit code
 * short noExit		if (noExit == 1) program will not print extra usage and resume by not exiting
 */
void usage(char *msg, int exitCode, short noExit)
{
	if (msg != NULL)
		(void) fprintf(stderr, "%s\n", msg);
	
	if (noExit)
		return;
	
	fprintf(stderr, "usage: %s [port-number]\n", progname);
	
	exit(EXIT_SUCCESS);
}


/* 
 * childFunc 	...		start function for cloned child 
 * void *arg 	...		void pointer to any array 
 * 
 * return int childExitCode for exit status of child thread 
 */
static int childFunc(void *arg) 
{
	int childExitCode = 0;
	int  bytesum = 0;
	
	if (c > 0) 
	{
		bytesum = handle_client(c); 	/* received bytes got from handle client socket */
	
		sprintf(outmsg, "%s:\tclient socket descriptor %d send/received %d bytes total.", progname, c, bytesum);
		usage(outmsg, 0, 1);
	
		close(c);							/* close client socket descriptor */	
		usage("closed client socket descriptor now, exiting in 2 seconds", 0, 1);
	}
	else 
	{
		childExitCode = -1;
		sprintf(outmsg, "%s:\tclient socket descriptor returns -1; client socket is'nt availible anymore!\nclosing cloned child in 2 sec.",
						progname);
		usage(outmsg, 0, 1);
	}

	sleep(2);							/* Keep child posix thread for 2s alive, before extting child thread. */			

	return childExitCode;
}

/*
* 
* handle_client 	...		handles client socket connect and send & receive data *
* int csd 			...		client socket descriptor 
* 
* return int number of total processed RECEIVE_BUFFER (mostly 8192) bytes blocks
*/
int handle_client(int csd)
{
	char buf[RECEIVE_BUFFER];
	char *lastpos;
	int rsize;
	int bytestotal = 0;

	while (bytestotal <= 64)
	{
		rsize = recv(c, buf, RECEIVE_BUFFER, 0); 
		if (rsize == 0) 
		{ 
			break; 
		} 
		lastpos = strchr(buf, '\n'); 
		send(c, buf, lastpos+1-buf, 0); 
		bytestotal++;
	}
	
  return bytestotal;
}


/*
 * main 		... 	main start for server6_clone program
 * int argc 	...		argument counter
 * char *argv[]	...		array of arguments
 *
 * return  		... 	program exit code
 */
int main(int argc, char *argv[])
{
	char 				        *stack, *stackTop;  				/* Start & End of stack buffer */
	int 				        reuseaddr = 1;
	int                 s;
	pid_t 			        pid;	
	off_t 			        offset, pa_offset; 
	long 				        pagesze_offet;
	struct utsname  	  uts;
	struct sockaddr_in6 addr;
	uint16_t			      tcp6port = TCPv6_PORT;				/* tcp v6 port default to TCPv6_PORT 7777 */
	
	strcpy(progname, argv[0]); 								/* progname = argv[0] */
	offset = (off_t)NMAP_OFFSET;
	pagesze_offet = sysconf(_SC_PAGE_SIZE) - 1;
	pa_offset = offset & ~(sysconf(_SC_PAGE_SIZE) - 1);
		
	
	if (argc > 1)
	{
		tcp6port = (uint16_t)atol(argv[1]);
		sprintf(outmsg, "received arguments: argv[1} = %s,\nsetting server tcpv6 port: %d", argv[1], (int) tcp6port);
	}
	else
		sprintf(outmsg, "Socket port number for IPv6 address not specified in arguments...\ndefault port: %d", (int) tcp6port);
	
	usage(outmsg, 0, 1);

	s = socket(AF_INET6, SOCK_STREAM, 0);
	setsockopt(s, SOL_SOCKET, SO_REUSEADDR, &reuseaddr, sizeof(reuseaddr));

	addr.sin6_family = AF_INET6; 
	addr.sin6_port = htons(tcp6port);
	addr.sin6_addr =  in6addr_any;

	bind(s, (struct sockaddr *)&addr, sizeof(addr));
	listen(s, 5);											/* listen on port */
		
	/* Allocate memory to be used for the stack of the child. */
	stack = mmap(NULL, STACKSZE, PROT_READ | PROT_WRITE,
				MAP_PRIVATE | MAP_ANONYMOUS | MAP_STACK, -1, 0);

	if (stack == MAP_FAILED) 
	{		
		sprintf(outmsg, "mmap(NULL, %d, PROT_READ|PROT_WRITE, MAP_PRIVATE|MAP_ANONYMOUS|MAP_SHARED|MAP_STACK, -1, 0)  failed",
					STACKSZE);
		close(s); 									/* close server socket									*/
		usage(outmsg, EXIT_FAILURE, 0);				/* exit with usage(outmsg, EXIT_FAILURE, noExit = 0) 	*/
	}
	
	stackTop = stack + STACKSZE;  					/* Assume stack grows downward */
		
	while (1) 
	{
		/*
		 * // unmap stack 
		 *
		 * munmap(stack, STACKSZE);
		 *
		 */
		
		c = accept(s, NULL, NULL);		
		        
		
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
		 * 	 sprintf(outmsg, "mmap(NULL, %d, PROT_READ|PROT_WRITE, MAP_PRIVATE|MAP_ANONYMOUS|MAP_SHARED|MAP_STACK, %d, %ld) failed",
		 *					        STACKSZE, -1, 0);
		 *	close(s); 						
		 *	close(c); 								
		 *	usage(outmsg, EXIT_FAILURE, 0);
		 * }
		 * else
		 *  	stackTop = stack + STACKSZE;  			
	     *
		 */
	 
		pid = clone(childFunc, stackTop, CLONE_NEWUTS | SIGCHLD, argv[1]);
	
		if (pid == -1) 
		{
			close(s); 								/* close server socket descriptor */
			close(c); 								/* close client socket descriptor */
			munmap(stack, STACKSZE); 				/* unmap program stack */
			usage("clone(childFunc, stackTop, CLONE_NEWUTS | SIGCHLD, argv[1])\t returned -1. Cannot clone process!", 
					EXIT_FAILURE, 0);
		}

		sprintf(outmsg, "\toffset: %ld,\tpagesze_offet: %ld,\tpa_offset: %ld,\nsrv sockdescr: %d,\tclient sockdescr: %d\nchild pid: %ld",
						(off_t) offset, (long) pagesze_offet, (off_t) pa_offset, s, c, (intmax_t) pid);
		usage(outmsg, 0, 1);

		sleep(1); 									/* sleep 1s to not permanently occupy scheduler */


		if (waitpid(pid, NULL, 0) == -1)    		/* Wait for child */
		{
			sprintf(outmsg, "%s:\twaitpid(%ld, NULL, 0) returned -1.\nWait for child pid to exit failed!", progname, (intmax_t) pid);
			
			close(s); 								/* close server socket descriptor */
			close(c); 								/* close client socket descriptor */
			munmap(stack, STACKSZE); 				/* unmap program stack */
			
			usage(outmsg, EXIT_FAILURE, 0);
		}
		
  }

}


