#
# Makefile for ipv4 ipv6 Echo Server & Echo Client
#

new: CLEAN SERVER CLIENT
all: SERVER CLIENT

client: CLIENT
server: SERVER
clean: CLEAN
	
SERVER:
	javac EchoServer.java EchoInputStream.java	

CLIENT: 
	javac EchoClient.java

CLEAN:
	rm -f *.class 

