#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#include "array.h"
#include "sort.h"

#define MAX_VALUE 10000
#define MAX_SIZE 100

int compare(const void *a, const void *b) {
    int diff = *(int*)a-*(int*)b;
    return diff == 0 ? 0 : (diff < 0 ? -1 : 1);
}




// PETITS TABLEAUX RANDOMS 
void testLittleTabRandom(){
    srand(time(NULL));
    int size=10;

    // clock_t begin = clock();
    // selection_sort(tab, size);
    // clock_t end = clock();
    // printf("SELECTION SORT: %ld\n\n", end-begin/CLOCKS_PER_SEC);

    // clock_t begin2 = clock();
    // qsort (tab2, size, sizeof(int), compare);
    // clock_t end2 = clock();
    // printf("\n\nQUICK SORT: %ld\n\n", end2-begin2);

    // TABLEAUX RANDOMS
    int* littleTab=create_array(size), *littleTab2=create_array(size);
    fill_random_array(littleTab, size, MAX_VALUE);

    copy_array(littleTab, littleTab2, size);
    selection_sort(littleTab, size);
    printf("\n\nTABLEAUX RANDOM\n\nSELECTION SORT:\n\n");
    print_array(littleTab, size);

    qsort(littleTab2, size, sizeof(int), compare);
    printf("\n\nQUICK SORT:\n\n");
    print_array(littleTab2, size);



}

// PETITS TABLEAUX TRIES
void testLittleTabSort(){
    int size=10;
    int* littleTab=create_array(size), *littleTab2=create_array(size);

    for(int i=0; i<size; i++){
        littleTab[i]=i;
    }
    
    copy_array(littleTab, littleTab2, size);
    printf("\n\nTABLEAUX TRIES AVANT:\n\n");
    print_array(littleTab, size);
    selection_sort(littleTab, size);
    printf("\n\nTABLEAUX TRIES APRES AVEC SELECTION SORT:\n\n");
    print_array(littleTab, size);

    printf("\n\nTABLEAUX2 TRIES AVANT:\n\n");
    print_array(littleTab2, size);
    qsort(littleTab2, size, sizeof(int), compare);
    printf("\n\nTABLEAUX2 TRIES APRES AVEC QUICK SORT:\n\n");
    print_array(littleTab2, size);
}

// PETITS TABLEAUX PRESQUE TRIES
void testLittleTabAlmostSort(){
    int size=10;
    int* littleTab=create_array(size), *littleTab2=create_array(size);
    for(int i=0; i<size; i++){
        if(i==2)
            littleTab[i]=-2;
        else if(i==5)
            littleTab[i]=-5;
        else
            littleTab[i]=i;
    }
    copy_array(littleTab, littleTab2, size);

    printf("\n\nTABLEAUX PRESQUE TRIES AVANT:\n\n");
    print_array(littleTab, size);
    selection_sort(littleTab, size);
    printf("\n\nTABLEAUX PRESQUE TRIES APRES AVEC SELECTION SORT:\n\n");
    print_array(littleTab, size);

    printf("\n\nTABLEAUX2 PRESQUE TRIES AVANT:\n\n");
    print_array(littleTab, size);
    qsort(littleTab2, size, sizeof(int), compare);
    printf("\n\nQUICK SORT:\n\n");
    print_array(littleTab2, size);
}

// PETITS TABLEAUX INVERSE
void testLittleTabReverse(){
    int size=10;
    int* littleTab=create_array(size), *littleTab2=create_array(size);
    
    int index=0;
    for(int i=size; i>0; i--){
        littleTab[index]=i;
        index++;
    }
    copy_array(littleTab, littleTab2, size);
    selection_sort(littleTab, size);
    printf("\n\nTABLEAUX PRESQUE TRIES\n\nSELECTION SORT:\n\n");
    print_array(littleTab, size);

    qsort(littleTab2, size, sizeof(int), compare);
    printf("\n\nQUICK SORT:\n\n");
    print_array(littleTab2, size);

    free(littleTab);
    free(littleTab2);
}




int main(int argc, char *argv[]) {

    

    // int size = MAX_SIZE;
    // int max_value = MAX_VALUE;

    /* tableau de travail */
    // int *tab, *tab2 = NULL;

    /* allocation et initialisation du tableau avec des valeurs aléatoires */
    // tab = create_array(size);
    // tab2 = create_array(size);
    // fill_random_array(tab, size, max_value);

    // for(int i=0; i<size; i++){
    //     tab2[i]=tab[i];
    // }

    // print_array(tab, size);
    // print_array(tab2, size);
    /* tri du tableau */
    ;
    // clock_t begin = clock();
    // selection_sort(tab, size);
    // clock_t end = clock();
    // printf("SELECTION SORT: %ld\n\n", end-begin);
    // print_array(tab, size);


    // clock_t begin2 = clock();
    // qsort (tab2, size, sizeof(int), compare);
    // clock_t end2 = clock();
    // printf("\n\nQUICK SORT: %ld\n\n", end2-begin2);
    // print_array(tab2, size);
    
    // testLittleTabRandom();
    testLittleTabSort();

    /* libération du tableau */
    // free(tab);
    // free(tab2);

    return EXIT_SUCCESS;
}
