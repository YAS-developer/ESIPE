#include "sudoku.h"
#include <stdio.h>
#include <stdlib.h>
#define REGION_SIZE 3


int is_legal_move(Board *grid, int row, int col, int value) {
    // Vérifier si la valeur est dans la plage permise (1 à SIZE)
    if (value < 1 || value > SIZE) {
        return 0;  // Mouvement illégal
    }

    // Vérifier si la valeur est déjà présente dans la ligne
    for (int j = 0; j < SIZE; j++) {
        if (grid->values[row][j] == value) {
            return 0;  // Mouvement illégal
        }
    }

    // Vérifier si la valeur est déjà présente dans la colonne
    for (int i = 0; i < SIZE; i++) {
        if (grid->values[i][col] == value) {
            return 0;  // Mouvement illégal
        }
    }

    // Vérifier si la valeur est déjà présente dans la région 3x3
    int region_start_row = row - row % REGION_SIZE;
    int region_start_col = col - col % REGION_SIZE;

    for (int i = 0; i < REGION_SIZE; i++) {
        for (int j = 0; j < REGION_SIZE; j++) {
            if (grid->values[region_start_row + i][region_start_col + j] == value) {
                return 0;  // Mouvement illégal
            }
        }
    }

    // Mouvement légal
    return 1;
}

int is_board_complete_and_correct(Board *grid) {
    // Vérifier si la grille est pleine
    if (!is_board_full(grid)) {
        return 0;  // La grille n'est pas complète
    }

    // Vérifier si toutes les lignes sont correctes
    for (int i = 0; i < SIZE; i++) {
        int row_values[SIZE] = {0};

        for (int j = 0; j < SIZE; j++) {
            int value = grid->values[i][j];

            // Vérifier si la valeur est dans la plage permise (1 à SIZE)
            if (value < 1 || value > SIZE) {
                return 0;  // La valeur n'est pas dans la plage permise
            }

            // Vérifier si la valeur est unique dans la ligne
            if (row_values[value - 1] == 1) {
                return 0;  // La valeur est répétée dans la ligne
            }

            row_values[value - 1] = 1;
        }
    }

    // Vérifier si toutes les colonnes sont correctes
    for (int j = 0; j < SIZE; j++) {
        int col_values[SIZE] = {0};

        for (int i = 0; i < SIZE; i++) {
            int value = grid->values[i][j];

            // Vérifier si la valeur est unique dans la colonne
            if (col_values[value - 1] == 1) {
                return 0;  // La valeur est répétée dans la colonne
            }

            col_values[value - 1] = 1;
        }
    }

    // Vérifier si toutes les régions 3x3 sont correctes
    for (int region_start_row = 0; region_start_row < SIZE; region_start_row += REGION_SIZE) {
        for (int region_start_col = 0; region_start_col < SIZE; region_start_col += REGION_SIZE) {
            int region_values[SIZE] = {0};

            for (int i = 0; i < REGION_SIZE; i++) {
                for (int j = 0; j < REGION_SIZE; j++) {
                    int value = grid->values[region_start_row + i][region_start_col + j];

                    // Vérifier si la valeur est unique dans la région
                    if (region_values[value - 1] == 1) {
                        return 0;  // La valeur est répétée dans la région
                    }

                    region_values[value - 1] = 1;
                }
            }
        }
    }

    // La grille est complète et correcte
    return 1;
}




void initialize_board(Board *grid) {
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            grid->values[i][j] = 0;
        }
    }
}

void print_board(Board *grid) {
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            printf("%d ", grid->values[i][j]);
        }
        printf("\n");
    }
}


int is_board_full(Board *grid) {
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            if (grid->values[i][j] == 0) {
                return 0;  // La grille n'est pas pleine, une cellule vide a été trouvée
            }
        }
    }
    return 1;  // La grille est pleine, toutes les cellules sont remplies
}

int solve_board(Board *grid) {
    // À compléter : résoudre la grille de Sudoku
    // Retourner 1 si solution trouvée, 0 sinon
    return 0;
}

void load_board_from_file(Board *grid, const char *filename) {
    FILE *file = fopen(filename, "r");
    if (file == NULL) {
        fprintf(stderr, "Erreur lors de l'ouverture du fichier %s\n", filename);
        exit(EXIT_FAILURE);
    }

    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            if (fscanf(file, "%d", &(grid->values[i][j])) != 1) {
                fprintf(stderr, "Erreur de lecture dans le fichier %s\n", filename);
                exit(EXIT_FAILURE);
            }
        }
    }

    fclose(file);
}

int is_vacant_cell(const Board *grid, int row, int col) {
    return (grid->values[row][col] == 0);
}


void set_number(Board *grid, int row, int col, int number) {
    grid->values[row][col] = number;
}