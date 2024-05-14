CC=gcc
CFLAGS=-Wall -ansi 
LDFLAGS= -lMLV
DEPS=bit.h
OBJ=main.o bit.o


%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)


tp13: main.o $(OBJ)
	gcc -o $@ $^ $(LDFLAGS)


.PHONY: clean
clean:
	rm -rf $(OBJ) tp13 
