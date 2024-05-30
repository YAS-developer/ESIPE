CC=gcc
CFLAGS=-std=c99 -Wall -Werror
LDFLAGS=-lm
DEPS=visualtree.h avl.h
OBJ=visualtree.o avl.o

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

tp09: tp09.o $(OBJ)
	gcc -o $@ $^ $(LDFLAGS)

.PHONY: clean
clean:
	rm -f $(OBJ) tp09 tp09.o current-tree.dot current-tree.pdf