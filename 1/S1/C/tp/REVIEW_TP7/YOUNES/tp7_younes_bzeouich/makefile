# Makefile TP 4 IR1/IG1

CC=gcc
CFLAGS=-Wall -ansi -g
OBJ=interface.o main.o sudoku.o in_out.o
LDFLAGS=-lMLV -lm -lpthread

calc: $(OBJ)
	$(CC) -o calc $(OBJ) $(LDFLAGS)


interface.o: interface.c interface.h
	$(CC) -c interface.c -o interface.o $(CFLAGS) -lMLV

sudoku.o: sudoku.c sudoku.h
	$(CC) -c sudoku.c -o sudoku.o $(CFLAGS)
 
in_out.o: in_out.c in_out.h
	$(CC) -c in_out.c -o in_out.o $(CFLAGS) 

main.o: main.c  interface.h 
	$(CC) -c main.c -o main.o $(CFLAGS)

clean:
	rm -f *.o
	rm -f calc
	rm -f *~
