#include <stdio.h>
#include <fcntl.h>
#include "../try.h"

#define BFSIZE 8192

void cat(int fd){
    char buf[BFSIZE];
    int rdLen;
    while((rdLen = try(read(fd, buf, sizeof(buf))) ) > 0){
        char* tmp = buf;
        try(write(STDOUT_FILENO, tmp, rdLen));
    }
}

int main(int argc, char **argv){
    if(argc < 2){
        cat(STDIN_FILENO);
    }
    else{
        
        int fd = try(open(argv[1], O_RDONLY));

        cat(fd);
        close(fd);
    }   
}



// static void cat(int fd) {
//   char buf[BUFSIZ];
//   ssize_t rdLen;
//   while ((rdLen = try(read(fd, buf, sizeof(buf)))) > 0) {
//     char *bufPtr = buf;
//     while (rdLen > 0) {
//       ssize_t wrLen = try(write(STDOUT_FILENO, bufPtr, rdLen));
//       bufPtr += wrLen;
//       rdLen -= wrLen;
//     }
//   }
// }

// int main(int argc, char **argv) {
//   if (argc == 1) {
//     cat(STDIN_FILENO);
//   }
//   else {
//     for (char **argp = argv + 1; *argp; argp++) {
//       int fd = open(*argp, O_RDONLY);
//       if (fd == -1) {
//         perror(*argp);
//         continue;
//       }
//       cat(fd);
//       try(close(fd));
//     }
//   }
// }
