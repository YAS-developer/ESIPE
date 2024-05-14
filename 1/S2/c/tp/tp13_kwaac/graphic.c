#include "graphic.h"
#include "bit.h"
#include "dames.h"
#include <MLV/MLV_all.h>

/**
 * Check if an element is inside an array
 *
 * @param tab The array
 * @param elt The element to fecth
 * @param size The size of the array
 * @return 1 if the array contains the element, 0 otherwise
 */
int in_array(int tab[], int elt, int size) {
    int i;
    for (i = 0; i < size; i++) {
        if (tab[i] == elt) {
            return 1;
        }
    }

    return 0;
}

void draw_board(unsigned long int n, int queens[], int queen_size, int cell_size) {
    int i, j, position;
    MLV_Color color;
    MLV_Image *image = MLV_load_image("queen.png");
    MLV_resize_image(image, cell_size, cell_size);

    for (i = 0; i < 8; i++) {
        for (j = 0; j < 8; j++) {
            position = (7 - i) * 8 + (7 - j);
            if (bit_value_ULI(n, position)) {
                if ((i + j) % 2 == 0) {
                    color = MLV_COLOR_RED4;
                } else {
                    color = MLV_COLOR_RED1;
                }
            } else {
                if ((i + j) % 2 == 0) {
                    color = MLV_COLOR_BLACK;
                } else {
                    color = MLV_COLOR_GRAY;
                }
            }

            MLV_draw_filled_rectangle(j * cell_size, i * cell_size, j * cell_size + cell_size,
                                      i * cell_size + cell_size, color);

            if (in_array(queens, position, queen_size)) {
                MLV_draw_image(image, j * cell_size, i * cell_size);
            }


        }


    }
    MLV_actualise_window();
}

void print_bord_command_line(unsigned long int n) {
    int i;
    for (i = BOARD_LENGTH; i >= 0; i--) {
        printf("%d ", bit_value_ULI(n, i));
        if (i % (CHAR_BIT) == 0) {
            printf("\n");
        }
    }
    printf("\n");
}



