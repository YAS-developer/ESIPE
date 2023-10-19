#include "stack.h"
#include <stdio.h>

/* This module proposesasing le global and static stack */
 
 static Stack stack ;

 /* Initialize correctly the stack */
 void stack_init( void ) {
    stack.size= 0;
 }

 int stack_size(void){
    return stack.size;
 }

int stack_is_empty(void){
    if(stack.size > 0){
        return 1;
    }
    return 0;
}


int stack_top(void){
    return stack.values[stack.size];
}

int stack_pop(void){

    int old_size = stack.size;
    if(stack.size > 0){
        stack.size--;
        return stack.values[old_size];
    }
    else{
        fprintf(stderr,"La pile est est vide.\n");
        return 0;
    }
    
}

void stack_push(int n){
     printf("\n\n");
    if(stack.size<15){
        stack.values[stack.size] = n;
        printf("La valeur %d à bien été ajouter.\n", n);
        if(stack.size != 14){
            stack.size++;
        }
    }
    else{
        printf("\n\n");
        printf("La valeur %d n'a pas été ajouter car la pile est pleine.\n", n);
    }
} 

void stack_display(void){
    printf("\n\nDisplay:\n\n");
    int i;
    for(i=0; i<stack.size; i++){
        printf("stack.values[%d]: %d\n", i, stack.values[i]);
    }
    printf("\n\n");
}

int stack_get_element(int index){
    if(index >= 0 && index < stack.size){
        return stack.values[index];
    }
    else{
        fprintf(stderr,"Veuillez rentrez un index compris entre 0 et la taille de la pile : %d\n", stack.size);
        return 0;
    }
}