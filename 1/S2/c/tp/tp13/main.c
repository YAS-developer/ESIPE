#include <stdio.h>
#include "bit.h"




int main(int argc, char ** argv){
    
    unsigned long int nb = 1;

    /* printf("%d\n", bit_value_ULI(nb, 1));*/
    
    set_positive_bit_ULI(&nb, 1);
    print_ULI(nb);

    return 0;
}