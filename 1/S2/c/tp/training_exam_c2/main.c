#include <stdio.h>
#include "people.h"


int main(int argc, char** argv){

    People *p = allocate_people("Taio", "Le coco", 20);

    printf("%s %s agé de %d\n", p->first_name, p->last_name, p->age);

    return 0;
}