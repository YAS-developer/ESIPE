#include "board.h"
#include <stdlib.h>
#include <assert.h>
#include "display.h"



void free_bloc_array(Carre **c, int size) {
    int i;
    for (i = 0; i < size; i++) {
        free(c[i]);
    }
    free(c);
}

/**
 * Allocate, initialise and return a new board
 *
 * @return A new Board
 */
Plateau *allocate_plateau(MLV_Image *image, int height, int width) {
    int i, j;
    int img_width = MLV_get_image_width(image) / width;
    int img_height = MLV_get_image_height(image) / height;
    MLV_Image *tmp;

    Plateau *p = (Plateau *) calloc(1, sizeof(Plateau));
    assert(p != NULL);

    p->width = width;
    p->height = height;

    p->bloc = (Carre **) calloc(height, sizeof(Carre *));

    for (i = 0; i < height; i++) {
        p->bloc[i] = (Carre *) calloc(width, sizeof(Carre));
    }

    p->solution = image;

    for (i = 0; i < height; i++) {
        for (j = 0; j < width; j++) {
            tmp = MLV_copy_partial_image(image, j * img_height, i * img_width, img_width, img_height);

            p->bloc[i][j].lig = i;
            p->bloc[i][j].col = j;
            p->bloc[i][j].weight = i * width + j;
            p->bloc[i][j].image_bloc = tmp;
        }
    }

    p->black_bloc = &p->bloc[i - 1][j - 1];

    return p;
}

/**
 * Free the board
 *
 * @param p the board
 */
void free_plateau(Plateau *p) {
    free_bloc_array(p->bloc, p->height);
    MLV_free_image(p->solution);
    free(p);
}


/**
 * Compare two blocs
 *
 * @param c1 first bloc
 * @param c2 second bloc
 * @return 1 if they are equals, 0 otherwise
 */
int compare_bloc_position(Carre c1, Carre c2) {
    return (c1.col == c2.col) && (c1.lig == c2.lig);
}

/**
 * Check if the board is completed
 *
 * @param p the board
 * @return 1 if the played won, 0 otherwise
 */
int check_win(Plateau *p) {
    int i, j;

    for (i = 0; i < p->height; i++) {
        for (j = 0; j < p->width; j++) {
            if (p->bloc[i][j].weight != i * p->width + j) {
                return 0;
            }
        }
    }

    return 1;
}

/**
 * Check if the selected bloc is adjacent to the black bloc
 * being adjacent means being on the same line or column and next to each other
 *
 * @param c selected bloc
 * @param black_bloc black_bloc
 * @return 1 if the they are adjacent can occur, 0 otherwise
 */
int can_swap_blocs(Carre c, Carre *black_bloc) {
    int line = c.lig - black_bloc->lig;
    int col = c.col - black_bloc->col;

    /*close to a 3x3 grid*/
    if ((line <= 1 && line >= -1) && (col <= 1 && col >= -1)) {
        /* exclude the diagonals */
        return (line != 0 && col == 0) || (col != 0 && line == 0);
    }

    return 0;
}

Carre *swap_blocs(Carre *black_bloc, Carre *c2) {
    int weight;
    MLV_Image *tmp;

    weight = black_bloc->weight;
    tmp = black_bloc->image_bloc;

    black_bloc->weight = c2->weight;
    black_bloc->image_bloc = c2->image_bloc;

    c2->weight = weight;
    c2->image_bloc = tmp;

    return c2;
}

int is_out_of_bound(Plateau *p, int lig, int col) {
    return lig < p->height && lig >= 0 && col < p->width && col >= 0;
}

void associate_move_to_bloc(Carre *black_bloc, int move, int *move_lig, int *move_col) {
    switch (move) {

        case 0: /* UP */
            *move_lig = -1 + black_bloc->lig;
            *move_col = 0 + black_bloc->col;
            break;


        case 1: /* DOWN */
            *move_lig = 1 + black_bloc->lig;
            *move_col = 0 + black_bloc->col;
            break;

        case 2: /* LEFT */
            *move_lig = 0 + black_bloc->lig;
            *move_col = -1 + black_bloc->col;
            break;

        case 3: /* RIGHT */
            *move_lig = 0 + black_bloc->lig;
            *move_col = 1 + black_bloc->col;
            break;

        default:;
    }
}

void randomize_plateau(Plateau *p) {
    int i, move, move_lig, move_col;
    update_board(p);
    MLV_wait_milliseconds(1000);
    for (i = 0; i < MOVE_NBR; i++) {
        do {
            move = rand() % 4;
            associate_move_to_bloc(p->black_bloc, move, &move_lig, &move_col);

        } while (!is_out_of_bound(p, move_lig, move_col));

        MLV_wait_milliseconds(35);
        p->black_bloc = swap_blocs(p->black_bloc, &p->bloc[move_lig][move_col]);
        update_board(p);

    }

}

/**
 * The function gives the row and the column of the cell of the grid the player clicked on.
 * The function does not change x and y if the player is not on a cell.
 *
 * @warning This function must be used after the player clicked on the p
 * @param p Grid to check in
 * @param x x coordinate (not row)
 * @param y y coordinate (not column)
 * @return 1 if the player clicked in a cell, 0 otherwise
 */
int from_coordinates_to_cell_index(Plateau *p, int *x, int *y) {
    int inter_x, inter_y;

    /* coordinates not inside the board */
    if (!((0 <= *x && *x <= 512) &&
          (0 <= *y && *y <= 512))) {
        return 0;
    }

    inter_x = *x / (512 / 4);
    inter_y = *y / (512 / 4);

    /* If inside the p, double check if the row and column are correct for the grid's array */
    if (!((0 <= inter_x && inter_x < p->height) &&
          (0 <= inter_y && inter_y < p->width))) {
        return 0;
    }

    *x = inter_x;
    *y = inter_y;

    return 1;
}
