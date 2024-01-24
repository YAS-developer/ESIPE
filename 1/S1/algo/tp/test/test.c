

#include <stdio.h>

void swap(int *a, int *b){
    int tmp= *a;
    *a = *b;
    *b = tmp; 
}


void tri(int t[], int size){
    int i,j;

    for(int i=0; i<size; i++){
        for(int j=i+1; j<size;j++){
            if(t[j] > t[i]){
                swap(&t[i], &t[j]);
            }
        }
    }
}

void printTab(int t[], int size){
    for(int i=0; i<size; i++){
        printf("tab[%d]: %d ", i, tab[i]);
    }
    printf("\n");
}


int main(int argc, char** argv){
    int tab[]={
        
    }
    return 0;
}
