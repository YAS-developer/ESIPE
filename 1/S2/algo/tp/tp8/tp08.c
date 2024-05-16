#include "tree.h"
#include "visualtree.h"  
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <limits.h>


void display_menu() {
    printf("Choix possibles:\n");
    printf("s : construire un nouvel arbre à partir d’une suite d’entiers\n");
    printf("a : construire un arbre avec un nombre d’entiers aléatoires\n");
    printf("i : insérer un élément dans l’arbre\n");
    printf("f : faire une recherche dans l’arbre\n");
    printf("d : afficher les entiers de l’arbre en ordre croissant\n");
    printf("b : verifie si c'est un arbre binaire de recherche\n");
    printf("r : insérer N entiers aléatoires et distincts\n");
    printf("q : terminer le programme\n");
}

node* build_random_tree(int num_nodes) {
    node *root = NULL;
    srand(time(NULL));
    for (int i = 0; i < num_nodes; i++) {
        int value = rand() % 1000; // Random values between 0 and 999
        root = insert_bst(root, value);
    }
    return root;
}


void measure_insertion_time(int N, int is_random) {
    node *root = NULL;
    clock_t start, end;
    double time_used;

    if (is_random) {
        printf("Inserting %d random elements:\n", N);
        start = clock();
        root = insert_random_elements(N);
        end = clock();
    } else {
        printf("Inserting %d sequential elements:\n", N);
        start = clock();
        root = insert_sequential_elements(N);
        end = clock();
    }

    time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
    int tree_height = height(root);
    printf("Time used: %.2f seconds\n", time_used);
    printf("Tree height: %d\n", tree_height);
    free_tree(root);
}

void measure_insertions() {
    int N_values[] = {10000, 20000, 30000, 40000, 50000};
    int num_tests = sizeof(N_values) / sizeof(N_values[0]);

    for (int i = 0; i < num_tests; i++) {
        measure_insertion_time(N_values[i], 1); // Random elements
        measure_insertion_time(N_values[i], 0); // Sequential elements
    }
}

int main() {

    // printf("Please enter the pre-order tree sequence (0 for NULL nodes):\n");
    // node *root = scan_tree();


    // Optionally, use the visualtree functions to visualize the constructed tree
    // if (root == NULL) {
    //     fprintf(stderr, "L'arbre n'a pas ete cree");
    //     exit(EXIT_FAILURE);
    // } 

    // // Test the find_bst function
    // int i;
    // for (i = 0; i < 100; i++) {
    //     if (find_bst(root, i)) {
    //         printf("%d ", i);
    //     }
    // }
    // printf("\n");


    // Insert new elements into the BST
    // int elements_to_insert[] = {10, 5, 15, 3, 8, 12, 18};
    // int num_elements = sizeof(elements_to_insert) / sizeof(elements_to_insert[0]);
    // for (int i = 0; i < num_elements; i++) {
    //     root = insert_bst(root, elements_to_insert[i]);
    // }




    // Display the tree in-order to verify the insertion
    // printf("Tree in-order after insertions:\n");
    // display_infix(root);
    // printf("\n");

  


    // Freeing the tree to avoid memory leaks
    // free_tree(root);

    node *root = NULL;
    char choice;
    int value;

   
    while (1) {
        display_menu();
        printf("Entrez votre choix: ");
        scanf(" %c", &choice);

        switch (choice) {
            case 's':
                printf("Entrez la suite d'entiers pour construire l'arbre (terminée par 0 pour NULL):\n");
                root = scan_tree();
                write_tree(root);
                break;

            case 'a':
                printf("Entrez le nombre d'entiers aléatoires: ");
                int num_nodes;
                scanf("%d", &num_nodes);
                root = build_random_tree(num_nodes);
                write_tree(root);
                break;

            case 'i':
                printf("Entrez l'élément à insérer: ");
                scanf("%d", &value);
                root = insert_bst(root, value);
                write_tree(root);
                break;

            case 'f':
                printf("Entrez l'élément à rechercher: ");
                scanf("%d", &value);
                if (find_bst(root, value)) {
                    printf("L'élément %d est présent dans l'arbre.\n", value);
                } else {
                    printf("L'élément %d n'est pas présent dans l'arbre.\n", value);
                }
                break;

            case 'd':
                printf("Arbre en ordre croissant: ");
                display_infix(root);
                printf("\n");
                break;

            case 'b':
                if (is_bst(root)) {
                    printf("L'arbre est un arbre binaire de recherche.\n");
                } else {
                    printf("L'arbre n'est pas un arbre binaire de recherche.\n");
                }
                break;

            case 'r':
                measure_insertions();
                break;

            case 'q':
                free_tree(root);
                printf("Programme terminé.\n");
                return 0;

            default:
                printf("Choix invalide. Veuillez réessayer.\n");
                break;
        }
    }



    return 0;
}
