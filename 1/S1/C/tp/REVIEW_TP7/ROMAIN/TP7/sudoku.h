#ifndef __SUDOKU__
#define __SUDOKU__

typedef int Board[9][9];
int get_digit(Board B, int x, int y);
int board_filled(Board B);
void analyze_click(int clickX, int clickY, int* positionX, int* positionY, int* menuOpened, Board B);

#endif
