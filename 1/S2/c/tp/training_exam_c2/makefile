CC=gcc
CFLAGS=-Wall -ansi 
LDFLAGS= -lMLV
DEPS=bit.h
OBJ=main.o people.o


%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)


main: main.o $(OBJ)
	gcc -o $@ $^ $(LDFLAGS)


.PHONY: clean
clean:
	rm -rf $(OBJ) main 
