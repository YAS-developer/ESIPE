#include <MLV/MLV_all.h>
#include "sudoku.h"
#include "graphics.h"

const int SIZE = 9;
const int ANSWER_SIZE = 3;
const int[3][3] choiceAnswer={
    [1,2,3],
    [4,5,6],
    [7,8,9]
};

void init_graphics(){
    MLV_create_window("Frame", NULL, 1200, 800);
    for(int i=1; i<=SIZE+1; i++){
        MLV_draw_line(60, 60*i, 600, 60*i, MLV_COLOR_ANTIQUE_WHITE);
        MLV_draw_line(60*i, 60, 60*i, 600, MLV_COLOR_ANTIQUE_WHITE);
    }
    MLV_actualise_window();
}


void update_graphics_board(Board grid){
    int number=0;
    char str[2];
    int addX=0, addY=0;
    for(int i=0; i<SIZE; i++){
        for(int j=0; j<SIZE; j++){
            number = grid[i][j];
            sprintf(str, "%d", number);
            if(number != 0){
                MLV_draw_text(85+addX, 85+addY, str, MLV_COLOR_ANTIQUE_WHITE);
            }
            addX+=60; 
        }
        addX=0;
        addY+=60;
    }
    MLV_actualise_window();
}


void graphics_main(Board grid){
    int x=0,y=0;
    while(1){
        MLV_wait_mouse(&x, &y);
        if(x > 0 || y > 0){
            check_coord(x, y, grid);
        }
    } 
}


void check_coord(int x, int y, Board grid){
    int currentX = 60, currentY = 60;
    for (int i = 0; i < SIZE; i++) {
        currentX = 60; 
        for (int j = 0; j < SIZE; j++) {
            if (x >= currentX && x <= (currentX + 60) && y >= currentY && y <= (currentY + 60)) {
                if(grid[i][j] == 0){
                    MLV_draw_filled_rectangle(currentX+25, currentY+25, 10, 20, MLV_COLOR_BLACK);
                    answerDraw();
                }
                // MLV_draw_filled_rectangle(currentX+25, currentY+25, 10, 20, MLV_COLOR_BLACK);
                // MLV_draw_filled_rectangle(currentX+25, currentY+25, 10, 20, MLV_COLOR_BLACK);
            }
            currentX += 60; 
        }
        currentY += 60; 
    }
    MLV_actualise_window();
}

void answerDraw(){
    int add=0, addX=0, addY=0;
    int number=1;
    char str[2];
    for(int i=0; i<=ANSWER_SIZE; i++){
        MLV_draw_line(750, 240+add, 930, 240+add, MLV_COLOR_ANTIQUE_WHITE);
        MLV_draw_line(750+add, 240, 750+add, 420, MLV_COLOR_ANTIQUE_WHITE);
        add+=60;
        if(i != ANSWER_SIZE){
            for(int j=0; j<ANSWER_SIZE; j++){
                sprintf(str, "%d", number);
                MLV_draw_text(775+addX, 260+addY, str, MLV_COLOR_ANTIQUE_WHITE);
                addX+=60;
                number++;
            }
        }
        addX=0;
        addY+=60;
    }
    MLV_actualise_window();
}


void deleteAnswerDraw(){
    int add=0;
    for(int i=1; i<=ANSWER_SIZE+1; i++){
        MLV_draw_line(750, 240+add, 930, 240+add, MLV_COLOR_BLACK);
        MLV_draw_line(750+add, 240, 750+add, 420, MLV_COLOR_BLACK);
        add+=60;
    }
}

