#include <stdio.h>
#include "dames.h"
#include "graphic.h"

int main() {
    unsigned long int n = 0;
    int queens[8];
    int queen_size = 0, mouse_x, mouse_y;

    MLV_create_window("bob", "aled", 640, 640);

    draw_board(n, queens, queen_size, 640 / 8);

    while (!is_bord_full(n) && queen_size <= 7) {
        MLV_wait_mouse(&mouse_x, &mouse_y);

        if (from_coordinates_to_cell_index(&mouse_y, &mouse_x)) {
            if (is_queen_placeable(n, mouse_y, mouse_x)) {
                set_cells_queen(&n, queens, &queen_size, mouse_y, mouse_x);
            }
        }
        draw_board(n, queens, queen_size, 640 / 8);
        print_bord_command_line(n);
    }

    if (queen_size <= 7) {
        printf("%s", "Pas de bol, c'est perdu\n");
    } else {
        printf("%s", "Ho bravo c'est gagné !\n");
    }

    MLV_free_window();

    return 0;
}
