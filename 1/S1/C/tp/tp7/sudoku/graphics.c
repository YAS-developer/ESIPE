#include <MLV/MLV_all.h>
#include <stdbool.h>
#include "sudoku.h"
#include "graphics.h"

const int SIZE = 9;
const int ANSWER_SIZE = 3;
const int grid_answer[3][3]={
    {1,2,3},
    {4,5,6},
    {7,8,9}
};
Board original_grid;
Board grid;
int row=0, col=0;


void set_original_board(Board newGrid){
    for(int i=0; i<9; i++){
        for(int j=0; j<9; j++){
            original_grid[i][j] = newGrid[i][j];
        }
    }
}

void set_board(Board newGrid){
    for(int i=0; i<9; i++){
        for(int j=0; j<9; j++){
            grid[i][j] = newGrid[i][j];
        }
    }
}

void init_graphics(){
    MLV_create_window("Frame", NULL, 1200, 800);
    MLV_draw_filled_rectangle(0, 0, 1200, 880, MLV_COLOR_CADET_BLUE);
    for(int i=1; i<=SIZE+1; i++){
        MLV_draw_line(60, 60*i, 600, 60*i, MLV_COLOR_ANTIQUE_WHITE);
        MLV_draw_line(60*i, 60, 60*i, 600, MLV_COLOR_ANTIQUE_WHITE);
    }
    MLV_actualise_window();
}


void update_graphics_board(){
    int number=0;
    char str[2];
    int addX=0, addY=0;
    for(int i=0; i<SIZE; i++){
        for(int j=0; j<SIZE; j++){
            number = grid[i][j];
            sprintf(str, "%d", number);
            if(number == 0){
                MLV_draw_filled_rectangle(85+addX, 85+addY, 20, 20, MLV_COLOR_CADET_BLUE);
            }
            else{
                MLV_draw_filled_rectangle(85+addX, 85+addY, 20, 20, MLV_COLOR_CADET_BLUE);
                MLV_draw_text(85+addX, 85+addY, str, MLV_COLOR_ANTIQUE_WHITE);
            }
            addX+=60; 
        }
        addX=0;
        addY+=60;
    }
    MLV_actualise_window();
}


void graphics_main(){
    int x=0,y=0;
    while(1){
        MLV_wait_mouse(&x, &y);
        if(x > 0 || y > 0){
            check_coord(x, y);
        }
    } 
}


void check_coord(int x, int y){
    int currentX = 60, currentY = 60;
    for (int i = 0; i < SIZE; i++) {
        currentX = 60; 
        for (int j = 0; j < SIZE; j++) {
            if (x >= currentX && x <= (currentX + 60) && y >= currentY && y <= (currentY + 60)) {
                if(original_grid[i][j] == 0){
                    row = i; col = j;
                    MLV_draw_filled_rectangle(currentX+25, currentY+25, 10, 20, MLV_COLOR_CADET_BLUE);
                    MLV_draw_text(currentX+25, currentY+25, "?", MLV_COLOR_GREEN);
                    answer_draw();
                    
                }
                // MLV_draw_filled_rectangle(currentX+25, currentY+25, 10, 20, MLV_COLOR_CADET_BLUE);
                // MLV_draw_filled_rectangle(currentX+25, currentY+25, 10, 20, MLV_COLOR_CADET_BLUE);
            }
            currentX += 60; 
        }
        currentY += 60; 
    }
    MLV_actualise_window();
}

void answer_draw(){
    int add=0, addX=0, addY=0;
    // int number=1;
    char str[2];
    for(int i=0; i<=ANSWER_SIZE; i++){
        MLV_draw_line(750, 240+add, 930, 240+add, MLV_COLOR_ANTIQUE_WHITE);
        MLV_draw_line(750+add, 240, 750+add, 420, MLV_COLOR_ANTIQUE_WHITE);
        add+=60;
        if(i != ANSWER_SIZE){
            for(int j=0; j<ANSWER_SIZE; j++){
                sprintf(str, "%d", grid_answer[i][j]);
                MLV_draw_text(775+addX, 260+addY, str, MLV_COLOR_ANTIQUE_WHITE);
                addX+=60;
            }
        }
        addX=0;
        addY+=60;
    }
    MLV_draw_filled_rectangle(750, 440, 180, 60, MLV_COLOR_RED);
    char* txt="Annuler";
    MLV_draw_text(805, 460, txt, MLV_COLOR_ANTIQUE_WHITE);


    MLV_actualise_window();


    while(1){
        int x=0,y=0;
  
        MLV_wait_mouse(&x, &y);
        if(x > 0 || y > 0){
            if(check_coord_answer(x, y)){
                break;
            }
        }
    }

    // MLV_actualise_window();
}


bool check_coord_answer(int x, int y){

    

    if(x>= 750 && x <= (750+180) && y >= 440 && y <= 500){
        update_graphics_board();
        delete_answer_draw();
        return true;
    }

    int currentX = 750, currentY = 240;
    for (int i = 0; i < ANSWER_SIZE; i++) {
        currentX = 750; 
        for (int j = 0; j < ANSWER_SIZE; j++) {
            if (x >= currentX && x <= (currentX + 60) && y >= currentY && y <= (currentY + 60)) {
                int number = grid_answer[i][j];
                if(is_valid_move(grid, row, col, number)){
                    grid[row][col]=number;
                    update_graphics_board();
                    delete_answer_draw();
                    return true;
                }
                else{
                    printf("On ne peut pas mettre ce numero: %d\n", number);
                }
            }
            currentX += 60; 
        }
        currentY += 60; 
    }

    return false;
}



void delete_answer_draw(){
    int add=0;
    // int currentX=;
    for(int i=0; i<=ANSWER_SIZE; i++){
        MLV_draw_line(750, 240+add, 930, 240+add, MLV_COLOR_CADET_BLUE);
        MLV_draw_line(750+add, 240, 750+add, 420, MLV_COLOR_CADET_BLUE);
        add+=60;
    }
    MLV_draw_filled_rectangle(750, 240, 930, 420, MLV_COLOR_CADET_BLUE);
}

