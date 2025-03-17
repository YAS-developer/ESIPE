#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include "../try.h"

int main(int argc, char** argv){
    if(argc != 2){
      fprintf(stderr, "Usage: %s file\n", argv[0]);
      exit(EXIT_FAILURE);
    }
    int fd = try(open(argv[1], O_WRONLY | O_CREAT | O_TRUNC, 0644));
    try(dup2(fd, STDOUT_FILENO));
    close(fd);


    char* args[] = {"whoami", (char*) NULL};
    try(execvp("whoami", args));


    // try(execlp("cat", "cat", "/etc/passwd", (char*) NULL));
}