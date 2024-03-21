#include "taquin_back.h"
#include <time.h>



void InitialisationPlateau(Plateau *P) {
    int i, j;
    for (i = 0; i < NB_LIG; i++) {
        for (j = 0; j < NB_COL; j++) {
            P->bloc[i][j].lig = i;
            P->bloc[i][j].col = j;
        }
    }
}

void ShufflePlateau(Plateau *P) {
    srand(time(NULL));
    int i, move;
    for (i = 0; i < 120; i++) { 
        move = rand() % 4; 
        MoveEmptySquare(P, move);
    }
}


bool CheckWin(const Plateau *P) {
    for (int i = 0; i < NB_LIG; i++) {
        for (int j = 0; j < NB_COL; j++) {
            if (P->bloc[i][j].lig == -1 && P->bloc[i][j].col == -1) {
                if (i != NB_LIG-1 || j != NB_COL-1) return false;
            } else if (P->bloc[i][j].lig != i || P->bloc[i][j].col != j) {
                return false;
            }
        }
    }
    return true;
}