CC=gcc
CFLAGS=-Wall
LDFLAGS=-lMLV -lm
OBJ=sudoku.o graphics.o main.o

sudoku: $(OBJ)
	$(CC) -o sudoku $(OBJ) $(CFLAGS) $(LDFLAGS)

main.o: main.c sudoku.h graphics.h
	$(CC) -c main.c $(CFLAGS)

sudoku.o: sudoku.c sudoku.h
	$(CC) -c sudoku.c $(CFLAGS)

graphics.o: graphics.c graphics.h sudoku.h
	$(CC) -c graphics.c $(CFLAGS)

clean: 
	rm -f *.o
	rm -f *~
	rm -f sudoku