#include <stdio.h>
#include <stdbool.h>

// Fonction pour compter le nombre de caractères, mots et lignes
void wc() {
    int caractere;
    int nbCaracteres = 0;
    int nbMots = 0;
    int nbLignes = 0;
    bool dansMot = false;

    while ((caractere = getchar()) != EOF) {
        // Compter le nombre de caractères
        nbCaracteres++;

        // Vérifier s'il s'agit d'un espace ou d'une tabulation
        if (caractere == ' ' || caractere == '\t') {
            dansMot = false;
        } 
        else if (caractere == '\n') {  // S'il s'agit d'un saut de ligne
            nbLignes++;
            dansMot = false;
        } 
        else {
            // S'il s'agit d'un caractère non-espace, et n'était pas déjà dans un mot
            if (!dansMot) {
                nbMots++;
                dansMot = true;
            }
        }
    }

    // Afficher les résultats
    printf("\nNombre de caractères : %d\n", nbCaracteres);
    printf("Nombre de mots : %d\n", nbMots);
    printf("Nombre de lignes : %d\n", nbLignes);
}

int main() {
    printf("Entrez du texte (Ctrl+D pour terminer) :\n");
    wc();

    return 0;
}