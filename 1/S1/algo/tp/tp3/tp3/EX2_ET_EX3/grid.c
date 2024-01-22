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

    // Initialise le générateur de nombres aléatoires
    srand(time(NULL));

    while (count < n) {
        // Génère une position aléatoire pour la mine
        x = rand() % g->x_size;
        y = rand() % g->y_size;

        // Place une mine seulement s'il n'y en a pas déjà une
        if (!g->cells[x][y].mine) {
            g->cells[x][y].mine = 1;
            count++;

            // Incrémente le compteur de mines des cellules adjacentes
            for (i = 0; i < 8; i++) {
                int adj_x = x + directions[i][0];
                int adj_y = y + directions[i][1];

                // Vérifie si la cellule adjacente est dans les limites de la grille
                if (adj_x >= 0 && adj_x < g->x_size && adj_y >= 0 && adj_y < g->y_size) {
                    g->cells[adj_x][adj_y].mine_count++;
                }
            }
        }
    }
}

/*
 * Révèle la cellule c dans la grille g.
 * Retourne le nombre total de cellules révélées.
 */
int reveal(grid *g, cell *c) {
    int revealed = 0;
    
    // Si la cellule est déjà visible ou marquée, ne rien faire et retourner 0
    if (c->visible || c->marked) {
        return 0;
    }

    // Rend la cellule visible
    c->visible = 1;
    draw_cell_actualise_window(c);  // Supposons que draw_cell_actualise_window est correctement implémentée

    // Si la cellule contient une mine ou est adjacente à des mines, retourner 1
    if (c->mine || c->mine_count > 0) {
        return 1;
    }

    // Autrement, révèle récursivement toutes les cellules voisines
    for (int dx = -1; dx <= 1; dx++) {
        for (int dy = -1; dy <= 1; dy++) {
            int new_x = c->x_pos + dx;
            int new_y = c->y_pos + dy;
            
            // Vérifie les limites et ignore la cellule actuelle
            if (new_x >= 0 && new_x < g->x_size && new_y >= 0 && new_y < g->y_size && !(dx == 0 && dy == 0)) {
                cell *neighbor = &g->cells[new_x][new_y];
                revealed += reveal(g, neighbor);
                MLV_wait_milliseconds(50);  // Petite pause pour la visualisation
            }
        }
    }

    return revealed + 1;  // Inclut la cellule actuelle dans le compte
}