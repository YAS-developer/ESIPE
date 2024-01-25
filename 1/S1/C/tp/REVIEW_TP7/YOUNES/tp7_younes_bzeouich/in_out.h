#ifndef __IN_OUT__
#define __IN_OUT__

#include "sudoku.h"

int fread_board(const char* file, Board board);
int lire_list(const char* file, int* list,char* arr[]);
int ecrire_list(const char* file, int* list, char* arr[]) ;

#endif
