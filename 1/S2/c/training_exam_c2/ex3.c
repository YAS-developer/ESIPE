#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Define the People structure
typedef struct people {
    char* first_name;
    char* last_name;
    int age;
} People;

// Define the Filter function type
typedef int (*Filter)(People* guy, int arg);

// Function to print a People structure
void print_people(People* person) {
    printf("Firstname: %s, Lastname: %s, Age: %d\n", person->first_name, person->last_name, person->age);
}

// Filter function to check if the last name starts with a specific letter
int last_name_starts_with(People* guy, int arg) {
    return guy->last_name[0] == (char)arg;
}

// Filter function to check if age is at least a specified minimum
int age_at_least(People* guy, int min_age) {
    return guy->age >= min_age;
}

// Filter function to check if the last name starts with 'P' and age is at least 42
int last_name_starts_with_P_and_age_at_least_42(People* guy, int arg) {
    (void)arg; // Unused parameter
    return guy->last_name[0] == 'P' && guy->age >= 42;
}

// Function to filter and print people
void filter_and_print(People* tab, int nmemb, Filter fct, int arg_filter) {
    for (int i = 0; i < nmemb; i++) {
        if (fct(&tab[i], arg_filter)) {
            print_people(&tab[i]);
        }
    }
}

int main() {
    // Example people array
    People all_people[] = {
        {"John", "Doe", 30},
        {"Jane", "Smith", 25},
        {"Alice", "Davis", 28},
        {"Bob", "Johnson", 35},
        {"Paul", "Parker", 45},
        {"Peter", "Pan", 42},
        {"Phillip", "Pine", 41},
        {"Perry", "Parson", 50},
        {"Patrick", "Peterson", 17}
    };
    int nb_people = sizeof(all_people) / sizeof(all_people[0]);

    // Call filter_and_print to display people whose last name starts with 'D'
    filter_and_print(all_people, nb_people, last_name_starts_with, 'D');

    // Call filter_and_print to display people whose age is at least 18
    filter_and_print(all_people, nb_people, age_at_least, 18);

    // Call filter_and_print to display people whose last name starts with 'P' and age is at least 42
    filter_and_print(all_people, nb_people, last_name_starts_with_P_and_age_at_least_42, 0);

    return 0;
}
