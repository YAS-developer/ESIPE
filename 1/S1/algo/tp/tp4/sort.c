#include <stdio.h>
#include <stdlib.h>
#include "sort.h"

int nb_less;
int nb_swap;

int less(int a, int b) {
    nb_less++;
    return a < b;
}

void swap(int *a, int *b) {
    nb_swap++;
    int tmp = *a;
    *a = *b;
    *b = tmp;
}

int compare(const void *a, const void *b) {
    int diff = *(int*)a-*(int*)b;
    return diff == 0 ? 0 : (diff < 0 ? -1 : 1);
}

void selection_sort(int t[], int size) {
    int i, min, j;

    for(i = 0; i < size; i++){
        min = i;
        for(j = i+1; j < size; j++){  
            if(less(t[j],t[min])){
                min = j; 
            }
        }
        if(t[i] != t[min]){
            swap(&t[i], &t[min]);
        }  
    }
}

void insertion_sort(int t[], int size){
    int i;
    for(i = 0; i < size; i++){
        int j = i;
        while (j > 0 && less(t[j], t[j-1]))
        {
            swap(&t[j-1], &t[j]);
            j--;
        }
        
    }
};

int partition(int t[], int lo, int hi){
    int i = lo+1;
    int j = hi;

    while(1){
        while (less(t[i], t[lo]) && i < hi)
        {
            i++;
        }

        while (less(t[lo], t[j]) && j > lo)
        {
            j--;
        }

        if(i >= j) break;

        swap(&t[i], &t[j]);
        i++;
        j--;
    }
    swap(&t[lo], &t[j]);
    return j;
}

void quickSortRecursive(int t[], int lo, int hi) {
    if(lo >= hi)
        return;

    int pivot = partition(t, lo, hi);

    quickSortRecursive(t, lo, pivot-1);
    quickSortRecursive(t, pivot+1, hi);
}

void quick_sort(int t[], int size) {
    quickSortRecursive(t, 0, size - 1);
}
