#ifndef __IN_OUT__
#define __IN_OUT__

#include "sudoku.h"

int fread_board(const char* file, Board board);
void check_existing_records(char* filename);
void update_records(char* filename, int newTime, char* timeBuffer);

#endif
