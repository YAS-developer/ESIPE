#ifndef BITARRAY_H
#define BITARRAY_H

typedef struct _bitarray {
    unsigned char* array; // Pointeur vers le tableau de bits
    int size; // Taille du tableau en bits
} bitarray;

// Crée un nouveau bitarray pouvant stocker m bits
bitarray* create_bitarray(int m);

// Libère la mémoire associée au bitarray
void free_bitarray(bitarray* a);

// Met la position pos dans le bitarray à 1
void set_bitarray(bitarray* a, int pos);

// Réinitialise la position pos dans le bitarray à 0
void reset_bitarray(bitarray* a, int pos);

// Obtient la valeur à la position pos dans le bitarray
int get_bitarray(bitarray* a, int pos);

// Réinitialise toutes les positions dans le bitarray à 0
void clear_bitarray(bitarray* a);

#endif
