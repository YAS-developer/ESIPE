#include <MLV/MLV_all.h>
#include <stdbool.h>
#include "sudoku.h"
#include "graphics.h"
#include "time.h"
#include <pthread.h>
#include <unistd.h> 

/* Initialisation du jeu : Remplit les grilles du jeu (grille de réponse, grille originale, grille de jeu et grille résolue) */
void init_game(SudokuGame *game, Board grid){
    game->row=0;
    game->col=0;
    int number=0;
    int i, j;
    for(i=0; i<3; i++){
        for(j=0; j<3; j++){
            number++;
            game->grid_answer[i][j]=number;
        }
    }

    for(i=0; i<SIZE; i++){
        for(j=0; j<SIZE; j++){
            game->original_grid[i][j] = grid[i][j];
            game->grid[i][j] = grid[i][j];
            game->solved_grid[i][j] = grid[i][j];
        }
    }

    solve_sudoku(game->solved_grid);
    time(&game->start_time);
}


/* Fonction de mise à jour du timer exécutée dans un thread séparé. 
   Calcule le temps écoulé depuis le début du jeu et affiche le timer 
   en minutes et secondes sur l'écran du jeu. */
void *update_timer(void *arg){
    SudokuGame *game = (SudokuGame *)arg;  

    while(1){
        time_t current_time;
        time(&current_time);
        int elapsed = difftime(current_time, game->start_time);
        int minutes = elapsed / 60;
        int seconds = elapsed % 60;

        char timer_text[10];
        sprintf(timer_text, "%02d:%02d", minutes, seconds);

        MLV_draw_filled_rectangle(1100, 10, 50, 30, MLV_COLOR_BLACK); 
        MLV_draw_text(1108, 18, timer_text, MLV_COLOR_GREEN); 
        MLV_actualise_window();

        sleep(1); 
    }

    return NULL;
}



/* Initialisation graphique : Crée la fenêtre de jeu et dessine le plateau initial */
void init_graphics(){
    MLV_create_window("Frame", NULL, 1200, 800);
    MLV_draw_filled_rectangle(0, 0, 1200, 880, MLV_COLOR_CADET_BLUE);
    int i;
    for(i=1; i<=SIZE+1; i++){
        MLV_draw_line(60, 60*i, 600, 60*i, MLV_COLOR_ANTIQUE_WHITE);
        MLV_draw_line(60*i, 60, 60*i, 600, MLV_COLOR_ANTIQUE_WHITE);
    }
    MLV_actualise_window();
}



/* Mise à jour graphique du plateau : Dessine les chiffres sur le plateau et vérifie la condition de victoire */
int update_graphics_board(SudokuGame *game){

    int number=0;
    char str[2];
    int addX=0, addY=0;
    int i, j;
    for(i=0; i<SIZE; i++){
        for(j=0; j<SIZE; j++){
            number = game->grid[i][j];
            sprintf(str, "%d", number);
            if(number == 0){
                MLV_draw_filled_rectangle(85+addX, 85+addY, 20, 20, MLV_COLOR_CADET_BLUE);
            }
            else{
                MLV_draw_filled_rectangle(85+addX, 85+addY, 20, 20, MLV_COLOR_CADET_BLUE);
                MLV_draw_text(85+addX, 85+addY, str, MLV_COLOR_ANTIQUE_WHITE);
            }
            addX+=60; 
        }
        addX=0;
        addY+=60;
    }
     MLV_actualise_window();

    /* VICTORY */ 
    for(i=0; i<SIZE; i++){
        for(j=0; j<SIZE; j++){
            if(game->grid[i][j] != game->solved_grid[i][j]){
                return 0;
            }
        }
    }

    MLV_draw_filled_rectangle(0, 0, 1200, 800, MLV_COLOR_CADET_BLUE);
    MLV_draw_text(550, 350,"VICTOIRE", MLV_COLOR_GREEN);
    MLV_actualise_window();
    return 1;
}


/* Vérification des coordonnées : Détermine la cellule cliquée par l'utilisateur et permet de saisir une réponse si la cellule est modifiable */
void check_coord(SudokuGame *game, int x, int y){
    int currentX = 60, currentY = 60;
    int i, j;
    for (i = 0; i < SIZE; i++) {
        currentX = 60; 
        for (j = 0; j < SIZE; j++) {
            if (x >= currentX && x <= (currentX + 60) && y >= currentY && y <= (currentY + 60)) {
                if(game->original_grid[i][j] == 0){
                    game->row = i; game->col = j;
                    MLV_draw_filled_rectangle(currentX+25, currentY+25, 10, 20, MLV_COLOR_CADET_BLUE);
                    MLV_draw_text(currentX+25, currentY+25, "?", MLV_COLOR_GREEN);
                    answer_draw(game);
                    
                }
            }
            currentX += 60; 
        }
        currentY += 60; 
    }
    MLV_actualise_window();
}


/* Affichage des réponses possibles : Dessine les options de numéros à choisir pour la cellule sélectionnée */
void answer_draw(SudokuGame *game){
    int add=0, addX=0, addY=0;
    char str[2];
    int i, j;
    for(i=0; i<=ANSWER_SIZE; i++){
        MLV_draw_line(750, 240+add, 930, 240+add, MLV_COLOR_ANTIQUE_WHITE);
        MLV_draw_line(750+add, 240, 750+add, 420, MLV_COLOR_ANTIQUE_WHITE);
        add+=60;
        if(i != ANSWER_SIZE){
            for(j=0; j<ANSWER_SIZE; j++){
                sprintf(str, "%d", game->grid_answer[i][j]);
                MLV_draw_text(775+addX, 260+addY, str, MLV_COLOR_ANTIQUE_WHITE);
                addX+=60;
            }
        }
        addX=0;
        addY+=60;
    }
    MLV_draw_filled_rectangle(750, 440, 180, 60, MLV_COLOR_RED);
    char* txt="Annuler";
    MLV_draw_text(805, 460, txt, MLV_COLOR_ANTIQUE_WHITE);


    MLV_actualise_window();

    int x=0,y=0;
    while(1){
        MLV_wait_mouse(&x, &y);
        if(x > 0 || y > 0){
            if(check_coord_answer(game, x, y)){
                break;
            }
        }
        x=0;y=0;
    }
}

/* Vérification des coordonnées pour la réponse : Détermine si l'utilisateur a sélectionné une réponse valide ou l'option annuler */
bool check_coord_answer(SudokuGame *game, int x, int y){
    if(x>= 750 && x <= (750+180) && y >= 440 && y <= 500){
        update_graphics_board(game);
        delete_answer_draw();
        return true;
    }
    int i, j;
    int currentX = 750, currentY = 240;
    for (i = 0; i < ANSWER_SIZE; i++) {
        currentX = 750; 
        for (j = 0; j < ANSWER_SIZE; j++) {
            if (x >= currentX && x <= (currentX + 60) && y >= currentY && y <= (currentY + 60)) {
                int number = game->grid_answer[i][j];
                if(is_valid_move(game->grid, game->row, game->col, number)){
                    game->grid[game->row][game->col]=number;
                    update_graphics_board(game);
                    delete_answer_draw();
                    return true;
                }
                else{
                    char error_message[50];
                    sprintf(error_message, "On ne peut pas mettre ce numero: %d", number);
                    MLV_draw_filled_rectangle(190, 10, 280, 30, MLV_COLOR_RED); /* Dessine un fond rouge pour le message */
                    MLV_draw_text(200, 20, error_message, MLV_COLOR_WHITE); /* Affiche le message d'erreur en blanc */
                    MLV_actualise_window(); /* Met à jour la fenêtre pour afficher le nouveau contenu */
                    MLV_wait_seconds(1); /* Attend 2 secondes avant de continuer */
                    MLV_draw_filled_rectangle(190, 10, 280, 30, MLV_COLOR_CADET_BLUE); /* Efface le message d'erreur */
                    MLV_actualise_window(); /* Met à jour la fenêtre après avoir effacé le message */
                }
            }
            currentX += 60; 
        }
        currentY += 60; 
    }

    return false;
}


/* Effacement de l'affichage des réponses : Supprime graphiquement les options de numéros à choisir */
void delete_answer_draw(){
    int add=0;
    int i;
    for(i=0; i<=ANSWER_SIZE; i++){
        MLV_draw_line(750, 240+add, 930, 240+add, MLV_COLOR_CADET_BLUE);
        MLV_draw_line(750+add, 240, 750+add, 420, MLV_COLOR_CADET_BLUE);
        add+=60;
    }
    MLV_draw_filled_rectangle(750, 240, 930, 420, MLV_COLOR_CADET_BLUE);
}


/* Fonction principale pour la gestion graphique : Initialise le jeu et gère les interactions utilisateur */
void graphics_main(Board grid){
    SudokuGame game;
    init_game(&game, grid);
    init_graphics();
    update_graphics_board(&game);

    pthread_t timer_thread;
    pthread_create(&timer_thread, NULL, update_timer, (void *)&game);

    int x=0,y=0;
    while(1){
        MLV_wait_mouse(&x, &y);
        if(x > 0 || y > 0){
            check_coord(&game, x, y);
        }
        x=0;y=0;
    }

    pthread_join(timer_thread, NULL); 
}
