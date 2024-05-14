/*! \file dames.h
 *  \brief File containing functions that are used
 *
 */

#ifndef TP13_DAMES_H
#define TP13_DAMES_H

#include <limits.h>

#define BOARD_LENGTH CHAR_BIT * 8 - 1

/**
 * Print the board in the standard output
 *
 * @param n The board
 */
void print_bord_command_line(unsigned long int n);

/**
 * Convert the given x, y position to place the queen on the board and in the array of queens
 *
 * @param n The board
 * @param queens The array of places queens
 * @param queen_size The size of the array
 * @param x row of the queen
 * @param y column of the queen
 */
void set_cells_queen(unsigned long int *n, int queens[], int *queen_size, int x, int y);

/**
 * Function that check if every cells are menaced by a queen
 *
 * @param n the board
 * @return 1 if every cell are menaced, 0 otherwise
 */
int is_bord_full(unsigned long int n);

/**
 * Inform if the queen can be placed at the given coordinates
 *
 * @param n The board
 * @param x The row of the coordinates
 * @param y The column of the coordinates
 * @return 1 if the queen can be placed, 0 otherwise
 */
int is_queen_placeable(unsigned long int n, int x, int y);

/**
 * The function gives the row and the column of the cell of the grid the player clicked on.
 * The function does not change x and y if the player is not on a cell.
 *
 * @warning This function must be used after the player clicked on the board
 * @param x x coordinate (not row)
 * @param y y coordinate (not column)
 * @return 1 if the player clicked in a cell, 0 otherwise
 */
int from_coordinates_to_cell_index(int *x, int *y);


#endif /*TP13_DAMES_H*/
