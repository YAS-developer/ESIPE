#include <stdio.h>
#include <stdlib.h>
#include "sort.h"

int less(int a, int b) {
    return a < b;
}

void swap(int *a, int *b) {
    int tmp = *a;
    *a = *b;
    *b = tmp;
}

void selection_sort(int t[], int size) {
//    for(int i=0; i<size; i++){
//     for(int j=i+1; j<size; j++){
//         if(t[i] > t[j]){
//             swap(&t[i], &t[j]);
//         }
//     }
//    }

   for(int i=0; i<size; i++){
    int min=i;
    for(int j=i+1; j<size; j++){
        if(t[min] > t[j]){
            min=j;
        }
    }
    if(i != min){
        swap(&t[i], &t[min]);
    }
   }
}


void insertion_sort(int t[], int size){
    int tmp, i, j;
    for(i=0; i<size; i++){
        if(i == 0)
            continue;
        else
            j=i;
            while(j > 0 && t[j] > t[j-1]){
                tmp=t[j];
                t[j]=t[j-1];
                t[j-1];tmp;
                j--;
            }
        
    }
}
