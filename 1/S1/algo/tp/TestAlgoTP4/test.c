#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#include "array.h"
#include "sort.h"

#define MAX_VALUE 10000
#define MAX_SIZE 1000


void test_random(int size, int max_value, int distante){
    printf("Test avec un tableau de %d entiers aléatoire\n", size);

    int* tab_ref = NULL;
    
    tab_ref = create_array(size);

    fill_random_array(tab_ref, size, max_value);

    int* tab_selectionsort = NULL;

    tab_selectionsort = create_array(size);

    copy_array(tab_ref, tab_selectionsort, size);

    qsort(tab_ref, size, sizeof(int), compare);

    selection_sort(tab_selectionsort, size);

    if(max_value > 20){
        if(compare_tab(tab_ref, tab_selectionsort, size)){
            printf("Le test %d entiers aléatoire a été effectué avec succès !\n", size);
        }else{
            printf("Le test %d entiers aléatoire a échoué\n", size);
        }
    }
    else {
        if(compare_tab(tab_ref, tab_selectionsort, size)){
            printf("Le test %d entiers aléatoire avec peu de clé distante a été effectué avec succès !\n", size);
        }else{
            printf("Le test %d entiers aléatoire avec peu de clé distante a échoué\n", size);
        }
    }

    
    printf("\n");
}

void test_sorted(int size, int max_value){
    printf("Test avec un tableau de %d entiers aléatoire trié\n", size);

    int* tab_ref = NULL;
    
    tab_ref = create_array(size);

    fill_random_array(tab_ref, size, max_value);

    qsort(tab_ref, size, sizeof(int), compare);

    int* tab_selectionsort = NULL;

    tab_selectionsort = create_array(size);

    copy_array(tab_ref, tab_selectionsort, size);

    qsort(tab_ref, size, sizeof(int), compare);

    selection_sort(tab_selectionsort, size);

    if(compare_tab(tab_ref, tab_selectionsort, size)){
        printf("Le test %d entiers aléatoire trié a été effectué avec succès !\n", size);
    }else{
        printf("Le test %d entiers aléatoire trié a échoué\n", size);
    }
    printf("\n");
}


void test_almost_sorted(int size, int max_value, int disorder) {

    if(disorder > size){
        printf("test_almost_sorted(%d, %d, %d) ne peux pas être réalisé car disorder est supérieur à size\n",size, max_value, disorder);
        return ;
    }

    printf("Test avec un tableau de %d entiers presque trié\n", size);


    int* tab_ref = create_array(size);
    fill_random_array(tab_ref, size, max_value);
    qsort(tab_ref, size, sizeof(int), compare);

    // Introduire un désordre
    for (int i = 0; i < disorder; i++) {
        int index1 = rand() % size;
        int index2 = rand() % size;
        int temp = tab_ref[index1];
        tab_ref[index1] = tab_ref[index2];
        tab_ref[index2] = temp;
    }

    int* tab_selectionsort = create_array(size);
    copy_array(tab_ref, tab_selectionsort, size);

    qsort(tab_ref, size, sizeof(int), compare);
    selection_sort(tab_selectionsort, size);

    if (compare_tab(tab_ref, tab_selectionsort, size)) {
        printf("Le test %d entiers presque trié a été effectué avec succès !\n", size);
    } else {
        printf("Le test %d entiers presque trié a échoué\n", size);
    }
    printf("\n");
}

void test_inverted(int size, int max_value) {
    printf("Test avec un tableau de %d entiers inversé\n", size);

    int* tab_ref = create_array(size);
    fill_random_array(tab_ref, size, max_value);
    qsort(tab_ref, size, sizeof(int), compare);
    // Inverser l'ordre
    for (int i = 0; i < size / 2; i++) {
        int temp = tab_ref[i];
        tab_ref[i] = tab_ref[size - 1 - i];
        tab_ref[size - 1 - i] = temp;
    }

    int* tab_selectionsort = create_array(size);
    copy_array(tab_ref, tab_selectionsort, size);

    qsort(tab_ref, size, sizeof(int), compare);
    selection_sort(tab_selectionsort, size);

    if (compare_tab(tab_ref, tab_selectionsort, size)) {
        printf("Le test %d entiers inversé a été effectué avec succès !\n", size);
    } else {
        printf("Le test %d entiers inversé a échoué\n", size);
    }
    printf("\n");
}


