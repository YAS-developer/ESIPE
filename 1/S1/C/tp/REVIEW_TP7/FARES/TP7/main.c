#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <MLV/MLV_all.h>

#include "sudoku.h"
#include "in_out.h"
#include "graph.h"


/*#define SIZE 9
#define SIZE2 3
#define case 60*/



int main(int argc, char *argv[]){


    Board grid;
    int tab[9][9];

    if (argc != 2){
        fprintf(stderr, "Usage: %s <file>\n", argv[0]);
        return 1;
    }

    fread_board(argv[1], grid);

    print_board(grid);

    initialize_game(grid, 9, tab);

    return 0;
}