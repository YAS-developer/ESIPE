#include "bitarray.h"
#include <stdlib.h>
#include <string.h>

bitarray* create_bitarray(int m) {
    bitarray* a = (bitarray*)malloc(sizeof(bitarray));
    a->size = (m + 7) / 8; // Calcul pour stocker m bits dans des octets
    a->array = (unsigned char*)malloc(a->size);
    clear_bitarray(a); // Initialiser tous les bits à 0
    return a;
}

void free_bitarray(bitarray* a) {
    if(a) {
        free(a->array);
        free(a);
    }
}

void set_bitarray(bitarray* a, int pos) {
    if(a && pos < a->size * 8) {
        a->array[pos / 8] |= (1 << (pos % 8));
    }
}

void reset_bitarray(bitarray* a, int pos) {
    if(a && pos < a->size * 8) {
        a->array[pos / 8] &= ~(1 << (pos % 8));
    }
}

int get_bitarray(bitarray* a, int pos) {
    if(a && pos < a->size * 8) {
        return (a->array[pos / 8] & (1 << (pos % 8))) != 0;
    }
    return 0;
}

void clear_bitarray(bitarray* a) {
    if(a) {
        memset(a->array, 0, a->size);
    }
}
