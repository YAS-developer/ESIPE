
GCC=gcc -Wall 

run: main.c stack.c 
	$(GCC) -c stack.c -o stack.o
	$(GCC) -c main.c -o main.o
	$(GCC) main.o stack.o -o run
	./run

clear:
	rm main.o stack.o run
