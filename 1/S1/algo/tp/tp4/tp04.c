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


/** 
 * 
 * PETITS TABLEAUX 
 * 
 **/



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
    printf("\n\nTABLEAU 1 RANDOM AVANT:\n\n");
    print_array(littleTab, size);
    selection_sort(littleTab, size);
    printf("\n\nTABLEAU 1 RANDOM APRES SELECTION SORT:\n\n");
    print_array(littleTab, size);

    printf("\n\nTABLEAU 2 RANDOM AVANT:\n\n");
    print_array(littleTab2, size);
    qsort(littleTab2, size, sizeof(int), compare);
    printf("\n\nTABLEAU 2 RANDOM APRES QUICK SORT:\n\n");
    print_array(littleTab2, size);

    free(littleTab);
    free(littleTab2);

}

// PETITS TABLEAUX TRIES
void testLittleTabSort(){
    int size=10;
    int* littleTab=create_array(size), *littleTab2=create_array(size);

    for(int i=0; i<size; i++){
        littleTab[i]=i;
    }
    
    copy_array(littleTab, littleTab2, size);
    printf("\n\nTABLEAU 1 TRIES AVANT:\n\n");
    print_array(littleTab, size);
    selection_sort(littleTab, size);
    printf("\n\nTABLEAU 1 TRIES APRES SELECTION SORT:\n\n");
    print_array(littleTab, size);

    printf("\n\nTABLEAU 2 TRIES AVANT:\n\n");
    print_array(littleTab2, size);
    qsort(littleTab2, size, sizeof(int), compare);
    printf("\n\nTABLEAU 2 TRIES APRES QUICK SORT:\n\n");
    print_array(littleTab2, size);

    free(littleTab);
    free(littleTab2);
}

// PETITS TABLEAUX PRESQUE TRIES
void testLittleTabAlmostSort(){
    int size=10;
    int* littleTab=create_array(size), *littleTab2=create_array(size);
    for(int i=0; i<size; i++){
        if(i==2)
            littleTab[i]=7;
        else if(i==5)
            littleTab[i]=-5;
        else
            littleTab[i]=i;
    }
    copy_array(littleTab, littleTab2, size);

    printf("\n\nTABLEAU 1 PRESQUE TRIES AVANT:\n\n");
    print_array(littleTab, size);
    selection_sort(littleTab, size);
    printf("\n\nTABLEAU 1 PRESQUE TRIES APRES SELECTION SORT:\n\n");
    print_array(littleTab, size);

    printf("\n\nTABLEAU 2 PRESQUE TRIES AVANT:\n\n");
    print_array(littleTab2, size);
    qsort(littleTab2, size, sizeof(int), compare);
    printf("\n\nTABLEAU 2 PRESQUE TRIES APRES QUICK SORT:\n\n");
    print_array(littleTab2, size);

    free(littleTab);
    free(littleTab2);
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
    printf("\n\nTABLEAU 1 INVERSE TRIES AVANT:\n\n");
    print_array(littleTab, size);
    selection_sort(littleTab, size);
    printf("\n\nTABLEAU 1 INVERSE TRIES APRES SELECTION SORT:\n\n");
    print_array(littleTab, size);


    printf("\n\nTABLEAU 2 INVERSE TRIES AVANT:\n\n");
    print_array(littleTab2, size);

    qsort(littleTab2, size, sizeof(int), compare);
    printf("\n\nTABLEAU 2 INVERSE TRIES APRES QUICK SORT:\n\n");
    print_array(littleTab2, size);

    free(littleTab);
    free(littleTab2);
}

// PETITS TABLEAUX AVEC PEU DE CLES DISTINCTS
void testLittleTabFewDistinct(){
    int size=10;
    int* littleTab=create_array(size), *littleTab2=create_array(size);
    
    fill_random_array(littleTab, size, 3);
    copy_array(littleTab, littleTab2, size);
    printf("\n\nTABLEAU 1 INVERSE TRIES AVANT:\n\n");
    print_array(littleTab, size);
    selection_sort(littleTab, size);
    printf("\n\nTABLEAU 1 INVERSE TRIES APRES SELECTION SORT:\n\n");
    print_array(littleTab, size);


    printf("\n\nTABLEAU 2 INVERSE TRIES AVANT:\n\n");
    print_array(littleTab2, size);

    qsort(littleTab2, size, sizeof(int), compare);
    printf("\n\nTABLEAU 2 INVERSE TRIES APRES QUICK SORT:\n\n");
    print_array(littleTab2, size);

    free(littleTab);
    free(littleTab2);
}



/** 
 * 
 * GRANDS TABLEAUX 
 * 
 **/



// GRANDS TABLEAUX RANDOMS 
void testBigTabRandom(){
    srand(time(NULL));
    int size=10000;
    // TABLEAUX RANDOMS
    int* bigTab=create_array(size), *bigTab2=create_array(size);
    fill_random_array(bigTab, size, MAX_VALUE);


    copy_array(bigTab, bigTab2, size);
    printf("\n\nTABLEAU 1 RANDOM AVANT:\n\n");
    print_array(bigTab, size);
    selection_sort(bigTab, size);
    printf("\n\nTABLEAU 1 RANDOM APRES SELECTION SORT:\n\n");
    print_array(bigTab, size);

    printf("\n\nTABLEAU 2 RANDOM AVANT:\n\n");
    print_array(bigTab2, size);
    qsort(bigTab2, size, sizeof(int), compare);
    printf("\n\nTABLEAU 2 RANDOM APRES QUICK SORT:\n\n");
    print_array(bigTab2, size);

    free(bigTab);
    free(bigTab2);
}


// GRANDS TABLEAUX TRIES
void testBigTabSort(){
    int size=10000;
    int* bigTab=create_array(size), *bigTab2=create_array(size);

    for(int i=0; i<size; i++){
        bigTab[i]=i;
    }
    
    copy_array(bigTab, bigTab2, size);
    printf("\n\nTABLEAU 1 TRIES AVANT:\n\n");
    print_array(bigTab, size);
    selection_sort(bigTab, size);
    printf("\n\nTABLEAU 1 TRIES APRES SELECTION SORT:\n\n");
    print_array(bigTab, size);

    printf("\n\nTABLEAU 2 TRIES AVANT:\n\n");
    print_array(bigTab2, size);
    qsort(bigTab2, size, sizeof(int), compare);
    printf("\n\nTABLEAU 2 TRIES APRES QUICK SORT:\n\n");
    print_array(bigTab2, size);

    free(bigTab);
    free(bigTab2);
}


// GRANDS TABLEAUX PRESQUE TRIES
void testBigTabAlmostSort(){
    int size=10000;
   
    int* bigTab=create_array(size), *bigTab2=create_array(size);
    
    for(int i=0; i<size; i++){
        if(i==2)
            bigTab[i]=7;
        else if(i==5)
            bigTab[i]=-5;
        else if(i==40)
            bigTab[i]=22; 
        else if(i==167)
            bigTab[i]=90;
        else if(i==978)
            bigTab[i]=785; 
        else
            bigTab[i]=i;
    }

    copy_array(bigTab, bigTab2, size);

    printf("\n\nTABLEAU 1 PRESQUE TRIES AVANT:\n\n");
    print_array(bigTab, size);
    selection_sort(bigTab, size);
    printf("\n\nTABLEAU 1 PRESQUE TRIES APRES SELECTION SORT:\n\n");
    print_array(bigTab, size);

    printf("\n\nTABLEAU 2 PRESQUE TRIES AVANT:\n\n");
    print_array(bigTab2, size);
    qsort(bigTab2, size, sizeof(int), compare);
    printf("\n\nTABLEAU 2 PRESQUE TRIES APRES QUICK SORT:\n\n");
    print_array(bigTab2, size);

    free(bigTab);
    free(bigTab2);
}




// GRANDS TABLEAUX INVERSE
void testBigTabReverse(){
    int size=10000;
    int* BigTab=create_array(size), *BigTab2=create_array(size);
    
    int index=0;
    for(int i=size; i>0; i--){
        BigTab[index]=i;
        index++;
    }
    copy_array(BigTab, BigTab2, size);
    printf("\n\nTABLEAU 1 INVERSE TRIES AVANT:\n\n");
    print_array(BigTab, size);
    selection_sort(BigTab, size);
    printf("\n\nTABLEAU 1 INVERSE TRIES APRES SELECTION SORT:\n\n");
    print_array(BigTab, size);


    printf("\n\nTABLEAU 2 INVERSE TRIES AVANT:\n\n");
    print_array(BigTab2, size);

    qsort(BigTab2, size, sizeof(int), compare);
    printf("\n\nTABLEAU 2 INVERSE TRIES APRES QUICK SORT:\n\n");
    print_array(BigTab2, size);

    free(BigTab);
    free(BigTab2);
}


void testBigTabFewDistinct(){
    int size=10000;
    int* littleTab=create_array(size), *littleTab2=create_array(size);
    
    fill_random_array(littleTab, size, 3);
    copy_array(littleTab, littleTab2, size);
    printf("\n\nTABLEAU 1 INVERSE TRIES AVANT:\n\n");
    print_array(littleTab, size);
    selection_sort(littleTab, size);
    printf("\n\nTABLEAU 1 INVERSE TRIES APRES SELECTION SORT:\n\n");
    print_array(littleTab, size);


    printf("\n\nTABLEAU 2 INVERSE TRIES AVANT:\n\n");
    print_array(littleTab2, size);

    qsort(littleTab2, size, sizeof(int), compare);
    printf("\n\nTABLEAU 2 INVERSE TRIES APRES QUICK SORT:\n\n");
    print_array(littleTab2, size);

    free(littleTab);
    free(littleTab2);
}


int main(int argc, char *argv[]) {

    // TESTS PETITS TABLEAUX

    // testLittleTabRandom();
    // testLittleTabSort();
    // testLittleTabAlmostSort();
    // testLittleTabReverse();
    // testLittleTabFewDistinct();

    // TESTS GRANDS TABLEAUX
    
    // testBigTabRandom();
    // testBigTabSort();
    // testBigTabAlmostSort();
    // testBigTabReverse();
    // testBigTabFewDistinct();


    




    return EXIT_SUCCESS;
}
