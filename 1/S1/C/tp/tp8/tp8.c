#include <stdio.h>
#include <stdlib.h>
#define SIZE 2000000




int syracuse(long unsigned int nb, int vol, long unsigned int* tab){
    // printf("%d ", nb);
    long unsigned int next_nb=0;
    if(nb == 1){
        return vol;
    }
    else if (nb%2 == 0){ 
        if(tab[nb] == -1){
            tab[nb]=nb/2;
            printf("calculé: %ld ", tab[nb]);
            next_nb=tab[nb];
        }
        else{
            next_nb=tab[nb];
            printf("non calculé: %ld ", next_nb);
        }
        return syracuse(next_nb, vol+1, tab);
    }
    else{
        if(tab[nb] == -1){
            tab[nb]=3*nb+1  ;
            printf("calculé: %ld ", tab[nb]);
            next_nb=tab[nb];
        }
        else{
            next_nb=tab[nb];
            printf("non calculé: %ld ", next_nb);
        }

        return syracuse(tab[nb], vol+1, tab);
    }
}


int main(int argc, char **argv){
    long unsigned int* tab= malloc(sizeof(long unsigned int)*SIZE);

    for(int i=0; i<SIZE; i++){
        tab[i]=-1;
    }   
    
    if(argc<2){
        fprintf(stderr, "Veuillez utilise ce format: %s numéro\n", argv[0]);
    }

    int nb=atoi(argv[1]);
    printf("\n%d ", nb);
    
    int nb_vol=syracuse(nb, 0, tab);

    printf("\nfly length: %d\n", nb_vol);

    int nb_vol2=syracuse(27, 0, tab);

    printf("\nfly length: %d\n", nb_vol2);



    free(tab);


}