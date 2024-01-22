CC=gcc
CFLAGS=-std=c99 -Wall
LDFLAGS=$(shell pkg-config --libs-only-l MLV)
DEPS=
OBJ=nqueens.o

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

nqueens: nqueens.o
	gcc -o $@ $^ $(LDFLAGS)

.PHONY: clean
clean:
	rm -f $(OBJ) nqueens