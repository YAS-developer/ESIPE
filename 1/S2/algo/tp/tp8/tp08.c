#include "tree.h"
#include "visualtree.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <limits.h>

void display_menu() {
    printf("Choix possibles:\n");
    printf("s : construire un nouvel arbre à partir d’une suite de mots\n");
    printf("a : construire un arbre avec un nombre de mots aléatoires\n");
    printf("i : insérer un mot dans l’arbre\n");
    printf("f : faire une recherche dans l’arbre\n");
    printf("d : afficher les mots de l’arbre en ordre lexicographique\n");
    printf("r : afficher les mots entre deux mots donnés\n");
    printf("b : verifier si c'est un arbre binaire de recherche\n");
    // printf("h : comparer la hauteur de l'arbre avec la hauteur idéale\n");
    printf("m : supprimer le plus petit mot de l'arbre\n");
    printf("x : supprimer un mot de l'arbre\n");
    printf("u : trouver les mots uniques entre deux fichiers\n");
    printf("q : terminer le programme\n");
}

int main() {
    node *root = NULL;
    char choice;
    char word[MAX_WORD_LENGTH + 1];

    FILE *f = fopen("text.txt", "r");
    if (f == NULL) {
        fprintf(stderr, "Could not open file.\n");
        return 1;
    }

    while (fscanf(f, "%s", word) != EOF) {
        root = insert_bst(root, word);
    }
    fclose(f);

    // int n = count_nodes(root);

    while (1) {
        display_menu();
        printf("Entrez votre choix: ");
        scanf(" %c", &choice);

        switch (choice) {
            case 'd':
                printf("Arbre en ordre lexicographique: ");
                display_infix(root);
                printf("\n");
                break;

            case 'r':
                {
                    char start[MAX_WORD_LENGTH + 1], end[MAX_WORD_LENGTH + 1];
                    printf("Entrez le premier mot: ");
                    scanf("%s", start);
                    printf("Entrez le deuxième mot: ");
                    scanf("%s", end);
                    printf("Mots entre %s et %s: ", start, end);
                    display_range(root, start, end);
                    printf("\n");
                }
                break;

            case 'b':
                if (is_bst(root)) {
                    printf("L'arbre est un arbre binaire de recherche.\n");
                } else {
                    printf("L'arbre n'est pas un arbre binaire de recherche.\n");
                }
                break;

            // case 'h':
            //     compare_height(root, n);
            //     break;

            case 'm':
                {
                    node *min_node = NULL;
                    root = extract_min_bst(root, &min_node);
                    if (min_node != NULL) {
                        printf("Noeud minimum extrait: %s\n", min_node->word);
                        free(min_node);
                    } else {
                        printf("L'arbre est vide.\n");
                    }
                    write_tree(root);
                }
                break;

            case 'x':
                printf("Entrez le mot à supprimer: ");
                scanf("%s", word);
                root = remove_bst(root, word);
                write_tree(root);
                break;

            case 'u':
                {
                    char file1[MAX_WORD_LENGTH + 1], file2[MAX_WORD_LENGTH + 1];
                    printf("Entrez le premier fichier: ");
                    scanf("%s", file1);
                    printf("Entrez le deuxième fichier: ");
                    scanf("%s", file2);
                    node *root1 = read_file_to_bst(file1);
                    node *root2 = read_file_to_bst(file2);
                    printf("Mots dans %s mais pas dans %s: ", file1, file2);
                    find_unique_words(root1, root2);
                    printf("\n");
                    free_tree(root1);
                    free_tree(root2);
                }
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
