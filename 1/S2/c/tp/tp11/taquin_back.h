#ifndef TAQUIN_BACK_H
#define TAQUIN_BACK_H

#define NB_COL 4
#define NB_LIG 4

typedef struct carre {
    int lig;
    int col;
} Carre;

typedef struct plateau {
    Carre bloc[NB_COL][NB_LIG];
} Plateau;



void InitialisationPlateau(Plateau *P);

void ShufflePlateau(Plateau *P);

bool MoveEmptySquare(Plateau *P, int direction);

bool CheckWin(const Plateau *P);


#endif
