# Nom de l'exécutable
EXEC = test

# Fichiers sources
SRC = main.c heap.c

# Fichiers objets générés
OBJ = $(SRC:.c=.o)

# Compilateur
CC = gcc

# Options de compilation
CFLAGS = -Wall -Wextra -Werror

# Règle par défaut
all: $(EXEC)

# Règle pour générer l'exécutable
$(EXEC): $(OBJ)
	$(CC) $(CFLAGS) -o $@ $^

# Règle pour générer les fichiers objets
%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@

# Règle pour nettoyer les fichiers objets et l'exécutable
clean:
	rm -f $(OBJ) $(EXEC)

# Règle pour nettoyer et recompiler
rebuild: clean all

# Règle pour exécuter le programme
run: $(EXEC)
	./$(EXEC)
