/*!
 * \file display.h
 * \brief File that contains function to print the board in the standard output and in 2D
 *
 */

#ifndef TP11_DISPLAY_H
#define TP11_DISPLAY_H

#include <stdio.h>
#include "board.h"

/**
 * Print every bloc in the board in the standard output
 *
 * @param p The given board
 */
void print_board_command_line(Plateau *p);

/**
 * Print the position and the weight of the bloc
 *
 * @param c The given bloc
 */
void print_carre_command_line(Carre *c);

/**
 * Actualize the board
 *
 * @param p The given board
 * @warning Use this function after every modification of the board.
 */
void update_board(Plateau *p);


#endif /* TP11_DISPLAY_H */
