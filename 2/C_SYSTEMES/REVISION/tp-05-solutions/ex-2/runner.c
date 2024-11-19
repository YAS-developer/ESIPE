#include <fcntl.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>
#include "../try.h"

int main(int argc, char **argv) {
  if (argc < 2) {
    fprintf(stderr, "Usage: %s COMMAND\n", argv[0]);
    exit(EXIT_FAILURE);
  }
  switch (try(fork())) {
  case 0:;  // Child
    int fd = try(open("/dev/null", O_WRONLY));
    try(dup2(fd, STDOUT_FILENO));
    try(dup2(fd, STDERR_FILENO));
    try(close(fd));
    try(execvp(argv[1], argv + 1));
    break;
  default:; // Parent
    int wstatus;
    try(wait(&wstatus));
    bool ok = WIFEXITED(wstatus) && WEXITSTATUS(wstatus) == 0;
    printf("%s\n", ok ? "OK" : "ERROR");
    break;
  }
}
