# 
# Makefile for ipv6 cloning socket server
# 

# GCCFLAGS compiler and linker optiuns passed to gcc 
GCCFLAGS="-Wimplicit-function-declaration"

# default target is all
default: all

# all includes client6 server6_clone 
all: client6 server6_clone 

# client6
client6: client6.o
	gcc ${GCCFLAGS} -o client6 client6.o

client6.o: client6.c
	gcc ${GCCFLAGS} -o client6.o -c  client6.c


# server6_clone
server6_clone: server6_clone.o
	gcc ${GCCFLAGS}  -o server6_clone server6_clone.o

server6_clone.o: server6_clone.c
	gcc  ${GCCFLAGS} -o server6_clone.o -c server6_clone.c


# clean cleans client6 server6_clone 
clean:
	rm -f client6 client6.o server6_clone server6_clone.o 


