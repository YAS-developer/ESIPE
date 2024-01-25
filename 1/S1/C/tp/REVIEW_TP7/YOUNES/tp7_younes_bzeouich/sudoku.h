#ifndef __SUDOKU__
#define __SUDOKU__

typedef int Board[9][9];
int est_vrais(Board gride, int x, int y, int valeur);

void initialize_empty_board(Board grid);
void print_board(Board grid);
int verif(Board board);
#endif
