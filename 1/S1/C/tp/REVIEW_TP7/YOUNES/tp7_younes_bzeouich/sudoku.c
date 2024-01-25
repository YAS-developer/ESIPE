#include <stdio.h>

#include "sudoku.h"

void initialize_empty_board(Board grid){

}
/* fonction just pour verifie utilise dans le main*/
int verif(Board board)
{
    int x , y, j ;
    for ( x = 0; x < 9; x++)
    {
        for ( y = 0; y < 9; y++)
        {
            if(board[x][y] <= 0){
                return 0 ;
            }
            for ( j = 0; j < 9; j++)
            {
                if ((board[x][j] == board[x][y]) && (j != y))
                {
                    return 0;
                }
            }
            for ( j = 0; j < 9; j++)
            {
                if ((board[j][y] == board[x][y]) && (j != x))
                {
                    return 0;
                }
            }
        }
    }
    return 1;
}
int est_vrais(Board gride, int x, int y, int valeur)
{
int j ;
    for (j= 0; j < 9; j++)
    {
        if (gride[y][j] == valeur)
        {
            return 0;
        }
    }
    for ( j = 0; j < 9; j++)
    {
        if (gride[j][x] == valeur)
        {
            return 0;
        }
    }
    return 1;
}

void print_board(Board grid){
int i ,j;
    for ( i = 0; i < 9; i++)
    {
        printf("\n");
        for ( j = 0; j < 9; j++)
        {
            printf("| %d |", grid[i][j]);
        }
    }
    printf("\n");
}
