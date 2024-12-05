
GCCFLAGS="-Wimplicit-function-declaration"

# default target is all
default: all

# all includes client6 server6_fork server6_clone
all: clone0 client6 server6_fork server6_clone 


clone0: clone0.o
	gcc ${GCCFLAGS} -o clone0 clone0.o

clone0.o: clone0.c 
	gcc ${GCCFLAGS} -o clone0.o -c clone0.c



# client6
client6: client6.o
	gcc ${GCCFLAGS} -o client6 client6.o

client6.o: client6.c
	gcc ${GCCFLAGS} -o client6.o -c  client6.c

# server6_fork
server6_fork: server6_fork.o
	gcc ${GCCFLAGS}  -o server6_fork server6_fork.o

server6_fork.o: server6_fork.c
	gcc  ${GCCFLAGS} -c server6_fork.c -o server6_fork.o


# server6_clone
server6_clone: server6_clone.o
	gcc ${GCCFLAGS}  -o server6_clone server6_clone.o

server6_clone.o: server6_clone.c
	gcc  ${GCCFLAGS} -o server6_clone.o -c server6_clone.c


# cleaaclient6 server6_fork server6_clone
clean:
	rm -f client6 client6.o server6_fork server6_fork.o server6_clone server6_clone.o clone0 clone0.o


