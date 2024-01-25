#ifndef _INTERFACE_
#define _INTERFACE_
#include "sudoku.h"


#define HEIGHT_WINDOWS_PIX 800
#define WIDTH_WINDOWS_PIX 1200

#define WAIT_TIME_MILLISEC 500
typedef  struct 
{
    int x ;
    int y ;
} point;

void colore_la_casse(int x, int y);
void win_the_game(int* list,char* arr[]);
void joue (Board b,int* list,char* arr[]);
void drow_table(int* list,char* arr[]);
void ModifListDeTOP5(int* list,char* arr[]);
void create_windows(void);
void rampil_windows(void);
void joue_window(Board b);
void actualise_window(void);
void AFFICHE_NUMERO_window(void);
int numero_clicke_dans_le_numbre(int x , int y);
void affiche_suduko_window(Board b,Board original);
point cordnne_de_point_clicke_dans_la_suduko(int x ,int y);
int clike_dans_sudoko(int x ,int y);
int clike_dans_nume(int x , int y);
void vide_la_casse( int x , int y );
int droit_a_change(int x , int y ,Board b);
void copy_bord(Board b , Board  deuxeme);
void efface_num();
#endif
