#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include "./try.h"

int main(int argc, char** argv){

    if(argc != 2){
        fprintf(stderr,"Usage %s txt\n", argv[0]);
        return EXIT_FAILURE;
    }

    int fd = try(open("toto.txt", O_CREAT | O_WRONLY | O_TRUNC), 0642);
    if(fd == -1){
        perror("Erreur ");
        return EXIT_FAILURE;
    }
  
    try(write(fd, argv[1], strlen(argv[1])));
    

    close(fd);
    


    return 0;
}