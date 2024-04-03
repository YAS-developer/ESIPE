/*!
 * \file board.h
 * \brief File that contains function to print the board in the standard output and in 2D
 *
 */

#ifndef TP11_BOARD_H
#define TP11_BOARD_H
#define MOVE_NBR 120

#include <MLV/MLV_all.h>

/*! \struct Carre structure
 *  \brief A sliced part of an image
 *
 *  \param lig Actual row coordinates of the image
 *  \param col Actual column coordinates of the image
 *  \param weight weight of the bloc, describing it's original position
 *  \param image_bloc Sliced image
 *
 */
typedef struct carre {
    int lig;
    int col;
    int weight;

    MLV_Image *image_bloc;
} Carre;


/*! \struct Plateau structure
 *  \brief The board
 *
 *  \param width width of the board
 *  \param height height of the board
 *  \param bloc Matrix of bloc corresponding to every sliced images
 *  \param black_bloc Pointer to the only black block of the board
 *  \param solution Image being the solution of the board
 *
 */
typedef struct plateau {
    int width;
    int height;

    Carre **bloc;
    Carre *black_bloc;

    MLV_Image *solution;
} Plateau;

/**
 *
 * Allocate a new plateau
 *
 * @param image The image of the game
 * @param height Height in pixel
 * @param width Width in pixel
 * @return A new plateau
 */
Plateau *allocate_plateau(MLV_Image *image, int height, int width);


/**
 * Initialise a board
 *
 * @param p board
 */
void init_plateau(Plateau *p);

/**
 * Free the board
 *
 * @param p The given board
 */
void free_plateau(Plateau *p);

/**
 * Check if the board is completed
 *
 * @param p the board
 * @return 1 if the played won, 0 otherwise
 */
int check_win(Plateau *p);

/**
 * Swap coordinates between the black bloc of a board and the given bloc
 *
 * @param black_bloc The black bloc
 * @param c2 The other bloc to be swapped with
 * @return The new position of the black bloc
 */
Carre *swap_blocs(Carre *black_bloc, Carre *c2);

/**
 * Randomize the board
 *
 * @param p Board to be randomize
 */
void randomize_plateau(Plateau *p);

/**
 * Convert x, y coordinates of a mouse clic to a board coodinates
 * The conversion is considered false if the converted coordinates does not match the position of our board or the
 * conversion does not fit our array
 *
 * @param p The board
 * @param x x position of the mouse
 * @param y y position of the mouse
 * @return 1 of the conversion was successful, 0 otherwise
 */
int from_coordinates_to_cell_index(Plateau *p, int *x, int *y);

/**
 * Check if the block is adjacent to the black bloc and thus can be swapped
 *
 * @param c The given bloc
 * @param black_bloc The black bloc
 * @return 1 if the blocs can be swapped, 0 otherwise
 */
int can_swap_blocs(Carre c, Carre *black_bloc);

#endif /*TP11_BOARD_H*/
