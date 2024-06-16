#include <stdio.h>
#include "heap.h"

int main() {
    int tab[] = {30, 20, 10, 40, 50};
    int size = sizeof(tab) / sizeof(tab[0]);

    printf("Array before sorting: ");
    for (int i = 0; i < size; i++) {
        printf("%d ", tab[i]);
    }
    printf("\n");

    heapsort(tab, size);

    printf("Array after sorting: ");
    for (int i = 0; i < size; i++) {
        printf("%d ", tab[i]);
    }
    printf("\n");

    return 0;
}
