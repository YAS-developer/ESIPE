#include "display.h"

void print_board_command_line(Plateau *p) {
    int i, j;

    for (i = 0; i < p->height; i++) {
        for (j = 0; j < p->width; j++) {
            print_carre_command_line(&p->bloc[i][j]);
        }
        printf("\n");
    }
}

void update_board(Plateau *p) {
    int i, j;
    int img_width = MLV_get_image_width(p->solution) / p->width;
    int img_height = MLV_get_image_height(p->solution) / p->height;

    MLV_clear_window(MLV_COLOR_BLACK);

    for (i = 0; i < p->height; i++) {
        for (j = 0; j < p->width; j++) {
            if (p->bloc[i][j].weight != p->black_bloc->weight) {
                MLV_draw_image(p->bloc[i][j].image_bloc, j * img_width, i * img_height);
            }
        }
    }

    MLV_actualise_window();
}


void print_carre_command_line(Carre *c) {
    printf("(%d, %d) : %d,  ", c->lig, c->col, c->weight);
}
