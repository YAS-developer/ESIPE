#include <stdio.h>
#include <stdlib.h>


typedef struct people {
    char *firstname;
    char *lastname;
    int age;
} People;

// Function to allocate and initialize a People structure
People* allocate_people(char* first_name, char* last_name, int age) {
    // Allocate memory for the People structure
    People* person = (People*)malloc(sizeof(People));
    if (person == NULL) {
        perror("Failed to allocate memory for People structure");
        exit(EXIT_FAILURE);
    }

    // Allocate memory for the first_name and last_name strings
    person->first_name = (char*)malloc(strlen(first_name) + 1);
    person->last_name = (char*)malloc(strlen(last_name) + 1);
    if (person->first_name == NULL || person->last_name == NULL) {
        perror("Failed to allocate memory for names");
        exit(EXIT_FAILURE);
    }

    // Copy the provided names into the allocated memory
    strcpy(person->first_name, first_name);
    strcpy(person->last_name, last_name);

    // Set the age
    person->age = age;

    // Return the pointer to the newly allocated People structure
    return person;
}

void free_people(People* person) {
    free(person->firstname);
    free(person->lastname);
    free(person);
}

void fwrite_array_people(FILE* out, People* tab, int nb_people) {
    for (int i = 0; i < nb_people; i++) {
        fprintf(out, "%s %s %d\n", tab[i].first_name, tab[i].last_name, tab[i].age);
    }
}


// Function to read people from a file using fscanf
void read_people_from_file(const char* filename) {
    FILE* file = fopen(filename, "r");
    if (file == NULL) {
        fprintf(stderr, "Could not open file %s\n", filename);
        exit(1);
    }

    char first_name[100], last_name[100];
    int age;

    while (fscanf(file, "Firstname: %99[^,], Lastname: %99[^,], Age: %d\n", first_name, last_name, &age) == 3) {
        People* person = allocate_people(first_name, last_name, age);
        printf("Read person - Firstname: %s, Lastname: %s, Age: %d\n", person->first_name, person->last_name, person->age);
        free_people(person);
    }

    fclose(file);
}



// Function to find the length of the longest contiguous sequence of 1s starting from the most significant bit
unsigned int highest_bit_sequence_length(unsigned int x) {
    // If the number is 0, return 0
    if (x == 0) {
        return 0;
    }

    // Variable to store the length of the current sequence of 1s
    unsigned int current_length = 0;
    // Variable to store the maximum length found
    unsigned int max_length = 0;

    // Traverse each bit from the most significant to the least significant
    for (int i = 31; i >= 0; i--) {
        if ((x >> i) & 1) {
            // If the current bit is 1, increment the current sequence length
            current_length++;
            // Update the maximum length if the current length is greater
            if (current_length > max_length) {
                max_length = current_length;
            }
        } else {
            // If the current bit is 0, reset the current sequence length
            current_length = 0;
        }
    }

    return max_length;
}


void toBinary(int a){
    for(int i= sizeof(a)*4-1; i >=0; i--){
        printf("%d", (a >> i) & 1 );
    }
}


int main(int argc, char **argv){
    int a =0;
    scanf("%d", &a);

    // printf("%d\n", sizeof(a));
    toBinary(a);




}