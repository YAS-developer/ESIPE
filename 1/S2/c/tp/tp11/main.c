#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <time.h>
#include "board.h"
#include "display.h"

/**
 * Compare the end of a string with the given end
 *
 * @param str the str the compare
 * @param end the end
 * @return 1 if they are equals, 0 otherwise
 */
int ends_with(char *str, char *end) {
    size_t size_str = strlen(str);
    size_t size_end = strlen(end);
    if (size_end > size_str) {
        return 0;
    }

    return strncmp(str + size_str - size_end, end, size_end) == 0;
}

int main(int argc, char *argv[]) {
    int mouse_x, mouse_y;
    MLV_Image *image;
    MLV_Keyboard_button button;
    MLV_Button_state button_state;
    MLV_Mouse_button mouse_state;
    Plateau *p;
    srand(time(NULL));

    if (argc == 1) {
        fprintf(stderr, "Missing file arguments: %s [image]\n ", argv[0]);
        return EXIT_FAILURE;
    }
    else if (!ends_with(argv[1], ".png") && !ends_with(argv[1], ".gif") && !ends_with(argv[1], ".jpg")) {
        fprintf(stderr, "Wrong file format, \"%s\" is not a png, gif or jpg\n", argv[1]);
        return EXIT_FAILURE;
        
    }
    else if (access(argv[1], F_OK)) {
        fprintf(stderr, "The file does not exist: %s\n", argv[1]);
        return EXIT_FAILURE;
    }

    MLV_create_window("Taquin", "Taquin", 512, 512);

    image = MLV_load_image(argv[1]);
    p = allocate_plateau(image, 4, 4);

    randomize_plateau(p);

    update_board(p);

    while (1) {
        MLV_flush_event_queue();
        button = MLV_KEYBOARD_NONE;
        mouse_state = MLV_BUTTON_RIGHT;
        MLV_wait_event_or_seconds(&button, NULL, NULL, NULL, NULL, &mouse_x, &mouse_y, &mouse_state, &button_state, 1500);
        if (button == MLV_KEYBOARD_h) {
            MLV_clear_window(MLV_COLOR_BLACK);
            MLV_draw_image(p->solution, 0, 0);
            MLV_actualise_window();
            MLV_wait_seconds(1);
            update_board(p);
        }

        if (mouse_state == MLV_BUTTON_LEFT && from_coordinates_to_cell_index(p, &mouse_x, &mouse_y) &&
            can_swap_blocs(p->bloc[mouse_y][mouse_x], p->black_bloc)) {

            p->black_bloc = swap_blocs(p->black_bloc, &p->bloc[mouse_y][mouse_x]);

            update_board(p);
            if (check_win(p)) {
                printf("%s\n", "GG A TOI, C'EST GAGNE !!!");
                break;
            }
        }

    }

    free_plateau(p);
    return 0;
}
