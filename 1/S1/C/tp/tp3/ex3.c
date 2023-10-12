#include <stdio.h>
#include <string.h> 
#include <stdlib.h>
#include <math.h>



int convert(char* string){
    char alphabet[26]= { 0 };
    int i = 0;

    // affectation de alphabet
    
    for(char c = 'a'; c != 'z'+1; c++){
        alphabet[i] = c;
        i++;
    }

   
    double resultat = 0;
    
    //abc

    for(int i = strlen(string)-1 ; i >=0; i--){
        for(int j = 0; j < 26; j++){
            if(string[i] == alphabet[j]){
                resultat = resultat + j * pow(26,i);
                // printf("lettre: %d puissance: %d\n", j, i);
            
            }
        }
    }
    

    return (int)resultat;
}



int main(int argc, char** argv){

    if(argc < 2){
        fprintf(stderr, "\n\nVeuillez entrer un entier comme ci-dessous: \n\n%s entier\n\n", argv[0]);
        return EXIT_FAILURE;
    }

    // convert(argv[1]);

    printf("%d\n,",convert(argv[1]));
    
    return 0;
}