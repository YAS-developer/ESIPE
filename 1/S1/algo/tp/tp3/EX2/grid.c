#include "grid.h"
#include <stdlib.h>
#include <time.h>
/*
 * Allocate memory for a grid and initialize each cell.
 */
grid *create_grid(int x_size, int y_size) {
    int i, j;
    grid *g = (grid *)malloc(sizeof(grid));
    g->x_size = x_size;
    g->y_size = y_size;
    g->cells = (cell **)malloc(x_size*sizeof(cell *));
    for (i = 0; i < x_size; i++)
        g->cells[i] = (cell *)malloc(y_size*sizeof(cell));

    for (j = 0; j < y_size; j++)
        for (i = 0; i < x_size; i++) {
            g->cells[i][j].x_pos = i;
            g->cells[i][j].y_pos = j;
            g->cells[i][j].visible = 0;
            g->cells[i][j].marked = 0;
            g->cells[i][j].mine = 0;
            g->cells[i][j].mine_count = 0;
        }

    return g;
}

/*
 * Free memory for a grid.
 */
void free_grid(grid *g) {
    int i;
    for (i = 0; i < g->x_size; i++)
        free(g->cells[i]);
    free(g->cells);
    free(g);
}

/*
 * Set all cells to visible (for debugging).
 */
void set_all_visible(grid *g) {
    int x, y;
    for (x = 0; x < g->x_size; x++)
        for (y = 0; y < g->y_size; y++)
            g->cells[x][y].visible = 1;
}

/*
 * Add exactly n mines to grid g in random positions.
 */
void add_mines(grid *g, int n) {
    int i, x, y, count = 0;
    int directions[8][2] = {
        {-1, -1}, {0, -1}, {1, -1},
        {-1, 0},           {1, 0},
        {-1, 1},  {0, 1},  {1, 1}
    };

    // Initialize random number generator
    srand(time(NULL));

    while (count < n) {
        x = rand() % g->x_size;
        y = rand() % g->y_size;

        // Only place a mine if there isn't one there already
        if (!g->cells[x][y].mine) {
            g->cells[x][y].mine = 1;
            count++;

            // Increment mine_count of all adjacent cells
            for (i = 0; i < 8; i++) {
                int adj_x = x + directions[i][0];
                int adj_y = y + directions[i][1];

                // Check if adjacent cell is within bounds
                if (adj_x >= 0 && adj_x < g->x_size && adj_y >= 0 && adj_y < g->y_size) {
                    g->cells[adj_x][adj_y].mine_count++;
                }
            }
        }
    }
}

/*
 * Reveal cell c in grid g.
 * Return the total number of revealed cells.
 */
int reveal(grid *g, cell *c) {
    int revealed = 0;
    
    /* If the cell is already visible or marked, do nothing and return 0 */
    if (c->visible || c->marked) {
        return 0;
    }

    /* Make the cell visible */
    c->visible = 1;
    draw_cell_actualise_window(c);  // Assumes draw_cell_actualise_window is implemented correctly

    /* If the cell has a mine or is adjacent to mines, return 1 */
    if (c->mine || c->mine_count > 0) {
        return 1;
    }

    /* Otherwise, recursively reveal all neighboring cells */
    for (int dx = -1; dx <= 1; dx++) {
        for (int dy = -1; dy <= 1; dy++) {
            int new_x = c->x_pos + dx;
            int new_y = c->y_pos + dy;
            
            /* Check boundaries and skip the current cell */
            if (new_x >= 0 && new_x < g->x_size && new_y >= 0 && new_y < g->y_size && !(dx == 0 && dy == 0)) {
                cell *neighbor = &g->cells[new_x][new_y];
                revealed += reveal(g, neighbor);
                MLV_wait_milliseconds(50);  // Small pause for visualization
            }
        }
    }

    return revealed + 1;  // Include the current cell in the count
}
