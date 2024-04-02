#include "filter.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_PASSWORD_LENGTH 256

// Prototypes de fonctions supplémentaires
void load_passwords_into_filter(filter *f, const char *filename);

int main(int argc, char **argv) {
    // Vérifiez que l'utilisateur a fourni le fichier de mots de passe
    if (argc < 2) {
        fprintf(stderr, "Usage: %s <fichier de mots de passe>\n", argv[0]);
        return EXIT_FAILURE;
    }

    const char *passwords_filename = argv[1];
    int m = 10000; // Taille du bitarray - exemple
    int k = 5;     // Nombre de fonctions de hachage - exemple

    // Créez le filtre de Bloom
    filter *password_filter = create_filter(m, k);

    // Chargez les mots de passe dans le filtre
    load_passwords_into_filter(password_filter, passwords_filename);

    // Exemple de vérification - à remplacer par la lecture de l'entrée utilisateur ou d'un fichier
    char password_to_check[MAX_PASSWORD_LENGTH];
    printf("Enter a password to check: ");
    if (scanf("%255s", password_to_check) == 1) {
        if (is_member_filter(password_filter, password_to_check)) {
            printf("Password might be in the set.\n");
        } else {
            printf("Password is definitely not in the set.\n");
        }
    }

    // Libérez la mémoire utilisée par le filtre de Bloom
    free_filter(password_filter);

    return EXIT_SUCCESS;
}

void load_passwords_into_filter(filter *f, const char *filename) {
    FILE *file = fopen(filename, "r");
    if (!file) {
        fprintf(stderr, "Could not open file: %s\n", filename);
        exit(EXIT_FAILURE);
    }

    char line[MAX_PASSWORD_LENGTH];
    while (fgets(line, sizeof(line), file)) {
        // Supprimez le retour à la ligne à la fin, s'il y en a un
        line[strcspn(line, "\n")] = 0;
        add_filter(f, line);
    }

    fclose(file);
}
