CC=gcc
CFLAGS=-Wall -lMLV -ansi

OBJ=cell.o parser.o main.o


filter: $(OBJ)
	$(CC) -o filter $(OBJ) $(CFLAGS)

parser.o: parser.c
	$(CC) -c parser.c $(CFLAGS)

main.o: main.c cell.h
	$(CC) -c main.c $(CFLAGS)

cell.o: cell.c cell.h
	$(CC) -c cell.c $(CFLAGS)

clean: 
	rm -f *.o
	rm -f *~
	rm -f filter