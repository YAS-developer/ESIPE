#include <stdio.h>
#include <stdlib.h>


void print_var(int n){
    printf("Value of variable: %d\n", n);
}

void print_pointer(int* p){
    printf("Pointer address : %p and Pointed  value  : %d\n", p, *p) ;
}

void set_pointer(int *p, int n){
    p = &n;
}


int main(int argc, char** argv){
    int a;

    int* p = &a;

    print_var(a);
    a = 53;
    print_var(a);

    print_pointer(p);

    set_pointer(p, 42);

    print_pointer(p);
    printf_var(a);


    return 0;
}