
EXEC = tp10


SRC = main.c heap.c


OBJ = $(SRC:.c=.o)


CC = gcc


CFLAGS = -Wall -Wextra -Werror


all: $(EXEC)


$(EXEC): $(OBJ)
	$(CC) $(CFLAGS) -o $@ $^


%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@


clean:
	rm -f $(OBJ) $(EXEC)


rebuild: clean all

run: $(EXEC)
	./$(EXEC)
