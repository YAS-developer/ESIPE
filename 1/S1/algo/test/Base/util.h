#ifndef UTIL_H
#define UTIL_H

/* Créer un tableau d'entiers de taille 'size' */
int *create_array(int size);

/* Remplir un tableau avec des entiers aléatoires entre 0 et mod-1 */
void fill_random_array(int t[], int size, int mod);

/* Afficher le contenu du tableau t de taille 'size' */
void display_array(int t[], int size);

/* Trier un tableau d'entiers en ordre croissant */
void sort(int t[], int size);

#endif /* UTIL_H */
