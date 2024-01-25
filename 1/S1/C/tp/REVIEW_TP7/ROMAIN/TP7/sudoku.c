#include "sudoku.h"

int get_digit(Board B, int x, int y) {
	return B[x][y] - (B[x][y]>10 ? 10 : 0);
}

int board_filled(Board B) {
	for (int i=0; i<9; i++)
		for (int j=0; j<9; j++)
			if (B[i][j] == 0)
				return 0;
	return 1;			
}

void analyze_click(int clickX, int clickY, int* positionX, int* positionY, int* menuOpened, Board B) {
	if (clickX > 50 && clickX < 500 && clickY > 50 && clickY < 500 && (B[(clickX-50)/50][(clickY-50)/50] == 0 || B[(clickX-50)/50][(clickY-50)/50] > 10)) {
		*menuOpened = 1;
		*positionX = (clickX-50)/50;
		*positionY = (clickY-50)/50;
	} else if (clickX > 600 && clickX < 750 && clickY > 50 && clickY < 250) {
		if (clickY > 200) B[*positionX][*positionY] = 0;
		else {
			int valueToInsert = (clickX-600)/50+((clickY-50)/50)*3+1;
			for (int i=0; i<9; i++) if (get_digit(B,i,*positionY) == valueToInsert) return;
			for (int i=0; i<9; i++) if (get_digit(B,*positionX,i) == valueToInsert) return;
			for (int i=0; i<9; i++) if (get_digit(B,(*positionX/3)*3+i%3,(*positionY/3)*3+i/3) == valueToInsert) return;
			B[*positionX][*positionY] = valueToInsert+10;
		}
	} else *menuOpened = 0;
}
