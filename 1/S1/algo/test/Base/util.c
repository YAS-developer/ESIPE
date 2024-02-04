#include "util.h"
#include <stdio.h>
#include <stdlib.h>

int *create_array(int size) {
    return (int *)malloc(size*sizeof(int));
}

void fill_random_array(int t[], int size, int mod) {
    int i;
    for (i = 0; i < size; i++)
        t[i] = rand()%mod;
}

void fill_random_permutation(int t[], int size) {
    int i;
    for (i = 0; i < size; i++) {
        int j = rand()%(i+1);
        t[i] = t[j];
        t[j] = i;
    }
}

void display_array(int t[], int size) {
    int i;
    for (i = 0; i < size; i++)
        printf("%d ", t[i]);
    printf("\n");
}

int compare(const void *a,const void *b) {
    return (*(int*)a)-(*(int*)b);
}

void sort(int t[], int size) {
    qsort(t, size, sizeof(int), compare);
}
