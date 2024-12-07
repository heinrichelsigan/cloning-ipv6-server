/*
 * client6.c - a simple ipv6 capable echo client, that sends a text message to the server */
 * Author: 	 office@area23.at
 * Open Source (MIT Licenese)
 */
#include "socket6.h"
#include <arpa/inet.h>

char *progname = NULL;                      // progname will be set l8r to argv[0]

/* 
 * ping:
 *  sd ... socket descriptor
 *  msg ... msg to send
 *  returns msg received from socket
 * ping sends a msg to socket 
 */
char *ping(int sd, char *msg)
{
	char inbuf[MSGBUFLEN], outbuf[MSGBUFLEN];
	
	strcpy(inbuf, msg);
	send(sd, inbuf, strlen(inbuf), 0);
	recv(sd, outbuf, MSGBUFLEN, 0);
  
	strncpy(&outbuf[strlen(outbuf)], "\0", MSGBUFLEN - strlen(outbuf));
	
	printf("len=%ld, size=%ld, %s\n", strlen(outbuf), sizeof(outbuf), outbuf);
	return &outbuf[0];
}

/*
 * main:
 *  argc ... argument counter
 *  argv ... array of arguments
 *  returns program exit code
 * main entry point for clientv6 sendsocket program
 */
int main(int argc, char **argv)
{
	int sd;                                 // sd socket descriptor
	struct sockaddr_in6 addr;               // ipv6 inet addr
	uint16_t tcp6port = TCPv6_PORT;         // tcp v6 port default to TCPv6_PORT 7777 
	char *serveraddr = "::1";               // server ipv6 address
	int outlen = 0;
	char *inmsg, outmsg[MSGBUFLEN];         // outmsg

	progname = argv[0];
	if (argc > 1)
	  serveraddr = argv[1];                 // TODO: validate
	if (argc > 2)
	  if ((tcp6port = (uint16_t)atol(argv[2])) < 2)
		tcp6port = TCPv6_PORT;
	for (int ac = 3; (ac < argc && (outlen + strlen(argv[ac]) * sizeof(char)) < MSGBUFLEN); ac++) 
	{
	  strncpy(&outmsg[outlen], argv[ac], strlen(argv[ac]) * sizeof(char));
	  outlen += strlen(argv[ac]);
	  strncpy(&outmsg[outlen++], " ", sizeof(char));
	}	  

	sd = socket(AF_INET6, SOCK_STREAM, 0);   // creates new client socket
	addr.sin6_family = AF_INET6;
	addr.sin6_port = htons(tcp6port);
	inet_pton(AF_INET6, serveraddr, &addr.sin6_addr);
	connect(sd, (struct sockaddr *)&addr, sizeof(addr));
	
	if (outmsg != NULL) 
	{
	  inmsg = ping(sd, outmsg);
	  if (inmsg != NULL)
		printf("=>\t%s\n", inmsg);
	}

	close(sd);
	return 0;
				
}
