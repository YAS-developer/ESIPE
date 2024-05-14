/*! \file graphic.h
 *  \brief File containing function to draw the board in the standard output and in graphic 2D
 */

#ifndef TP13_GRAPHIC_H
#define TP13_GRAPHIC_H

#include <MLV/MLV_all.h>

/**
 * Draw the board and actualize the window
 *
 * @param n The board
 * @param queens The array of places queens
 * @param queen_size The number of queens placed
 * @param cell_size The size of a cell
 */
void draw_board(unsigned long int n, int queens[], int queen_size, int cell_size);

/**
 * Print every cell of the board in command line
 *
 * @param n the board
 */
void print_bord_command_line(unsigned long int n);

#endif /*TP13_GRAPHIC_H*/
