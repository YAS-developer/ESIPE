#include "arrays.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main() {
    clock_t start, end;
    double cpu_time_used;

    int max_size = 250000000; // Taille maximale du tableau
    int elt = 42; // Elément à insérer ou chercher dans le tableau

    // Mesures pour la fonction create_array
    start = clock();
    int *arr = create_array(max_size);
    end = clock();
    cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
    printf("Time taken to create_array: %f\n", cpu_time_used);

    // Mesures pour la fonction insert_unsorted
    start = clock();
    int size = 0;
    for (int i = 0; i < max_size; i++) {
        insert_unsorted(arr, &size, elt);
    }
    end = clock();
    cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
    printf("Time taken to insert_unsorted: %f\n", cpu_time_used);

    // Mesures pour la fonction find_unsorted
    // start = clock();
    // for (int i = 0; i < max_size; i++) {
    //     find_unsorted(arr, size, elt);
    // }
    // end = clock();
    // cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
    // printf("Time taken to find_unsorted: %f\n", cpu_time_used);

    // Libérer la mémoire allouée pour le tableau
    free_array(arr);

    return 0;
}
