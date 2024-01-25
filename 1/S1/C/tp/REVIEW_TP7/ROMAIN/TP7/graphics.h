#ifndef __GRAPHICS__
#define __GRAPHICS__

#include "sudoku.h"

void draw_digit_value(int x, int y, int value, int highlight);
void draw_grid(int cellCount, int x, int y);
void draw_gamezone(Board B, int positionX, int positionY, int hoverX, int hoverY, int menuOpened);
void draw_menu();

#endif
