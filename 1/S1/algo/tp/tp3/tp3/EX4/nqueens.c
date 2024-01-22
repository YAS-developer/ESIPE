#include <MLV/MLV_all.h>
#include <stdio.h>

#define N 8
#define WIDTH 800
#define HEIGHT 800
#define SQUARE_SIZE (WIDTH / N)

int board[N][N];


// Dessine la grille
void draw_board() {
    int i, j;
    for (i = 0; i < N; i++) {
        for (j = 0; j < N; j++) {
            if ((i + j) % 2 == 0)
                MLV_draw_filled_rectangle(j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, MLV_COLOR_GREY);
            else
                MLV_draw_filled_rectangle(j * SQUARE_SIZE, i * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, MLV_COLOR_WHITE);
        }
    }
}

// Dessine une reine sur une case donnée
void draw_queen(MLV_Image *queen_image, int i, int j) {
    MLV_draw_image(queen_image, j * SQUARE_SIZE, i * SQUARE_SIZE);
}

int is_attacked(int row, int col) {
    int i, j;
    // Vérifie les attaques dans la colonne, la diagonale gauche supérieure et la diagonale gauche inférieure
    
    for (i = 0; i < col; i++){
        if (board[row][i]){
            return 1;
        }
    }
        
            
    for (i = row, j = col; i >= 0 && j >= 0; i--, j--){
        if (board[i][j]){
            return 1;
        }
    }
        
            
    for (i = row, j = col; i < N && j >= 0; i++, j--){
        if (board[i][j]){
            return 1;
        }
    }
        
            
    return 0;
}

// Fonction récursive pour résoudre le problème des N reines
int solve(MLV_Image *queen_image, int col) {
    if (col >= N)
        return 1; // Si toutes les reines sont placées, retourne vrai
    int i;
    for (i = 0; i < N; i++) {
        // Tente de placer une reine dans chaque rangée de la colonne actuelle
        if (!is_attacked(i, col)) {
            board[i][col] = 1; 
            draw_queen(queen_image, i, col); 
            MLV_actualise_window(); 
            MLV_wait_milliseconds(500); 
            
            // Appelle récursivement la fonction pour placer les reines restantes
            if (solve(queen_image, col + 1)){
                 return 1; 
            }

            // Si placer une reine ici ne conduit pas à une solution, retire la reine (backtrack)
            board[i][col] = 0;
            draw_board(); 
            MLV_actualise_window(); 
        }
    }
    return 0; // Si aucune place n'est trouvée, retourne faux
}

int main() {
    MLV_Image *queen_image;

    MLV_create_window("N Queens Problem", "N Queens", WIDTH, HEIGHT);
    draw_board();

    queen_image = MLV_load_image("./king.png"); 
    if (queen_image == NULL) {
        fprintf(stderr, "Erreur : l'image n'a pas pu être chargée depuis './king.jpg'.\n");
        MLV_free_window();
        return 1;
    }
    MLV_resize_image(queen_image, SQUARE_SIZE, SQUARE_SIZE);

    if (!solve(queen_image, 0)) {
        printf("Pas de solution\n");
    }

    MLV_wait_seconds(10);
    MLV_free_image(queen_image);
    MLV_free_window();
    return 0;
}
