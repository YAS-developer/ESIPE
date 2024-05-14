# Squelette de Makefile

# Définition des variables
CC=gcc
CFLAGS=-Wall -Wextra -Werror -ansi -lMLV -Wunreachable-code
OBJ=main.o bit.o dames.o graphic.o

# Compilation des fichiers
but : $(OBJ)
	$(CC) -o tp13 $(OBJ) $(CFLAGS)

main.o : main.c dames.h graphic.h
	$(CC) -c main.c $(CFLAGS)

dames.o : dames.c dames.h bit.h
	$(CC) -c dames.c $(CFLAGS)

bit.o : bit.c bit.h
	$(CC) -c bit.c $(CFLAGS)

graphic.o : graphic.c graphic.h bit.h
	$(CC) -c graphic.c $(CFLAGS)

cleandoc:
	@echo "Cleaning documentation..."
	@rm -rf doc
	@echo "Documentation cleaned !"

doc: cleandoc
	@echo "Creating documentation in doc/..."
	@doxygen Doxyfile
	@echo "Documentation created"

.PHONY: clean
clean:
	rm -rf $(OBJ) tp13 tp13.o doc
