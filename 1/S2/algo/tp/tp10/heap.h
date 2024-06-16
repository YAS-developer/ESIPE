#ifndef HEAP_H
#define HEAP_H

typedef struct {
    int *tree;
    int size;
    int max;
} heap;

// Prototype de la fonction pour créer un tas
heap *create_heap(int max);

// Prototype de la fonction pour libérer un tas
void free_heap(heap *h);

// Prototype de la fonction pour insérer une valeur dans le tas
void insert_heap(heap *h, int elt);

// Prototype de la fonction pour vérifier si une structure est un tas binaire
int is_heap(heap *h);

// Prototype de la fonction pour extraire la valeur minimum du tas
int extract_min(heap *h);

// Prototype de la fonction heapsort
void heapsort(int tab[], int size);

#endif // HEAP_H
