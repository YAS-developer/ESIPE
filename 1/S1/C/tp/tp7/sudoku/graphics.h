#ifndef __GRAPHICS__
#define __GRAPHICS__
#define SIZE 9
#define ANSWER_SIZE 3


typedef int Board[9][9];

typedef struct {
    int grid_answer[3][3];
    Board original_grid;
    Board grid;
    int row;
    int col;
} SudokuGame;


void initialize_game(SudokuGame *game);

// void set_original_board(Board newGrid);
// void set_board(Board newGrid);

void init_graphics();
void update_graphics_board(SudokuGame *game);
void graphics_main(Board grid);
void check_coord(SudokuGame *game, int x, int y);
void answer_draw(SudokuGame *game);
bool check_coord_answer(SudokuGame *game, int x, int y);
void delete_answer_draw();
#endif