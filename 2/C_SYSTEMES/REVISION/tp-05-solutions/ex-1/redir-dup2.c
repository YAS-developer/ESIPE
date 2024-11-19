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
  int fd = try(open(argv[1], O_WRONLY | O_CREAT | O_TRUNC, 0644));
  try(dup2(fd, STDOUT_FILENO));
  // Now `STDOUT_FILENO` refers to the file just opened.
  // We can close `fd` since it will not be used anymore.
  try(close(fd));
  try(execlp("ls", "ls", "-l", (char *) NULL));
}


/*

Choisissez une commande à exécuter :
1. ls -l /home
2. cat /etc/passwd
3. grep root /etc/passwd
4. echo 'Hello, world!'
5. ps aux
6. date
7. uname -a
8. whoami

*/ 