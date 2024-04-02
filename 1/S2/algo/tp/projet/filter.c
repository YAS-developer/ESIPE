#include "filter.h"
#include <stdlib.h>
#include <string.h>

filter* create_filter(int m, int k) {
    filter* f = (filter*)malloc(sizeof(filter));
    f->m = m;
    f->k = k;
    f->bits = create_bitarray(m);
    f->weights = (unsigned char*)malloc(k * sizeof(unsigned char));
    for (int i = 0; i < k; ++i) {
        // Choisir des poids aléatoires entre 3 et 255
        f->weights[i] = (unsigned char)(3 + rand() % (255 - 3));
    }
    return f;
}

void free_filter(filter* f) {
    if (f) {
        free_bitarray(f->bits);
        free(f->weights);
        free(f);
    }
}

void hash(filter* f, char* str, unsigned hashes[]) {
    int t = strlen(str);
    for (int i = 0; i < f->k; ++i) {
        unsigned hash = 0;
        for (int j = 0; j < t; ++j) {
            hash += (unsigned char)str[j] * (unsigned)f->weights[i];
        }
        hashes[i] = hash % f->m;
    }
}

void add_filter(filter* f, char* str) {
    unsigned hashes[f->k];
    hash(f, str, hashes);
    for (int i = 0; i < f->k; ++i) {
        set_bitarray(f->bits, hashes[i]);
    }
}

int is_member_filter(filter* f, char* str) {
    unsigned hashes[f->k];
    hash(f, str, hashes);
    for (int i = 0; i < f->k; ++i) {
        if (!get_bitarray(f->bits, hashes[i])) {
            return 0; // La clé n'est sûrement pas dans le filtre
        }
    }
    return 1; // La clé est peut-être dans le filtre
}
