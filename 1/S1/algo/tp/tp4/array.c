#include <stdio.h>
#include <stdlib.h>
#include "array.h"

int* create_array(int size) {
    int* t = (int*) malloc(size * sizeof(int)); 
    if (t == NULL) {
        printf("Failed to allocate array.\n");
        exit(EXIT_FAILURE);
    }
    return t;
}

void print_array(int t[], int size) {
    int i;
    printf("[");
    for (i = 0; i < size-1; i++) {
        printf("%d, ", t[i]);    
    }
    if (size > 0)
        printf("%d", t[size-1]);
    printf("]\n");
    
}

void copy_array(int src[], int dst[], int size) {
    int i;
    for (i = 0; i < size; i++) {
        dst[i] = src[i];
    }
}

void fill_random_array(int t[], int size, int max_value) {
    int i;
    for (i = 0; i<size; i++) {
        t[i] = rand()%max_value;
    }
}

/**
 * Initialises the array t with a random permutation of
 *   the values 0, 1, ..., size-1.
 * Uses the Knuth shuffle:
 *   https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
 */
void fill_random_permutation(int t[], int size) {
    int i;
    for (i = 0; i < size; i++) {
        int j = rand()%(i+1);
        t[i] = t[j];
        t[j] = i;
    }
}
