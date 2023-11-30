#include <stdio.h>
#include <stdlib.h>


int* fill_array(void){
    int size = 0;
    printf("Quelle est la taille que vous souhaitez allouer au tableau d'entiers ?\n");
    scanf("%d", &size);
    
    int* tab = malloc(sizeof(int)*size);

    printf("\nVeuillez entrez les valeurs du tableaux");

    for(int i=0; i<size; i++){
       printf("\ntab[%d]: ", i); 
       scanf("%d", tab+i);
    }

    for(int i=0; i<size; i++){
       printf("\ntab[%d]=%d", i, tab[i]); 
    }
    printf("\n");
    return tab;
}

void print_array(int* array){
    
}


int main(int argc, char* argv[]){
    int* array = fill_array();
}