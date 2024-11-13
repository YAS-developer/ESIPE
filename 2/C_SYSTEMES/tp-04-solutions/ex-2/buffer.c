#include <stdio.h>
#include <unistd.h>
#include "../try.h"

#define BSIZ 1024

int main(void) {
  char buf[BSIZ];
  printf("Mon PID est %ld. ", (long) getpid());
  snprintf(buf, sizeof(buf), "My PID is %ld. ", (long) getpid());
  try(write(STDOUT_FILENO, buf, strlen(buf)));
  switch (try(fork())) {
  case 0:  /* Child */
    printf("Je suis l'enfant et mon PID est %ld. ", (long) getpid());
    snprintf(buf, sizeof(buf), "I am the child and my PID is %ld. ", (long) getpid());
    try(write(STDOUT_FILENO, buf, strlen(buf)));
    break;
  default: /* Parent */
    printf("Je suis le parent et mon PID est %ld. ", (long) getpid());
    snprintf(buf, sizeof(buf), "I am the parent and my PID is %ld. ", (long) getpid());
    try(write(STDOUT_FILENO, buf, strlen(buf)));
    break;
  }
  puts("");
}

/* Par défaut, les fonctions de la bibliothèque `stdio` utilisent un buffer pour
   réduire le nombre d'appels système `write`, qui sont très coûteux. Lorsque
   l'affichage se fait sur un terminal, le buffer d'écriture est seulement vidé
   quand il est plein ou qu'il contient un retour à la ligne `\n`. (Ce
   comportement peut être modifié avec la fonction `setvbuf()`.) De plus, après
   un `fork()`, l'enfant hérite du buffer du parent (avec tout son contenu).

   Ceci explique pourquoi le message "Mon PID est %ld. " s'affiche deux fois
   (avec le PID du parent), alors que l'appel correspondant à `printf()` est
   fait avant le `fork()`. Cela explique aussi pourquoi les messages écrits avec
   `write()` (qui lui n'utilise jamais de buffer) s'affichent avant ceux écrits
   avec `printf()`.
*/
