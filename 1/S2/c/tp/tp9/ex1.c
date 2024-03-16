#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>






int main(int argc, char **argv){
    

    int size = atoi(argv[1]);
    
    int *tab = malloc(sizeof(int) * size);
    bool b = true;
    
    for(int i=0; i<=size; i++){
        if(b && i < size){
            tab[i]=i;
        }
        else if(b && i == size){
            b=false;
            i=0;
        }
        else if(!b && i < size){
            printf("tab[%d]=%d\n",i, tab[i]);
        }
    } 

    free(tab);

    return 0;
}