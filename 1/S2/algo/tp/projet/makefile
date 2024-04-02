CC=gcc
CFLAGS=-Wall -g
OBJ=main.o bitarray.o filter.o

%.o: %.c
	$(CC) -c -o $@ $< $(CFLAGS)

password_filter: $(OBJ)
	$(CC) -o $@ $^ $(CFLAGS)

clean:
	rm -f *.o password_filter
