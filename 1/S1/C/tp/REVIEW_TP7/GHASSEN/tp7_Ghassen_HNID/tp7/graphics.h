#ifndef GRAPHICS_H
#define GRAPHICS_H

#include <MLV/MLV_all.h>
#include "sudoku.h"

extern int selected_number;
extern int selected_row;
extern int selected_col;


// Fonction pour dessiner la grille de Sudoku
void draw_board(Board *grid);

// Fonction pour attendre un clic de la souris et récupérer les coordonnées
void wait_for_mouse_click(int *x, int *y);

// Fonction pour gérer le survol de la souris
void handle_mouse_click(Board *grid, int *selected_row, int *selected_col);


int handle_mouse_click_numbers(Board *numbers_board);

void draw_numbers(Board *grid);

#endif