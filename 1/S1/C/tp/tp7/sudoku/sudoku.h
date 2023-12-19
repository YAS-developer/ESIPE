#ifndef __SUDOKU__
#define __SUDOKU__
#include <stdbool.h>

typedef int Board[9][9];

void initialize_empty_board(Board grid);
void print_board(Board grid);
bool solve_sudoku(Board grid);
bool is_valid_move(Board grid, int row, int col, int num);
bool find_empty_cell(Board grid, int *row, int *col);


#endif


