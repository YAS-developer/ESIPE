CFLAGS = -g -Wall
LDLIBS = -lX11 -lm -no-pie
LDFLAGS = -z noexecstack

all: dessin

dessin: dessin.o multiple.o
	$(CC) $(LDFLAGS) $^ $(LDLIBS) -o $@

%.o: %.nasm
	nasm -f elf64 -g $< -o $@

clean:
	rm *.o dessin || true