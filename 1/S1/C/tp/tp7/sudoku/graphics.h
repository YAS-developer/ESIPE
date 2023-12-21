#ifndef __GRAPHICS__
#define __GRAPHICS__

typedef int Board[9][9];

void init_graphics();
void update_graphics_board(Board grid);
void graphics_main();
void check_coord(int x, int y, Board grid);
void answerDraw();
void deleteAnswerDraw();
#endif