#ifndef __GRAPHICS__
#define __GRAPHICS__
#define SIZE 9
#define ANSWER_SIZE 3


typedef int Board[9][9];

typedef struct {
    int grid_answer[3][3]={
        {1,2,3},
        {4,5,6},
        {7,8,9}
    };
    Board original_grid;
    Board grid;
    int row=0;
    int col=0;
} SudokuGame;

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