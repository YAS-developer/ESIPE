#include "arrays.h"
#include <stdlib.h>
#include <stdio.h>

int *create_array(int max_size) {
    int *ptr = (int*)malloc(max_size*sizeof(int));
    return ptr;
}

void free_array(int t[]) {
    free(t);
}

void insert_unsorted(int t[], int *size, int elt) {
    t[(*size)] = elt;
    (*size)++;
}

int find_unsorted(int t[], int size, int elt) {
    for(int i=0; i<size; i++){
        if(t[i] == elt){
            return 1;
        }
    }
    return 0;
}

void insert_sorted(int t[], int *size, int elt) {
    /*
    * Write this function!
    */
    

    // for(int i=(*size-1); i>=0; i--){
    //     if(elt < t[i]){
    //         t[i+1]=t[i]; 
    //     }
    //     else{
    //         t[i+1] = elt;
    //         break;
    //     }
    // }
    // (*size)++;

    int i;
    for (i = *size - 1; i >= 0; i--) {
        if(elt < t[i]) {
            t[i + 1] = t[i];
        } 
        else{       
            break;
        }
    }
    t[i + 1] = elt;
    
    (*size)++;
}

int find_sorted(int t[], int size, int elt) {
    /*
    * Write this function!
    */

    int low=0, high=size-1;

    int mid;

    while(low <= high){
            mid=low+(high-low)/2;
            if(t[mid] == elt){
                return 1;
            }
            else if(t[mid] > elt){
                high=mid-1;
            }
            else{
                low=mid+1;
            }
    }

    return 0;
}
