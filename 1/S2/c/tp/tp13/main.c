#include <stdio.h>
#include "dames.h"
#include "graphic.h"

#define SIZE 640

int main() {
    unsigned long int n = 0;
    int queens[8];
    int queen_size = 0, mouse_x, mouse_y;

    MLV_create_window("Damier", "aled", SIZE, SIZE);

    draw_board(n, queens, queen_size, SIZE / 8);

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
        printf("%s", "You lose :(\n");
    } else {
        printf("%s", "YOU WIN !!!\n");
    }

    MLV_free_window();

    return 0;
}
