#include "avl.h"
#include "visualtree.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <limits.h>

void display_menu() {
    printf("Choix possibles:\n");
    printf("i : insérer des éléments\n");
    printf("t : chronométrer l'insertion d'éléments aléatoires\n");
    printf("c : trouver combien d'éléments peuvent être insérés dans un nombre donné de secondes\n");
    printf("s : supprimer un élément\n");
    printf("q : terminer le programme\n");
}

/* Function to measure time and height for inserting random elements */
void measure_insertion_random(int N, node **root) {
    int *array = (int *)malloc(N * sizeof(int));
    fill_random_permutation(array, N);

    clock_t start = clock();
    for (int i = 0; i < N; i++) {
        *root = insert_avl(*root, array[i]);
    }
    clock_t end = clock();

    double time_spent = (double)(end - start) / CLOCKS_PER_SEC;
    int tree_height = *root ? (*root)->height : 0;

    printf("Temps d'insertion de %d éléments aléatoires: %f secondes\n", N, time_spent);
    printf("Hauteur de l'arbre: %d\n", tree_height);
    free(array);
    write_tree(*root); // Visualize the tree after insertion
}

/* Function to measure time and height for inserting ordered elements */
void measure_insertion_ordered(int N, node **root) {
    clock_t start = clock();
    for (int i = 1; i <= N; i++) {
        *root = insert_avl(*root, i);
    }
    clock_t end = clock();

    double time_spent = (double)(end - start) / CLOCKS_PER_SEC;
    int tree_height = *root ? (*root)->height : 0;

    printf("Temps d'insertion de %d éléments ordonnés: %f secondes\n", N, time_spent);
    printf("Hauteur de l'arbre: %d\n", tree_height);
    write_tree(*root); // Visualize the tree after insertion
}

/* Function to measure the number of elements that can be inserted in a given time */
void measure_insertion_time(int seconds, node **root) {
    int increment = 10000;
    int num_elements = increment;
    double total_time_spent = 0;
    clock_t start;

    start = clock();

    while (total_time_spent < seconds) {
        int *array = (int *)malloc(num_elements * sizeof(int));
        fill_random_permutation(array, num_elements);

        for (int i = 0; i < num_elements; i++) {
            *root = insert_avl(*root, array[i]);
        }

        total_time_spent = (double)(clock() - start) / CLOCKS_PER_SEC;

        free(array);

        if (total_time_spent < seconds) {
            num_elements += increment;
        }
    }

    printf("Nombre d'éléments insérés en %d secondes: %d\n", seconds, num_elements);
    write_tree(*root); // Visualize the tree after insertion
}

int main() {
    char choice;
    node *root = NULL; // Keeping the tree root as a global variable for simplicity

    while (1) {
        display_menu();
        printf("Entrez votre choix: ");
        scanf(" %c", &choice);

        switch (choice) {
            case 'i':
                {
                    int num_elements;
                    printf("Entrez le nombre d'éléments à insérer: ");
                    scanf("%d", &num_elements);
                    
                    int *elements = (int *)malloc(num_elements * sizeof(int));
                    printf("Entrez les éléments à insérer: ");
                    for (int i = 0; i < num_elements; i++) {
                        scanf("%d", &elements[i]);
                    }

                    for (int i = 0; i < num_elements; i++) {
                        root = insert_avl(root, elements[i]);
                    }

                    free(elements);
                    write_tree(root); // Visualize the tree after insertion
                }
                break;

            case 't':
                {
                    int num_elements;
                    printf("Entrez le nombre d'éléments à insérer (multiple de 10000): ");
                    scanf("%d", &num_elements);

                    measure_insertion_random(num_elements, &root);
                    measure_insertion_ordered(num_elements, &root);
                }
                break;

            case 'c':
                {
                    int seconds;
                    printf("Entrez le nombre de secondes: ");
                    scanf("%d", &seconds);

                    measure_insertion_time(seconds, &root);
                }
                break;

            case 's':
                {
                    int key;
                    printf("Entrez l'élément à supprimer: ");
                    scanf("%d", &key);

                    root = remove_avl(root, key);
                    printf("Élément %d supprimé.\n", key);
                    write_tree(root); // Visualize the tree after deletion
                }
                break;

            case 'q':
                printf("Programme terminé.\n");
                free_tree(root);
                return 0;

            default:
                printf("Choix invalide. Veuillez réessayer.\n");
                break;
        }
    }

    return 0;
}
