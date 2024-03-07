#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#include "array.h"
#include "sort.h"

#define MAX_VALUE 10000
#define MAX_SIZE 10

void reverseArray(int arr[], int size) {
    int temp;
    for (int i = 0; i < size / 2; i++) {
        temp = arr[i];
        arr[i] = arr[size - 1 - i];
        arr[size - 1 - i] = temp;
    }
}

int compare_tab(int t1[], int t2[], int size){
    int i;
    for(i = 0; i < size; i++){
        if(t1[i] != t2[i]){
            return 0;
        }
    }
    return 1;
}

void test_selection_random(int size, int max_value){
    /* tableau de référence */
	int* tab_ref = NULL;

	/* tableau de qsort */
	int* tab_qsort = NULL;

    /* tableau de travail */
    int* tab = NULL;

    tab_ref = create_array(size);
    fill_random_array(tab_ref, size, max_value);

    tab_qsort = create_array(size);
	tab = create_array(size);


    copy_array(tab_ref, tab_qsort, size);
    copy_array(tab_ref, tab, size);

    qsort(tab_qsort, size, sizeof(int), compare);
    selection_sort(tab, size);

    if (compare_tab(tab, tab_qsort, size)) {
        printf("Le test de taille %d sur tri par selection a réussi !\n", size);
    } else {
        printf("Le test de taille %d sur tri par selection a échoué\n", size);
    }

    free(tab_ref);
    free(tab_qsort);
    free(tab);
}

void test_insertion_random(int size, int max_value){
/* tableau de référence */
	int* tab_ref = NULL;

	/* tableau de qsort */
	int* tab_qsort = NULL;

    /* tableau de travail */
    int* tab = NULL;

    tab_ref = create_array(size);
    fill_random_array(tab_ref, size, max_value);

    tab_qsort = create_array(size);
	tab = create_array(size);


    copy_array(tab_ref, tab_qsort, size);
    copy_array(tab_ref, tab, size);

    qsort(tab_qsort, size, sizeof(int), compare);
    insertion_sort(tab, size);

    if (compare_tab(tab, tab_qsort, size)) {
        printf("Le test de taille %d sur tri par insertion a réussi !\n", size);
    } else {
        printf("Le test de taille %d sur tri par insertion a échoué\n", size);
    }

    free(tab_ref);
    free(tab_qsort);
    free(tab);
}

void test_quicksort_random(int size, int max_value){
/* tableau de référence */
	int* tab_ref = NULL;

	/* tableau de qsort */
	int* tab_qsort = NULL;

    /* tableau de travail */
    int* tab = NULL;

    tab_ref = create_array(size);
    fill_random_array(tab_ref, size, max_value);

    tab_qsort = create_array(size);
	tab = create_array(size);


    copy_array(tab_ref, tab_qsort, size);
    copy_array(tab_ref, tab, size);

    qsort(tab_qsort, size, sizeof(int), compare);
    quick_sort(tab, size);

    if (compare_tab(tab, tab_qsort, size)) {
        printf("Le test de taille %d par quicksort a réussi !\n", size);
    } else {
        printf("Le test de taille %d par quicksort a échoué\n", size);
    }

    free(tab_ref);
    free(tab_qsort);
    free(tab);
}

void test_selection_reverse(int size, int max_value){
    /* tableau de référence */
	int* tab_ref = NULL;

	/* tableau de qsort */
	int* tab_qsort = NULL;

    /* tableau de travail */
    int* tab = NULL;

    tab_ref = create_array(size);
    fill_random_array(tab_ref, size, max_value);

    

    tab_qsort = create_array(size);
	tab = create_array(size);


    copy_array(tab_ref, tab_qsort, size);
    copy_array(tab_ref, tab, size);

    qsort(tab, size, sizeof(int), compare);

    reverseArray(tab, size);

    qsort(tab_qsort, size, sizeof(int), compare);
    selection_sort(tab, size);

    if (compare_tab(tab, tab_qsort, size)) {
        printf("Le test de taille %d sur tri par selection décroissant a réussi !\n", size);
    } else {
        printf("Le test de taille %d sur tri par selection décroissant a échoué\n", size);
    }

    free(tab_ref);
    free(tab_qsort);
    free(tab);
}

void test_insertion_reverse(int size, int max_value){
/* tableau de référence */
	int* tab_ref = NULL;

	/* tableau de qsort */
	int* tab_qsort = NULL;

    /* tableau de travail */
    int* tab = NULL;

    tab_ref = create_array(size);
    fill_random_array(tab_ref, size, max_value);

    

    tab_qsort = create_array(size);
	tab = create_array(size);


    copy_array(tab_ref, tab_qsort, size);
    copy_array(tab_ref, tab, size);

    qsort(tab, size, sizeof(int), compare);

    reverseArray(tab, size);

    qsort(tab_qsort, size, sizeof(int), compare);
    insertion_sort(tab, size);

    if (compare_tab(tab, tab_qsort, size)) {
        printf("Le test de taille %d sur tri par insertion décroissant a réussi !\n", size);
    } else {
        printf("Le test de taille %d sur tri par insertion décroissant a échoué\n", size);
    }

    free(tab_ref);
    free(tab_qsort);
    free(tab);
}

void test_quicksort_reverse(int size, int max_value){
/* tableau de référence */
	int* tab_ref = NULL;

	/* tableau de qsort */
	int* tab_qsort = NULL;

    /* tableau de travail */
    int* tab = NULL;

    tab_ref = create_array(size);
    fill_random_array(tab_ref, size, max_value);

    tab_qsort = create_array(size);
	tab = create_array(size);

    qsort(tab, size, sizeof(int), compare);

    reverseArray(tab, size);


    copy_array(tab_ref, tab_qsort, size);
    copy_array(tab_ref, tab, size);

    qsort(tab_qsort, size, sizeof(int), compare);
    quick_sort(tab, size);

    if (compare_tab(tab, tab_qsort, size)) {
        printf("Le test de taille %d par quicksort décroissant a réussi !\n", size);
    } else {
        printf("Le test de taille %d par quicksort décroissant a échoué\n", size);
    }

    free(tab_ref);
    free(tab_qsort);
    free(tab);
}

void test_selection_sort(int size, int max_value){
    /* tableau de référence */
	int* tab_ref = NULL;

	/* tableau de qsort */
	int* tab_qsort = NULL;

    /* tableau de travail */
    int* tab = NULL;

    tab_ref = create_array(size);
    fill_random_array(tab_ref, size, max_value);

    

    tab_qsort = create_array(size);
	tab = create_array(size);


    copy_array(tab_ref, tab_qsort, size);
    copy_array(tab_ref, tab, size);

    qsort(tab, size, sizeof(int), compare);

    qsort(tab_qsort, size, sizeof(int), compare);
    selection_sort(tab, size);

    if (compare_tab(tab, tab_qsort, size)) {
        printf("Le test de taille %d sur tri par selection croissant a réussi !\n", size);
    } else {
        printf("Le test de taille %d sur tri par selection croissant a échoué\n", size);
    }

    free(tab_ref);
    free(tab_qsort);
    free(tab);
}

void test_insertion_sort(int size, int max_value){
/* tableau de référence */
	int* tab_ref = NULL;

	/* tableau de qsort */
	int* tab_qsort = NULL;

    /* tableau de travail */
    int* tab = NULL;

    tab_ref = create_array(size);
    fill_random_array(tab_ref, size, max_value);

    

    tab_qsort = create_array(size);
	tab = create_array(size);


    copy_array(tab_ref, tab_qsort, size);
    copy_array(tab_ref, tab, size);

    qsort(tab, size, sizeof(int), compare);

    qsort(tab_qsort, size, sizeof(int), compare);
    insertion_sort(tab, size);

    if (compare_tab(tab, tab_qsort, size)) {
        printf("Le test de taille %d sur tri par insertion croissant a réussi !\n", size);
    } else {
        printf("Le test de taille %d sur tri par insertion croissant a échoué\n", size);
    }

    free(tab_ref);
    free(tab_qsort);
    free(tab);
}

void test_quicksort_sort(int size, int max_value){
/* tableau de référence */
	int* tab_ref = NULL;

	/* tableau de qsort */
	int* tab_qsort = NULL;

    /* tableau de travail */
    int* tab = NULL;

    tab_ref = create_array(size);
    fill_random_array(tab_ref, size, max_value);

    tab_qsort = create_array(size);
	tab = create_array(size);

    qsort(tab, size, sizeof(int), compare);


    copy_array(tab_ref, tab_qsort, size);
    copy_array(tab_ref, tab, size);

    qsort(tab_qsort, size, sizeof(int), compare);
    quick_sort(tab, size);

    if (compare_tab(tab, tab_qsort, size)) {
        printf("Le test de taille %d par quicksort croissant a réussi !\n", size);
    } else {
        printf("Le test de taille %d par quicksort croissant  a échoué\n", size);
    }

    free(tab_ref);
    free(tab_qsort);
    free(tab);
}



int main(int argc, char *argv[]) {

    srand(time(NULL));

    // int size = MAX_SIZE;
    int max_value = MAX_VALUE;

    



    /* Tri par selection size entiers aléatoires*/

    test_selection_random(1000, max_value);

    /* Tri par selection 5 entiers aléatoires*/

    test_selection_random(5, max_value);

    /* Tri par selection 5645 entiers aléatoires*/

    test_selection_random(5645, max_value);

    /* Tri par selection size entiers aléatoires*/

    test_insertion_random(1000, max_value);

    /* Tri par selection 5 entiers aléatoires*/

    test_insertion_random(5, max_value);

    /* Tri par selection 5645 entiers aléatoires*/

    test_insertion_random(5645, max_value);

    /* Tri par selection size entiers aléatoires*/

    test_quicksort_random(1000, max_value);

    /* Tri par selection 5 entiers aléatoires*/

    test_quicksort_random(5, max_value);

    /* Tri par selection 5645 entiers aléatoires*/

    test_quicksort_random(5645, max_value);

    /* peu de clé distante */

    test_selection_random(5451, 12);

    test_insertion_random(2356, 15);

    test_quicksort_random(6489, 21);
    test_quicksort_random(6489, 21);

    test_selection_reverse(5, max_value);
    test_selection_reverse(1245, max_value);

    test_insertion_reverse(4, max_value);
    test_insertion_reverse(2456, max_value);

    test_quicksort_reverse(9, max_value);
    test_quicksort_reverse(3824, max_value);


    test_selection_sort(6, max_value);
    test_selection_sort(1275, max_value);

    test_insertion_sort(8, max_value);
    test_insertion_sort(6524, max_value);

    test_quicksort_sort(3, max_value);
    test_quicksort_sort(3856, max_value);

    


    return EXIT_SUCCESS;
}
