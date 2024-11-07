#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

int main() {
    int pipefd[2];  // Tableau pour stocker les descripteurs de fichier du pipe
    char message[] = "Bonjour du processus parent!";
    char buffer[100];
    pid_t pid;

    // Création du pipe
    if (pipe(pipefd) == -1) {
        perror("Erreur création pipe");
        exit(1);
    }

    // Création du processus fils
    pid = fork();

    if (pid < 0) {
        perror("Erreur fork");
        exit(1);
    }

    if (pid > 0) {  // Processus parent
        // Ferme l'extrémité de lecture car on va seulement écrire
        close(pipefd[0]);
        
        // Écrit dans le pipe
        write(pipefd[1], message, strlen(message) + 1);
        printf("Parent: Message envoyé: %s\n", message);
        
        // Ferme l'extrémité d'écriture
        close(pipefd[1]);
    }
    else {  // Processus fils
        // Ferme l'extrémité d'écriture car on va seulement lire
        close(pipefd[1]);
        
        // Lit depuis le pipe
        read(pipefd[0], buffer, sizeof(buffer));
        printf("Fils: Message reçu: %s\n", buffer);
        
        // Ferme l'extrémité de lecture
        close(pipefd[0]);
    }

    return 0;
}