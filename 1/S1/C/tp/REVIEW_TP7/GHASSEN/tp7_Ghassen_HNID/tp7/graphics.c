#include "graphics.h"
#include "sudoku.h"  // Assurez-vous d'inclure sudoku.h
#include <MLV/MLV_all.h>

#define WINDOW_WIDTH 1200
#define WINDOW_HEIGHT 800
#define CELL_SIZE (WINDOW_WIDTH / SIZE)

// Définir la taille de la grille et sa position à gauche
#define grid_width 500
#define grid_height 500
#define grid_x 50
#define grid_y (WINDOW_HEIGHT - grid_height) / 2

extern int selected_number;
int selected_row = -1;
int selected_col = -1;


void draw_board(Board *grid) {
    // Dessiner la grille
    for (int i = 0; i <= SIZE; i++) {
        int x = grid_x + i * (grid_width / SIZE);
        int y = grid_y + i * (grid_height / SIZE);
        MLV_draw_line(x, grid_y, x, grid_y + grid_height, MLV_COLOR_WHITE);
        MLV_draw_line(grid_x, y, grid_x + grid_width, y, MLV_COLOR_WHITE);
    }

    // Dessiner les nombres dans les cellules
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            int cell_x = grid_x + j * (grid_width / SIZE);
            int cell_y = grid_y + i * (grid_height / SIZE);

            // Convertir la valeur de la cellule en chaîne de caractères
            char num[2];
            if (grid->values[i][j] != 0) {
                sprintf(num, "%d", grid->values[i][j]);
            } else {
                num[0] = '\0';  // Chaîne vide si la valeur est 0
            }

            // Changer la couleur du texte
            // MLV_Color text_color = (grid->values[i][j] != 0) ? MLV_COLOR_GREEN : MLV_COLOR_WHITE;
            MLV_Color text_color = MLV_COLOR_BLUE;

            MLV_draw_text(cell_x + (grid_width / SIZE) / 2, cell_y + (grid_height / SIZE) / 2, num, text_color);
        }
    }

    // Actualiser la fenêtre
    MLV_actualise_window();
}

void handle_mouse_click(Board *grid, int *selected_row, int *selected_col) {
    MLV_draw_text(WINDOW_WIDTH / 2 - 100, 30, "Ajoutez le nombre sélectionné dans la grille du jeu Sudoku.", MLV_COLOR_WHITE);
    MLV_actualise_window();
    int mouse_x, mouse_y;
    wait_for_mouse_click(&mouse_x, &mouse_y);

    MLV_draw_filled_rectangle(0, 0, WINDOW_WIDTH, 60, MLV_COLOR_BLACK);
    MLV_actualise_window();

    // Calculer la ligne et la colonne de la cellule sélectionnée
    *selected_row = (mouse_y - grid_y) / (grid_height / SIZE);
    *selected_col = (mouse_x - grid_x) / (grid_width / SIZE);

    // Assurez-vous que la ligne et la colonne sont dans les limites valides
    if (*selected_row < 0) *selected_row = 0;
    if (*selected_row >= SIZE) *selected_row = SIZE - 1;
    if (*selected_col < 0) *selected_col = 0;
    if (*selected_col >= SIZE) *selected_col = SIZE - 1;
}

// Fonction pour gérer le clic sur la numbers_board
int handle_mouse_click_numbers(Board *numbers_board) {
    MLV_draw_text(WINDOW_WIDTH / 2 - 100, 30, "Sélectionnez un élément de la grille des nombres de 1 à 9.", MLV_COLOR_GREEN);
    MLV_actualise_window();
    
    int mouse_x, mouse_y;
    wait_for_mouse_click(&mouse_x, &mouse_y);

    MLV_draw_filled_rectangle(0, 0, WINDOW_WIDTH, 60, MLV_COLOR_BLACK);
    MLV_actualise_window();

    // Calculer la ligne et la colonne de la cellule sélectionnée sur la numbers_board
    int cell_size = grid_width / 3;
    int clicked_row = (mouse_y - 300) / (cell_size / 3);
    int clicked_col = (mouse_x - 900) / (cell_size / 3);

    // Assurez-vous que la ligne et la colonne sont dans les limites valides
    if (clicked_row < 0) clicked_row = 0;
    if (clicked_row >= 3) clicked_row = 2;
    if (clicked_col < 0) clicked_col = 0;
    if (clicked_col >= 3) clicked_col = 2;

    // Calculer le numéro sélectionné
    int clicked_number = clicked_row * 3 + clicked_col + 1;

    return clicked_number;
}


void draw_numbers(Board *grid) {
    int cell_size = grid_width / 3;

    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            int x = 900 + j * (cell_size / 3);
            int y = 300 + i * (cell_size / 3);

            // Dessiner les lignes horizontales
            MLV_draw_line(900, y, 900 + cell_size, y, MLV_COLOR_WHITE);

            // Dessiner les lignes verticales
            MLV_draw_line(x, 300, x, 300 + cell_size, MLV_COLOR_WHITE);
            if ((i != 3) && (j != 3) ){
                int num = i * 3 + j + 1;
                if (num <= 9) {
                    char num_str[2];
                    sprintf(num_str, "%d", num);
                    MLV_draw_text(x + (cell_size / 6), y + (cell_size / 6), num_str, MLV_COLOR_WHITE);
                }
            }
        }
    }
}

void wait_for_mouse_click(int *x, int *y) {
    MLV_wait_mouse(x, y);
}