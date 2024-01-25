#include <MLV/MLV_all.h>

#include "graph.h"
#include "sudoku.h"


void draw_grid(int SIZE)
{
    MLV_draw_filled_rectangle (0, 0, 1200, 800, MLV_COLOR_CHOCOLATE3);
    int i;
    for(i = 1; i <= SIZE+1; i++)
    {
        MLV_draw_line(60,60*i,60*(SIZE+1),60*i, MLV_COLOR_BLACK);
       /* if(i%4 == 0)
        {
            MLV_draw_line(60,60*i+1,60*(SIZE+1),60*i+1, MLV_COLOR_BLACK);
            MLV_draw_line(60*i+1, 60, 60*i, 60*(SIZE+1), MLV_COLOR_BLACK);
        }*/
        
        MLV_draw_line(60*i, 60, 60*i, 60*(SIZE+1), MLV_COLOR_BLACK);
    }
}

void number_in_grid(Board grid, int SIZE)
{
    int i,j;

    char text[2];

    for(i = 0; i < SIZE; i++){
        for(j = 0; j < SIZE; j++){
            	text[0] = grid[j][i] + '0';
	            text[1] = '\0';

            if(strcmp(text, "0")){
                MLV_draw_text((90-3)+60*i, (90-8)+60*j, text, MLV_COLOR_BLACK);
            }    
        }
    }
}

void second_tab(int SIZE, int tab[SIZE][SIZE], Board grid){
    int i,j;

    for(i = 0; i < SIZE; i++){
        for(j = 0; j < SIZE; j++){
            if(grid[i][j] != 0){
                tab[i][j] = 1;
            }else{
                tab[i][j] = 0;
            }
        }

    }
}

void draw_select_number(int doit)
{

    int i, j;
    int a = 1;
    char text[2];

    if(doit == 0){
        for(i = 0; i <= 3 ; i++){
            MLV_draw_line(750,200+(60*i),750+(60*3),200+(60*i), MLV_COLOR_CHOCOLATE3);
            MLV_draw_line(750+ (60*i) , 200, 750+(60*i), 200+(60*3), MLV_COLOR_CHOCOLATE3);
        }
        for(i = 0; i < 3; i++)
        {
            for(j = 0; j < 3; j++){
                sprintf(text, "%d", a);
                if(strcmp(text, "0")){
                    MLV_draw_text(((750+60*j)+60/2)-3, ((200+60*i)+60/2)-6, text, MLV_COLOR_CHOCOLATE3);
                }  
                a++;
            }
        }
        MLV_draw_line(750,200+(60*3),750,200+(60*3)+60, MLV_COLOR_CHOCOLATE3); /* ligne gauche x */

        MLV_draw_line(750,200+(60*3)+60,750+(60*3),200+(60*3)+60, MLV_COLOR_CHOCOLATE3); /* ligne bas Y */

        MLV_draw_line(750+(60*3),200+(60*3),750+(60*3),200+(60*3)+60, MLV_COLOR_CHOCOLATE3); /* ligne droite X */

        MLV_draw_text((750+(750+(60*3)-750)/2)-20, ((200+(60*3)+60)-(60/2))-8, "Annuler", MLV_COLOR_CHOCOLATE3);
        
    }
    else{
        for(i = 0; i <= 3 ; i++){
            MLV_draw_line(750,200+(60*i),750+(60*3),200+(60*i), MLV_COLOR_BLACK);
            MLV_draw_line(750+ (60*i) , 200, 750+(60*i), 200+(60*3), MLV_COLOR_BLACK);
        }
        for(i = 0; i < 3; i++)
        {
            for(j = 0; j < 3; j++){
                sprintf(text, "%d", a);
                if(strcmp(text, "0")){
                    MLV_draw_text(((750+60*j)+60/2)-3, ((200+60*i)+60/2)-6, text, MLV_COLOR_BLACK);
                }  
                a++;
            }
        }
        MLV_draw_line(750,200+(60*3),750,200+(60*3)+60, MLV_COLOR_BLACK); /* ligne gauche x */

        MLV_draw_line(750,200+(60*3)+60,750+(60*3),200+(60*3)+60, MLV_COLOR_BLACK); /* ligne bas Y */

        MLV_draw_line(750+(60*3),200+(60*3),750+(60*3),200+(60*3)+60, MLV_COLOR_BLACK); /* ligne droite X */

        MLV_draw_text((750+(750+(60*3)-750)/2)-20, ((200+(60*3)+60)-(60/2))-8, "Annuler", MLV_COLOR_BLACK);
    }

    MLV_actualise_window();    
}

int find_case(int n)
{
    return n/60;
}

void draw_char(int x, int y, char c, int valid)
{
    char text[2];
	text[0] = c;
	text[1] = '\0';

    MLV_draw_filled_rectangle((60*(x))+1, (60*(y))+1, 59, 59, MLV_COLOR_CHOCOLATE3);
    if(valid == 1)
    {
        MLV_draw_text((90-3)+60*(x-1), (90-8)+60*(y-1), text, MLV_COLOR_CYAN);
    }
    else if(valid == 2){
        MLV_draw_text((90-3)+60*(x-1), (90-8)+60*(y-1), text, MLV_COLOR_DARKRED);
    }
    
    MLV_actualise_window();
}

void finish(void){
    printf("GAGNE ! \n");
    char bravo[8] = { 'B', 'R', 'A', 'V', 'O', ' ' ,'!' , '\0' };
	MLV_draw_filled_rectangle(450, 250, 200, 200, MLV_COLOR_GREEN);
	MLV_draw_text(525, 335, bravo, MLV_COLOR_RED);
	MLV_actualise_window();
	MLV_wait_seconds(5);
}

void game(Board grid, int SIZE, int tab[SIZE][SIZE])
{
    int x, y;
    int case_x, case_y;
    int number_x, number_y;
    int number;
    int res;

    int boolean = 0;
    

    while(!sudoku_finish(grid)){
        MLV_wait_mouse(&x, &y);

        if((x >= 60 && x <= 600) && ( y >= 60 && y <= 600) && boolean == 0){
            case_x = find_case(x);
            case_y = find_case(y);

            if(tab[case_y-1][case_x-1] == 0){    
            draw_char(case_x, case_y, '?', 1);
            draw_select_number(1);
            boolean = 1;
            }

        }
        if((x >= 750 && x <= 930) && (y >= 200 && y <= 380) && boolean == 1){
            number_x = find_case(x-750);
            number_y = find_case(y-200);

            /* number = */  

            if((number_y+1) == 1){
                number = number_x+1;
            }
            else if((number_y+1) == 2)
            {
                number = (number_x+1)+3;
            }
            else if((number_y+1) == 3)
            {
                number = (number_x+1)+6;
            }

            
            grid[case_y-1][case_x-1] = number;
            
            res = is_valid_move(grid, case_y-1, case_x-1, number);
            if(res == 1)
            {
                draw_char(case_x, case_y, '0'+number, 1);
            }
            else{
                draw_char(case_x, case_y, '0'+number, 2);
            }
            
        
            boolean = 0;
            draw_select_number(0);
            print_board(grid);
        }
        if((x >= 750 && x <= 930) && (y >= 380 && y <= 440) && boolean == 1){
            grid[case_y-1][case_x-1] = 0;
            draw_char(case_x, case_y, 'a', 3);
            draw_select_number(0);
            boolean = 0;
        }
    }

    finish();
}


void initialize_game(Board grid,int SIZE, int tab[SIZE][SIZE])
{
    MLV_create_window("Sudoku", NULL, 1200, 800);
    second_tab(SIZE, tab, grid);      
    print_board(grid);
    print_board(tab);
    draw_grid(SIZE);
    number_in_grid(grid, SIZE);   
    MLV_actualise_window();
    game(grid, SIZE, tab);
}





