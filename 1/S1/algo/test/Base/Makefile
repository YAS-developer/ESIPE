CC=gcc
CFLAGS=-Wall -ansi
DEPS=util.h exercices.h
OBJ=util.o exercices.o tpnote.o

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

tpnote: $(OBJ)
	gcc -o $@ $^ $(LDFLAGS)

.PHONY: clean
clean:
	rm -f $(OBJ) tpnote