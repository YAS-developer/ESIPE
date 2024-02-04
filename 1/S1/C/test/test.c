# include <stdio.h>
#include <stdlib.h>

unsigned int wrong_way(unsigned int n){

   unsigned int reversed = 0;
    while (n > 0) {
        reversed = reversed * 10 + n % 10;
        n /= 10;
    }
    return reversed;
}
int main (int argc , char * argv [] ) {
    int sum=0;
    // for(int i=1; i < argc; i++)
    //     if(atoi(argv[i]) != 0)
    //         sum+=atoi(argv[i]);

    // printf("%d\n", sum);
    printf("%d\n", wrong_way(123));
}