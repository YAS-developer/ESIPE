#include<stdio.h>
#include "stack.h"


int main(int arc, char** argv){
    stack_init();
   
    // for(int i=15; i > 0; i--){
    //     stack_push(i);
    // }

    // for(int i=0; i < 16; i++){
    //     printf("%d\n", stack_pop());
    // }



    // stack_display();

    // for(int i=0; i < 15; i++){
    //     printf("%d\n", stack_top());
    //     stack_pop();
    // }

    printf("%d\n", stack_get_element(13));
    // stack_display();
}