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

    free(tab_ref);
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

    free(tab_ref);
}



int main(int argc, char *argv[]) {

    srand(time(NULL));


    /* ------------------------------TEST ENTIER ALEATOIRE--------------------------------- */

    /* Test avec un tableau de 1000 entiers aléatoire*/
    test_random(1000, MAX_VALUE);

    /* Test avec un tableau de 0 entiers aléatoire*/
    test_random(0, MAX_VALUE);

    /* Test avec un tableau de 5 entiers aléatoire*/
    test_random(5, MAX_VALUE);

    /* Test avec un tableau de 8 entiers aléatoire*/
    test_random(8, MAX_VALUE);

    /* Test avec un tableau de 10 entiers aléatoire*/
    test_random(10, MAX_VALUE);

    /* Test avec un tableau de 35628 entiers aléatoire*/
    test_random(35628, MAX_VALUE);

    /* Test avec un tableau de 124562 entiers aléatoire*/
    test_random(124562, MAX_VALUE);

    /* Test avec un tableau de 57892 entiers aléatoire*/
    test_random(57892, MAX_VALUE);

    /* Test avec un tableau de 1000 entiers aléatoire trié*/
    test_sorted(1000, MAX_VALUE);



    /* ------------------------------ TEST ENTIER TRIE ------------------------------ */


    /* Test avec un tableau de 0 entiers aléatoire trié*/
    test_sorted(0, MAX_VALUE);

    /* Test avec un tableau de 3 entiers aléatoire trié*/
    test_sorted(3, MAX_VALUE);

    /* Test avec un tableau de 6 entiers aléatoire trié*/
    test_sorted(6, MAX_VALUE);

    /* Test avec un tableau de 7 entiers aléatoire trié*/
    test_sorted(7, MAX_VALUE);

    /* Test avec un tableau de 7884 entiers aléatoire trié*/
    test_sorted(7884, MAX_VALUE);

    /* Test avec un tableau de 15698 entiers aléatoire trié*/
    test_sorted(15698, MAX_VALUE);

    /* Test avec un tableau de 75896 entiers aléatoire trié*/
    test_sorted(75896, MAX_VALUE);

    /* Test avec un tableau de 121263 entiers aléatoire trié*/
    test_sorted(121263, MAX_VALUE);

    /* ------------------ TEST ENTIER PEU DE CLES DISTINCTES ------------------------- */

    test_random(1000, 5);
    test_random(0, 5);
    test_random(5, 2);
    test_random(15, 4);
    test_random(7554, 10);
    test_random(11282, 5);


    /* ------------------ TEST ENTIER PRESQUE TRIE ------------------------- */

    test_almost_sorted(1000, MAX_VALUE, 200);

    test_almost_sorted(0, MAX_VALUE, 0);

    test_almost_sorted(5, MAX_VALUE, 2);

    test_almost_sorted(8, MAX_VALUE, 4);

    test_almost_sorted(1226, MAX_VALUE, 200);

    test_almost_sorted(1678, 6, 200); 

    test_almost_sorted(2000, 15, 200); 

    test_almost_sorted(16059, MAX_VALUE, 900);

    test_almost_sorted(28342, MAX_VALUE, 1000);


    /* ------------------ TEST ENTIER PRESQUE TRIE ------------------------- */

    test_inverted(1000, MAX_VALUE);

    test_inverted(0, MAX_VALUE);

    test_inverted(7, MAX_VALUE);

    test_inverted(12, MAX_VALUE);

    test_inverted(1678, MAX_VALUE);

    test_inverted(7456, MAX_VALUE);

    test_inverted(16459, MAX_VALUE);

    

    return EXIT_SUCCESS;
}
