#ifndef __SUDOKU__
#define __SUDOKU__

typedef int Board[9][9];

void initialize_empty_board(Board grid);
void print_board(Board grid);
int solve_sudoku(Board grid);
int is_valid_move(Board grid, int row, int col, int num);
int find_empty_cell(Board grid, int *row, int *col);
int sudoku_finish(Board grid);

#endif
