#include <limits.h>
#include <stdio.h>
#include "bit.h"

void print_ULI(unsigned long int n) {
    int i;
    for (i = CHAR_BIT * 8 - 1; i >= 0; i--) {
        printf("%d", bit_value_ULI(n, i));
    }
    printf("\n");
}

int bit_value_ULI(unsigned long int n, int position) {
    return (int) (n >> position) & 1;
}

void set_positive_bit_ULI(unsigned long int *n, int position) {
    *n = *n | (((*n >> position) | 1) << position);
}

void set_negative_bit_ULI(unsigned long int *n, int position) {
    *n = (*n) & ~((unsigned long int) 1 << position);
}
