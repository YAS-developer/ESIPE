#include <stdio.h>

void swap(int *a, int *b){
    int tmp=*a;
    *a=*b;
    *b=tmp;
}

int max_occ(int t[], int size){
    int max_occ=0, tmp=0;

    for(int i=0; i<size; i++){
        for(int j=0; j<size; j++){
            if(t[i] == t[j]){
                tmp++;
            }
        }
        if(tmp > max_occ){
            max_occ=tmp;
            
        }
        tmp=0;
    }
    return max_occ;
}

void print_tab(int* t, int size){
    for(int i=0; i<size; i++)
        printf("t[%d]=%d\n", i, t[i]);
}

void insert_sorted(int t[], int *size, int elt){
    t[(*size)]=elt;
    for(int i=(*size)-1; i>=0; i--){
        if(t[i] > t[i+1]){
            swap(&t[i], &t[i+1]);
        }
    }
    (*size)++;
}

int main(int argc, char** argv){
    int t[4]={-2,0,3,4};
    int size = sizeof(t)/4;
    

    insert_sorted(t, &size, -3);
    print_tab(t, size);
    
    // printf("%d\n", max_occ(t, sizeof(t)/4));

}