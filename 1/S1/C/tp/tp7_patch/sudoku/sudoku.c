#include <stdio.h>
#include <stdbool.h>
#include "sudoku.h"

bool is_valid_move(Board grid, int row, int col, int num) {
    int i;
    for (i = 0; i < 9; i++) {
        if (grid[row][i] == num) {
            return false;
        }
    }

 
    for (i = 0; i < 9; i++) {
        if (grid[i][col] == num) {
            return false;
        }
    }

    int j;
    int startRow = row - row % 3;
    int startCol = col - col % 3;
    for (i = 0; i < 3; i++) {
        for (j = 0; j < 3; j++) {
            if (grid[startRow + i][startCol + j] == num) {
                return false;
            }
        }
    }

    return true;
}

bool find_empty_cell(Board grid, int *row, int *col) {
    for (*row = 0; *row < 9; (*row)++) {
        for (*col = 0; *col < 9; (*col)++) {
            if (grid[*row][*col] == 0) {
                return true;
            }
        }
    }
    return false;
}

bool solve_sudoku(Board grid) {
    int row, col, num;

    if (!find_empty_cell(grid, &row, &col)) {
        /*If there are no empty cells, the Sudoku is solved*/ 
        return true;
    }

    for (num = 1; num <= 9; num++) {
        if (is_valid_move(grid, row, col, num)) {
            /*Try placing the number in the empty cell*/ 
            grid[row][col] = num;

            /*Recursively solve the Sudoku*/ 
            if (solve_sudoku(grid)) {
                return true;
            }

            /*If the number doesn't lead to a solution, backtrack and try the next number*/ 
            grid[row][col] = 0;
        }
    }

    return false;
}

void initialize_empty_board(Board grid) {
    int i, j;
    for (i = 0; i < 9; i++) {
        for (j = 0; j < 9; j++) {
            grid[i][j] = 0;
        }
    }
}

void print_board(Board grid) {
    /*Print the Sudoku grid*/ 
    int i, j;
    for (i = 0; i < 9; i++) {
        for (j = 0; j < 9; j++) {
            printf("%d ", grid[i][j]);
        }
        printf("\n");
    }
}
