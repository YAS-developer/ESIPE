#include <stdio.h>
#include <stdlib.h>


int affN(int n, int status, int max){
    
    if(n == max && status != 0){
        printf("\n");
        return EXIT_SUCCESS;
    }
    else if(status != 0){
        n++;
        printf("%d ",n);
        affN(n,1,max);
    }
    else if(n == 0){
        affN(n,1,max);
    }
    else{
        printf("%d ",n);
        n--;
        affN(n,0, max);
    }

    return 0;
}


int main(int argc, char** argv){

    if(argc < 2 || (atoi(argv[1]) == 0 && argv[1] != 0) ){
        fprintf(stderr, "\n\nVeuillez entrer un entier comme ci-dessous: \n\n%s entier1\n\n", argv[0]);
        return EXIT_FAILURE;
    }
    
    int n = atoi(argv[1]);
    int choix = 0;

    printf("\n\nQue souhaitez vous utiliser:\n\n"
        "Boucle: 1\n"
        "Méthode récursive: 2\n\n");
    
    while(1)
    {
        scanf("%d", &choix);
        if(choix == 1 || choix == 2){
            break;
        }
        printf("Veuillez renseigner le chiffre 1 ou 2\n\n");
    }
    
    switch (choix)
    {
    case 1:
        for(int i = n; i >= 0; i--){
            if(i != 0){
                printf("%d ", i);
            }
            else{
                for(int j = 1; j <= n; j++){
                    printf("%d ", j);
                }
            }
        }   
        break;

    case 2:
        affN(n, 0, n);
        break;    

    default:
        break;
    }


    printf("\n");

    return 0;
}
