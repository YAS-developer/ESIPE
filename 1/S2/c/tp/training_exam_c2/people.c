#include <stdio.h>
#include "people.h"



People* allocate_people(char* first_name, char* last_name, int age){
    People* p = (People*) malloc(sizeof(People));
    
    if(p == NULL){
        fprintf(stderr, "People not allowed");
        exit(EXIT_FAILURE);
    }

    p->first_name = first_name;
    p->last_name = last_name;
    p->age = age;

    return p;
}


void fwrite_array_people(File* out, People* tab, int nb_people){

}
