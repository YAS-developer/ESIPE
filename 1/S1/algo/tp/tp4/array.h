#ifndef ARRAY_H
#define ARRAY_H

int* create_array(int size);

void print_array(int t[], int size);

void copy_array(int src[], int dst[], int size);

void fill_random_array(int t[], int size, int max_value);

void fill_random_permutation(int t[], int size);

#endif /* ARRAY_H */
