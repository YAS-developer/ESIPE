#include <stdio.h>

#include "sudoku.h"

int is_valid_move(Board grid, int row, int col, int num) {

    int i, j;

    for (i = 0; i < 9; i++) {
        if(i != col){
            if (grid[row][i] == num) {
                return 0;
            }
        }
        
    }

    for (i = 0; i < 9; i++) {
        if(i != row){
            if (grid[i][col] == num) {
                return 0;
            }
        }
    }

    int startRow = row - row % 3;
    int startCol = col - col % 3;
    for (i = 0; i < 3; i++) {
        for (j = 0; j < 3; j++) {
            if(!(((startRow + i) == row) &&  ((startCol+j) == col))){
                if (grid[startRow + i][startCol + j] == num) {
                    return 0;
                }
            }
            
        }
    }

    return 1;
}

int solve_sudoku(Board grid) {
    int row, col, num;

    if (!find_empty_cell(grid, &row, &col)) {
        /* Si pas de case vide sudoku resolu */
        return 1;
    }

    for (num = 1; num <= 9; num++) {
        if (is_valid_move(grid, row, col, num)) {
            grid[row][col] = num;

        /* Resoudre recursivement */
            if (solve_sudoku(grid)) {
                return 1;
            }

            grid[row][col] = 0;
        }
    }

    return 0;
}

void initialize_empty_board(Board grid){
    int i,j;

    for (i = 0; i < 9; i++) {
        for (j = 0; j < 9; j++) {
            grid[i][j] = 0;
        }
    }
}


int find_empty_cell(Board grid, int *row, int *col){
    for (*row = 0; *row < 9; (*row)++)
    {
        for(*col = 0; *col < 9; (*col)++){
            if(grid[*row][*col] == 0){
                return 1;
            }
        }
    }
    return 0;
}


void print_board(Board grid){
    int i, j;

    printf("-------------------------------------\n");
    for (i = 0; i < 9; i++) {
        printf("| ");
        for (j = 0; j < 9; j++) {
            printf("%d | ", grid[i][j]);
        }
        printf("\n-------------------------------------\n");
    }
}

int sudoku_finish(Board grid) {
    int row, col, num;
    
    for (row = 0; row < 9; row++) {
        for (col = 0; col < 9; col++) {
            if (grid[row][col] == 0) {
                return 0; 
            }
        }
    }


    for (row = 0; row < 9; row++) {
        for (col = 0; col < 9; col++) {
            num = grid[row][col];
            
            grid[row][col] = 0;
            if (!is_valid_move(grid, row, col, num)) {
                grid[row][col] = num; 
                return 0; 
            }
            grid[row][col] = num;
        }
    }
    return 1; 
}