#include <stdio.h>
#include <stdlib.h>

#define SIZE 2000000

int syracuse(long unsigned int nb, int vol, long unsigned int* tab) {
    if (nb == 1) {
        return vol;
    }

    long unsigned int next_nb = 0;

    if (nb < SIZE && tab[nb] != -1) {
        // La valeur est déjà calculée, éviter de recalculer
        next_nb = tab[nb];
        // printf("non calculé: %ld ", next_nb);
        printf("%ld ", next_nb);
    } else {
        if (nb % 2 == 0) {
            next_nb = nb / 2;
        } else {
            next_nb = 3 * nb + 1;
        }

        if (nb < SIZE) {
            // Mettre en cache la valeur calculée
            tab[nb] = next_nb;
            // printf("calculé: %ld ", tab[nb]);
             printf("%ld ", next_nb);
        }
    }

    return syracuse(next_nb, vol + 1, tab);
}

int main(int argc, char **argv) {
    if (argc < 2) {
        fprintf(stderr, "Veuillez utiliser ce format: %s numéro\n", argv[0]);
        return EXIT_FAILURE;
    }

    long unsigned int* tab = malloc(sizeof(long unsigned int) * SIZE);
    if (!tab) {
        fprintf(stderr, "Erreur d'allocation mémoire.\n");
        return EXIT_FAILURE;
    }

    for (int i = 0; i < SIZE; i++) {
        tab[i] = -1;
    }

    int nb = atoi(argv[1]);
    printf("\n%d ", nb);

    int nb_vol = syracuse(nb, 0, tab);

    printf("\nLongueur de vol : %d\n", nb_vol);

    int nb_vol2 = syracuse(53, 0, tab);
    printf("\nLongueur de vol : %d\n", nb_vol2);

    free(tab);

    return EXIT_SUCCESS;
}
