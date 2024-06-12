#ifndef FOO_H
#define FOO_H

#include <stdio.h>

// Déclaration des types de données
typedef struct plop {
    int foobar;
    char *barfoo;
} Plop;

typedef struct plipinternal {
    int flic;
    int flac;
} Plipinternal;

// Définition des constantes
#define GLOBAL_WEIRD 12
#define GLOBAL_STRING "example string"
#define GLOBAL_DOUBLE 3.14159

// Déclaration des fonctions
int bla(FILE *in, int nbentries);
void bar(int pouet, int pouetpouet);
int babar(Plop *tab, int nbplop);

#endif // FOO_H
