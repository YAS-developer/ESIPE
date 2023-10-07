#include <stdio.h>
#include <stdlib.h>


int puissN(int a, int n){
    int initial = a;
    while(n > 1){
        a=a*initial;
        n--;
    }
    return a;
}

int puissanceN(int a, int init, int n){
    if(n == 1){
        return a;
    }
    return puissanceN(a*init, init, n-1);
}


int main(int argc, char** argv){

    // if(argc < 2 ||  atoi(argv[1]) == 0 || argv[1] == 0  ){
    //     fprintf(stderr,"\n\nVeuillez mettre un entier superieur a 0 comme ci-dessous :\n\n"
    //                     "%s [entier]\n"
    //                     ,argv[0]);
    //     return EXIT_FAILURE;  
    // }

    // int a = atoi(argv[1]);
    // int n = atoi(argv[2]);

    printf("%d\n",puissN(2,10));

    printf("%d\n",puissanceN(2,2,10));

    return 0;
}