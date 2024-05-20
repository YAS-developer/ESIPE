#include <stdio.h>
#include "dames.h"
#include "bit.h"

/**
 * This function convert an x, y value in a board to a bit position
 *
 * @param x the row of the cell
 * @param y the column of the cell
 */
int convert_xy_to_bit_position(int x, int y) {
    return x * CHAR_BIT + y;
}

int from_coordinates_to_cell_index(int *x, int *y) {
    int inter_x, inter_y;

    /* coordinates not inside the board */
    if (!((0 <= *x && *x <= 640) &&
          (0 <= *y && *y <= 640))) {
        return 0;
    }

    inter_x = 7 - (*x / (640 / 8));
    inter_y = 7 - (*y / (640 / 8));


    /* If inside the p, double check if the row and column are correct for the grid's array */
    if (!((0 <= inter_x && inter_x < 8) &&
          (0 <= inter_y && inter_y < 8))) {
        return 0;
    }

    *x = inter_x;
    *y = inter_y;

    return 1;
}


int is_bord_full(unsigned long int n) {
    int i;
    for (i = BOARD_LENGTH; i >= 0; i--) {
        if (bit_value_ULI(n, i) == 0) {
            return 0;
        }
    }

    return 1;
}


int is_queen_placeable(unsigned long int n, int x, int y) {

    int position = convert_xy_to_bit_position(x, y);
    printf("%d\n", position);
    return !bit_value_ULI(n, position);
}

/**
 * This function will update every bits to 1 corresponding to the parameters
 *
 * @param n The board
 * @param position The actual position of the queen
 * @param start_index The starting index (either row or column)
 * @param delta The next position of the bit
 */
void set_cells(unsigned long int *n, int position, int start_index, int delta) {
    int i, pos_update;

    for (i = start_index, pos_update = position;
         i < 8 && pos_update >= 0 && pos_update < 64; i++, pos_update = pos_update + delta) {
        set_positive_bit_ULI(n, pos_update);
    }

    for (i = start_index, pos_update = position;
         i >= 0 && pos_update >= 0 && pos_update < 64; i--, pos_update = pos_update - delta) {
        set_positive_bit_ULI(n, pos_update);
    }
}

/**
 * Set every bits of the board to 1 on the line of the given position
 *
 * @param n The board
 * @param position The position of the queen
 */
void set_line(unsigned long int *n, int position) {
    set_cells(n, position, position % 8, 1);
}

/**
 * Set every bits of the board to 1 on the column of the given position
 *
 * @param n The board
 * @param position The position of the queen
 */
void set_column(unsigned long int *n, int position) {
    set_cells(n, position, position / 8, 8);
}

/**
 * Set every bits of the board to 1 on the diagonals of the given position
 *
 * @param n The board
 * @param position The position of the queen
 */
void set_diagonals(unsigned long int *n, int position) {
    /*First diagonal*/
    set_cells(n, position, position % 8, 9);

    /*Second diagonal*/
    set_cells(n, position, position % 8, -7);
}

void set_cells_queen(unsigned long int *n, int queens[], int *queen_size, int x, int y) {
    int position = convert_xy_to_bit_position(x, y);
    queens[*queen_size] = position;
    *queen_size += 1;

    set_line(n, position);
    set_column(n, position);
    set_diagonals(n, position);
}
