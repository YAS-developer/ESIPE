#ifndef SUDOKU_H
#define SUDOKU_H

#define SIZE 9

typedef struct {
    int values[SIZE][SIZE];
} Board;


int is_board_complete_and_correct(Board *grid);

// Fonction pour initialiser une grille de Sudoku
void initialize_board(Board *grid);

// Fonction pour afficher la grille de Sudoku dans la console (à des fins de débogage)
void print_board(Board *grid);

// Fonction pour vérifier si un mouvement est légal
int is_legal_move(Board *grid, int row, int col, int value);

// Fonction pour vérifier si la grille est pleine
int is_board_full(Board *grid);

// Fonction pour résoudre la grille de Sudoku (à compléter)
int solve_board(Board *grid);

void load_board_from_file(Board *grid, const char *filename);

int is_vacant_cell(const Board *grid, int row, int col);
void set_number(Board *grid, int row, int col, int number);


#endif

