#include <MLV/MLV_all.h>

#define LINE_COLOR MLV_COLOR_BLUE
#define BACKGROUND_COLOR MLV_COLOR_WHITE

void draw_H(int x, int y, int width) {
    if (width <= 8) return;  

    int half_width = width / 2;
    int quarter_width = width / 4;


    int left_top_x = x - quarter_width;
    int left_top_y = y - half_width;
    int left_bottom_x = x - quarter_width;
    int left_bottom_y = y + half_width;
    int right_top_x = x + quarter_width;
    int right_top_y = y - half_width;
    int right_bottom_x = x + quarter_width;
    int right_bottom_y = y + half_width;

    MLV_draw_line(left_top_x, left_top_y, left_bottom_x, left_bottom_y, LINE_COLOR);
    MLV_draw_line(right_top_x, right_top_y, right_bottom_x, right_bottom_y, LINE_COLOR);
    MLV_draw_line(left_top_x, y, right_top_x, y, LINE_COLOR);

    MLV_actualise_window();
    
    
    MLV_wait_milliseconds(500);

    draw_H(left_top_x, left_top_y, half_width);
    draw_H(right_top_x, right_top_y, half_width);
    draw_H(left_bottom_x, left_bottom_y, half_width);
    draw_H(right_bottom_x, right_bottom_y, half_width);
}

int main() {
    MLV_create_window("Recursive H", "Recursive H", 500, 500);
    MLV_draw_filled_rectangle(0, 0, 500, 500, BACKGROUND_COLOR);

    draw_H(250, 250, 200); // Centre de la fenêtre avec une largeur initiale

    MLV_update_window();
    MLV_wait_seconds(10); 
    MLV_free_window();

    return 0;
}
