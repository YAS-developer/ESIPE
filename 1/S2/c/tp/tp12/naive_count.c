#include <stdio.h>
#include <stdlib.h>
#include "naive_count.h"




void read_file(char* filename){
    FILE *fptr;
    fptr = fopen(filename, "r");


    char* c=(char *)malloc(sizeof(char));
    char* word = (char *)malloc(sizeof(char));
    int i=0, word_count;
    while(fgets(c, 1, fptr)) {
        
        if((c[0] >= 65 && c[0] <= 90) || (c[0] >= 97 && *c <= 122)){
            *(word+i) = c[0]; 
            i++;
        }
        else if((c[0]=32) || (c[0] = 44)){
            printf("%s\n", word);
            word_count++;
            free(word);
        }
    }

    printf("count word: %d", word_count);

    free(c);
    free(word);
    fclose(fptr);
}