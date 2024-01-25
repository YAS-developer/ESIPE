#include <MLV/MLV_all.h>
#include "graphics.h"

void draw_digit_value(int x, int y, int value, int highlight) {
	char convertedValue[2];
	sprintf(convertedValue, "%d", value);
	MLV_draw_text(x, y, convertedValue, highlight ? MLV_COLOR_RED : MLV_COLOR_WHITE);
}

void draw_grid(int cellCount, int x, int y) {
	cellCount++;
	for (int i=0; i<cellCount; i++) {
		MLV_draw_line(x+50*i, y, x+50*i, y+cellCount*50-50, MLV_COLOR_WHITE);
		MLV_draw_line(x, y+50*i, x+cellCount*50-50, y+50*i, MLV_COLOR_WHITE);
	}
}

void draw_gamezone(Board B, int positionX, int positionY, int hoverX, int hoverY, int menuOpened) {
	if (hoverX > 50 && hoverX < 500 && hoverY > 50 && hoverY < 500)
		MLV_draw_filled_rectangle(50+50*((hoverX-50)/50), 50+50*((hoverY-50)/50), 50, 50, MLV_COLOR_GRAY30);
	if (menuOpened) {
		MLV_draw_filled_rectangle(50+50*positionX, 50+50*positionY, 50, 50, MLV_COLOR_BLUE);
		if (B[positionX][positionY] == 0) MLV_draw_text(70+50*positionX, 65+50*positionY, "?", MLV_COLOR_WHITE);
	}
	draw_grid(9, 50, 50);
	for (int i=0; i<9; i++)
		for (int j=0; j<9; j++)
			if (B[i][j] != 0)
				draw_digit_value(70+50*i, 65+50*j, get_digit(B,i,j), B[i][j]>10);
}

void draw_menu() {
	draw_grid(3, 600, 50);
	for (int i=0; i<9; i++)
		draw_digit_value(620+50*(i%3), 65+50*(i/3), i+1, 0);
	MLV_draw_line(600, 200, 600, 250, MLV_COLOR_WHITE);
	MLV_draw_line(750, 200, 750, 250, MLV_COLOR_WHITE);
	MLV_draw_line(600, 250, 750, 250, MLV_COLOR_WHITE);
	MLV_draw_text(645, 215, "Effacer", MLV_COLOR_WHITE);
}
