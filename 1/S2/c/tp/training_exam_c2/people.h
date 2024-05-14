#include <stdlib.h>


typedef struct people{
    char *first_name;
    char *last_name;
    int age;
}People;



People* allocate_people(char* first_name, char* last_name, int age);

void fwrite_array_people(File* out, People* tab, int nb people);