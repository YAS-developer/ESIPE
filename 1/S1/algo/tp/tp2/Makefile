CC=gcc
CFLAGS=-std=c99 -Wall -Werror -g
DEPS=funcs.h
OBJ=verif.o verif_func.o parse.o funcs.o

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

verif: $(OBJ)
	gcc -o $@ $^

.PHONY: clean
clean:
	rm -f $(OBJ) verif
