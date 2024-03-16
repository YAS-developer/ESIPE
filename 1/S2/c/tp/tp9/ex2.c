#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>






int main(int argc, char **argv){
    

    int x,y;
    bool alloue = true, affiche = false;
    char letter='a';
    int incr_letter=0;
    printf("Donnez deux dimensions entières : ");
    scanf("%d %d", &x, &y);
    
    char ** tab= malloc(sizeof(*tab) * x);

    for(int i=0; i <= x; i++){
        if(alloue && i<x){
            tab[i] = malloc(sizeof(*tab) * y);
        }
        else if(alloue && i == x){
            alloue=false;
            i=0;
        }
        else if(!alloue && i<x){
            for(int j=0; j<=y; j++){
                if(affiche){
                    printf("%c ", tab[i][j]);
                }
                else if(i==x-1 && j==y){
                    affiche = true;
                    i=0;
                }
                else if(letter > 'z' && j < y){
                    letter='a';
                }
                else if(j < y && !affiche){
                    tab[i][j]=letter;
                    letter++;
                }
            }
            incr_letter++;
            letter='a'+incr_letter;
            if(affiche){
                printf("\n");
            }
        }

    }

    for(int i=0; i<x; i++)
        free(tab[i]);
    free(tab);

    return 0;
}