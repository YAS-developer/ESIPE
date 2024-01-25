#include <stdio.h>
#include <time.h>
#include <unistd.h>
#include <MLV/MLV_all.h>
#include "sudoku.h"
#include "in_out.h"
#include "graphics.h"

int main(int argc, char* argv[]){
	Board B;
	MLV_create_window("Sudoku", "", 800, 550);
	MLV_change_default_font("ubuntu.ttf", 20);
	char* gridChoice;
	char path[15] = "data/grid0.txt";
	MLV_draw_text_with_font(300, 150, "Sudoku", MLV_load_font("ubuntu.ttf", 60), MLV_COLOR_WHITE);
	do { MLV_wait_input_box(200, 350, 400, 40,
		                MLV_COLOR_WHITE, MLV_COLOR_WHITE, MLV_COLOR_BLACK,
		                "Entrez un numéro de grille entre 1 et 5 : ", &gridChoice);
	} while (atoi(gridChoice)<1 || atoi(gridChoice)>5);
	path[9] += atoi(gridChoice);
	fread_board(path, B);
	int menuOpened = 0;
	int clickX, clickY, positionX, positionY;
	int hoverX, hoverY;
	int timestamp = time(NULL);
	char timeBuffer[6];
	check_existing_records("records.sav");
	while (!board_filled(B)) {
		MLV_draw_filled_rectangle(0, 0, 800, 550, MLV_COLOR_BLACK);
		MLV_get_mouse_position(&hoverX, &hoverY);
		draw_gamezone(B, positionX, positionY, hoverX, hoverY, menuOpened);
		if (menuOpened) draw_menu();
		sprintf(timeBuffer, "%02ld:%02ld", (time(NULL)-timestamp)/60, (time(NULL)-timestamp)%60);
		MLV_draw_text(620, 465, timeBuffer, MLV_COLOR_WHITE);
		MLV_actualise_window();
		if (MLV_get_mouse_button_state(MLV_BUTTON_LEFT) == MLV_PRESSED) {
			MLV_get_mouse_position(&clickX, &clickY);
			analyze_click(clickX, clickY, &positionX, &positionY, &menuOpened, B);
		}
	}
	MLV_draw_filled_rectangle(0, 0, 800, 550, MLV_COLOR_BLACK);
	update_records("records.sav", (time(NULL)-timestamp)/60, timeBuffer);
	draw_gamezone(B, 0, 0, 0, 0, 0);
	MLV_draw_text(580, 465, "Sudoku résolu !", MLV_COLOR_GOLD);
	MLV_actualise_window();
	sleep(3);
	MLV_free_window();
	return 0;
}
