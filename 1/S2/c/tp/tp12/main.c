#include <stdio.h>
#include <stdlib.h>
#include "naive_count.h"

int main(int argc, char **argv){

    if(argc < 2){
        fprintf(stderr,"Missing argument: %s file.txt", argv[0]);
        exit(0);
    }
    
    read_file(argv[1]);

    return 0;
}