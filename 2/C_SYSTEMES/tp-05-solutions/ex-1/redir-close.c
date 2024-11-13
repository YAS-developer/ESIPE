#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "../try.h"

int main(int argc, char **argv) {
  if (argc != 2) {
    fprintf(stderr, "Usage: %s FILE\n", argv[0]);
    exit(EXIT_FAILURE);
  }
  try(close(STDOUT_FILENO));
  // Now `STDOUT_FILENO` is the smallest free file descriptor.
  try(open(argv[1], O_WRONLY | O_CREAT | O_TRUNC, 0644));
  // Now `STDOUT_FILENO` refers to the file just opened.
  try(execlp("ls", "ls", "-l", (char *) NULL));
}
