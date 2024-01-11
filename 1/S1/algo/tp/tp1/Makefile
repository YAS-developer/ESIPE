CC=gcc
CFLAGS=-std=c99 -Wall -Werror -g
DEPS=arrays.h
OBJ=verif.o verif_func.o parse.o arrays.o 

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

verif: $(OBJ)
	gcc -o $@ $^


ex2: ex2/arrays.o ex2/main.o
	$(CC) -c ex2/ -o $@ $< $(CFLAGS)
	$(CC) ex2/ -o main




.PHONY: clean
clean:
	rm -f $(OBJ) verif
