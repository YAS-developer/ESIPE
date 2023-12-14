#include <stdio.h>
#include <stdlib.h>
#define N 3


void print_buffer(int buffer[],int max){
    int i;
    printf("--> [");
    for(i=0;i<max;i++){
        if(i==max-1){
            printf("%d",buffer[i]);
        }
        else(printf("%d,",buffer[i]));
    }
    printf("]\n");
}

void permutations(int buffer[], int current, int max){
    int i;
    if(current > max){  
        print_buffer(buffer,max);
    }
    for(i=0;i<max;i++){
        if(buffer[i]==0){
            buffer[i]=current; /*avant appel récursif*/ 
            permutations(buffer,current+1,max);
            buffer[i]=0; /*après appel récurisif, backtracking = enlever pour mettre ailleurs*/
        }
    }

}

/* Display indentation following the depth of recursion */
void make_space(int n){
  int i;
  for (i=0 ; i<n ; i++)
    printf("  ");
}


int main(int argc, char* argv[]){
    int buffer[N];
    int i;
    for(i=0;i<N;i++){
        buffer[i]=0;
        printf("Buffer[%d] : %d\n",i,buffer[i]);
    }

    permutations(buffer,1,N);
    return 0;
}