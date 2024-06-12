CC=gcc
CFLAGS=-Wall -ansi 
LDFLAGS= -lMLV
DEPS=board.h display.h
OBJ=main.o naive_count.o

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

taquin: main.o $(OBJ)
	gcc -o $@ $^ $(LDFLAGS)

.PHONY: clean
clean:
	rm -rf $(OBJ) naive_count 