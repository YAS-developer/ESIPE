#ifndef FILTER_H
#define FILTER_H

#include "bitarray.h"

typedef struct _filter {
    bitarray* bits;
    int m; // Taille du bitarray
    int k; // Nombre de fonctions de hachage
    unsigned char* weights; // Poids pour les fonctions de hachage
} filter;

// Crée un nouveau filtre avec m bits et k fonctions de hachage
filter* create_filter(int m, int k);

// Libère la mémoire associée au filtre
void free_filter(filter* f);

// Calcule k valeurs de hachage pour la chaîne str
void hash(filter* f, char* str, unsigned hashes[]);

// Ajoute la clé str au filtre
void add_filter(filter* f, char* str);

// Vérifie si la clé str est dans le filtre, 0 signifie non, 1 signifie peut-être
int is_member_filter(filter* f, char* str);

#endif
