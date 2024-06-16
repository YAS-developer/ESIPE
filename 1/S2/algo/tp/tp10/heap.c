#include <stdlib.h>
#include "heap.h"

// Fonction pour créer un tas
heap *create_heap(int max) {
    heap *h = (heap *)malloc(sizeof(heap));
    if (h == NULL) {
        return NULL;
    }
    h->tree = (int *)malloc(max * sizeof(int));
    if (h->tree == NULL) {
        free(h);
        return NULL;
    }
    h->size = 0; // Le tas est initialement vide, donc la taille est 0
    h->max = max;
    return h;
}

// Fonction pour libérer un tas
void free_heap(heap *h) {
    if (h != NULL) {
        free(h->tree);
        free(h);
    }
}

// Fonction pour insérer une valeur dans le tas
void insert_heap(heap *h, int elt) {
    if (h->size >= h->max) {
        // Le tas est plein, ne rien faire ou agrandir la capacité
        return;
    }
    
    // Ajouter l'élément à la fin du tas
    h->tree[h->size] = elt;
    int i = h->size;
    h->size++;
    
    // Faire remonter l'élément pour maintenir la propriété du tas
    while (i > 0) {
        int parent = (i - 1) / 2;
        if (h->tree[parent] >= h->tree[i]) {
            break;
        }
        
        // Échanger les éléments
        int temp = h->tree[parent];
        h->tree[parent] = h->tree[i];
        h->tree[i] = temp;
        
        // Monter à l'index du parent
        i = parent;
    }
}

// Fonction pour vérifier si une structure est un tas binaire
int is_heap(heap *h) {
    for (int i = 0; i < h->size; i++) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        
        // Vérifier la relation parent-enfant pour le nœud gauche
        if (left < h->size && h->tree[i] < h->tree[left]) {
            return 0;
        }
        
        // Vérifier la relation parent-enfant pour le nœud droit
        if (right < h->size && h->tree[i] < h->tree[right]) {
            return 0;
        }
    }
    return 1;
}

// Fonction pour extraire la valeur minimum du tas
int extract_min(heap *h) {
    if (h->size == 0) {
        // Le tas est vide, retourner une valeur spéciale ou lever une exception
        return -1; // Ici, -1 signifie que le tas est vide
    }

    int min = h->tree[0];

    // Placer le dernier élément à la racine
    h->tree[0] = h->tree[h->size - 1];
    h->size--;

    // Faire descendre l'élément pour restaurer la propriété du tas
    int i = 0;
    while (i < h->size) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int smallest = i;

        if (left < h->size && h->tree[left] < h->tree[smallest]) {
            smallest = left;
        }

        if (right < h->size && h->tree[right] < h->tree[smallest]) {
            smallest = right;
        }

        if (smallest == i) {
            break;
        }

        // Échanger les éléments
        int temp = h->tree[i];
        h->tree[i] = h->tree[smallest];
        h->tree[smallest] = temp;

        i = smallest;
    }

    return min;
}




// Fonctions auxiliaires pour maintenir la propriété du tas
void heapify_down(int tab[], int n, int i) {
    int largest = i; // Initialiser largest comme racine
    int left = 2 * i + 1; // gauche = 2*i + 1
    int right = 2 * i + 2; // droite = 2*i + 2

    // Si l'enfant gauche est plus grand que la racine
    if (left < n && tab[left] > tab[largest]) {
        largest = left;
    }

    // Si l'enfant droit est plus grand que largest
    if (right < n && tab[right] > tab[largest]) {
        largest = right;
    }

    // Si largest n'est pas la racine
    if (largest != i) {
        int swap = tab[i];
        tab[i] = tab[largest];
        tab[largest] = swap;

        // Appliquer récursivement heapify_down
        heapify_down(tab, n, largest);
    }
}

// Fonction heapsort en place
void heapsort(int tab[], int size) {
    // Construire le tas (réorganiser le tableau)
    for (int i = size / 2 - 1; i >= 0; i--) {
        heapify_down(tab, size, i);
    }

    // Extraire les éléments un par un du tas
    for (int i = size - 1; i >= 0; i--) {
        // Déplacer la racine actuelle à la fin
        int temp = tab[0];
        tab[0] = tab[i];
        tab[i] = temp;

        // Appeler heapify_down sur le tas réduit
        heapify_down(tab, i, 0);
    }
}