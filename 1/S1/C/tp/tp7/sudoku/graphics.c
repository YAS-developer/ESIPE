#include <MLV/MLV_all.h>
#include "sudoku.h"
#include "graphics.h"

const int SIZE = 9;

void init_graphics(){
    MLV_create_window("Frame", NULL, 1200, 800);
    for(int i=1; i<=SIZE+1; i++){
        MLV_draw_line(60, 60*i, 600, 60*i, MLV_COLOR_ANTIQUE_WHITE);
        MLV_draw_line(60*i, 60, 60*i, 600, MLV_COLOR_ANTIQUE_WHITE);
    }
}


void update_graphics_board(Board grid){
    int number=0;
    char str[2];
    int addX=0, addY=0;
    for(int i=0; i<SIZE; i++){
        for(int j=0; j<SIZE; j++){
            number = grid[i][j];
            sprintf(str, "%d", number);

            // printf("%d\n", str);
            // if(number != 0){
                
            // }
            MLV_draw_text(85+addX, 85+addY, str, MLV_COLOR_ANTIQUE_WHITE);
            addY+=60; 
        }
        addX+=60;
        addY=0;
    }
}


void graphics_main(Board grid){
    while(1){
        int x=0,y=0;
        MLV_wait_mouse(&x, &y);
        
        int currentX = 60, currentY = 60;
        for (int i = 0; i < SIZE; i++) {
            currentX = 60; 
            for (int j = 0; j < SIZE; j++) {
                if (x >= currentX && x <= (currentX + 60) && y >= currentY && y <= (currentY + 60)) {
                    if(grid[i][j] == 0){
                      MLV_draw_filled_rectangle(currentX+25, currentY+25, 10, 20, MLV_COLOR_BLACK);
                      printf("%d\n", grid[i][j]);
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
}