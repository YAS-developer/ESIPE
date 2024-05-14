#include "bit.h"
#include <stdlib.h>


unsigned long int tab[SIZE][SIZE];

int bit_value_ULI(unsigned long int n, int position){
    /*
    int i=SIZE-1, j=SIZE-1;
    for(; i>=0; i--){
        for(; j>=0; j--){
             if(tab[i][j] == n){
                 return 1;
        }
    } 
    */
    if(position < 0 || position > 63){
        fprintf(stderr, "Veuillez renseigner une position entre 0 et 63.");
        return EXIT_FAILURE;
    }

    return (int) (n >> position) & 1;
    
}


void print_ULI(unsigned long int n){
    int i;
    printf("%ld en binaire : ", n);
    for (i = 0; i < 64; i++)
        printf("%ld", (n >> i ) & 1);

    printf("\n"); 
}

void set_positive_bit_ULI(unsigned long int *n, int position) {
    *n = *n | (((*n >> position) | 1) << position);
}

void set_negative_bit_ULI(unsigned long int *n, int position) {
    *n = (*n) & ~((unsigned long int) 1 << position);
}