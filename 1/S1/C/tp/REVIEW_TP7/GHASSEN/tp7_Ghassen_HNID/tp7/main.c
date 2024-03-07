#include <MLV/MLV_all.h>
#include "sudoku.h"
#include "graphics.h"

#define WINDOW_WIDTH 1200
#define WINDOW_HEIGHT 800

int selected_number = 0;
int selected_row_game = -1;
int selected_col_game = -1;

int main() {
    MLV_create_window("Sudoku Game", "Sudoku", WINDOW_WIDTH, WINDOW_HEIGHT);

    Board game_board;
    Board numbers_board;

    initialize_board(&game_board);

    // Charger la grille à partir du fichier "grid.txt"
    load_board_from_file(&game_board, "data/grid1.txt");

    draw_numbers(&numbers_board);

    while (!is_board_full(&game_board)) {
        draw_numbers(&numbers_board);  // Dessiner les numéros à côté de la grille principale
        draw_board(&game_board);  // Dessiner la grille principale

        // Gérer les clics sur la grille des nombres
        int clicked_number = handle_mouse_click_numbers(&numbers_board);

        // Si un nombre est sélectionné sur la grille des nombres
        if (clicked_number > 0) {
            // Boucle pour gérer les clics sur la grille principale
            handle_mouse_click(&game_board, &selected_row_game, &selected_col_game);

            // Si une case de la grille principale est sélectionnée
            if (selected_row_game >= 0 && selected_col_game >= 0) {
                // Si la case sélectionnée dans la grille principale est vide et que le mouvement est légal, insérer le nombre
                if (is_legal_move(&game_board, selected_row_game, selected_col_game, clicked_number)
                    && game_board.values[selected_row_game][selected_col_game] == 0) {
                    set_number(&game_board, selected_row_game, selected_col_game, clicked_number);
                }
                // Réinitialiser les sélections dans la grille principale
                selected_row_game = -1;
                selected_col_game = -1;
            }
        }

        // Actualiser la fenêtre
        MLV_actualise_window();
    }

    // Vérifier si la grille est complète et correcte
    if (is_board_complete_and_correct(&game_board)) {
        draw_board(&game_board);  // Afficher la grille une dernière fois

        MLV_draw_text(WINDOW_WIDTH / 2 - 100, WINDOW_HEIGHT / 2, "Partie terminée !", MLV_COLOR_BLACK);
        MLV_draw_text(WINDOW_WIDTH / 2 - 150, WINDOW_HEIGHT / 2 + 30, "Cliquez pour quitter.", MLV_COLOR_BLACK);

        MLV_actualise_window();

        while (1) {
            int mouse_x, mouse_y;
            wait_for_mouse_click(&mouse_x, &mouse_y);
            break;  // Quitter le programme lorsqu'un clic est détecté
        }
    } else {
        // Si la grille n'est pas complète et correcte, afficher un message d'erreur
        MLV_draw_text(WINDOW_WIDTH / 2 - 150, WINDOW_HEIGHT / 2, "Erreur : La grille n'est pas correcte !", MLV_COLOR_RED);
        MLV_actualise_window();

        // Attendre un clic pour quitter
        int mouse_x, mouse_y;
        wait_for_mouse_click(&mouse_x, &mouse_y);
    }

    MLV_free_window();   // Libérer la fenêtre

    return 0;
}