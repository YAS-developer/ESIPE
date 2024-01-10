#ifndef __GRAPHICS__
#define __GRAPHICS__
#define SIZE 9
#define ANSWER_SIZE 3


typedef struct {
    int grid_answer[3][3];
    Board original_grid;
    Board grid;
    Board solved_grid;
    int row;
    int col;
} SudokuGame;


void initialize_game(SudokuGame *game);
void init_graphics();
int update_graphics_board(SudokuGame *game);
void graphics_main(Board grid);
void check_coord(SudokuGame *game, int x, int y);
void answer_draw(SudokuGame *game);
bool check_coord_answer(SudokuGame *game, int x, int y);
void delete_answer_draw();

#endif