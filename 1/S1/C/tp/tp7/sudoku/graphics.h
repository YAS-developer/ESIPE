#ifndef __GRAPHICS__
#define __GRAPHICS__

typedef int Board[9][9];


void set_original_board(Board newGrid);
void set_board(Board newGrid);

void init_graphics();
void update_graphics_board();
void graphics_main();
void check_coord(int x, int y);
void answer_draw();
bool check_coord_answer(int x, int y);
void delete_answer_draw();
#endif