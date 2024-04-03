CC=gcc
CFLAGS=-Wall -Wextra -Werror -ansi -g -pedantic-errors -Wunreachable-code
LDFLAGS= -lMLV
DEPS=board.h display.h
OBJ=main.o board.o display.o

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

taquin: main.o $(OBJ)
	gcc -o $@ $^ $(LDFLAGS)

.PHONY: clean
clean:
	rm -rf $(OBJ) taquin doc