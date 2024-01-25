CC=gcc
CFLAGS=-Wall
LDFLAGS=-lMLV
OBJ=sudoku.o main.o in_out.o graphics.o

sudoku: $(OBJ)
	$(CC) -o sudoku $(OBJ) $(CFLAGS) $(LDFLAGS)

main.o: main.c sudoku.h in_out.h graphics.h
	$(CC) -c main.c $(CFLAGS) $(LDFLAGS)

sudoku.o: sudoku.c sudoku.h
	$(CC) -c sudoku.c $(CFLAGS) $(LDFLAGS)

in_out.o: in_out.c in_out.h
	$(CC) -c in_out.c $(CFLAGS) $(LDFLAGS)

graphics.o: graphics.c graphics.h
	$(CC) -c graphics.c $(CFLAGS) $(LDFLAGS)

clean: 
	rm -f *.o
	rm -f *~
	rm -f sudoku
