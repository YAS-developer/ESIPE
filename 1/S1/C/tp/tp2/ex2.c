#include <stdio.h>
#include <stdlib.h>

int main(int argc, char** argv){

    if(argc < 3 || (atoi(argv[1]) == 0 && argv[1] != 0) || (atoi(argv[2]) == 0 && argv[1] != 0) ){
        fprintf(stderr, "\n\nVeuillez entrer 2 entiers comme ci-dessous: \n\n%s entier1 entier2\n\n", argv[0]);
        return EXIT_FAILURE;
    }

    int a = atoi(argv[1]);
    int b = atoi(argv[2]);
    
    printf("resultat: %d\n", a+b);

}