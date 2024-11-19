#include <stdbool.h>
#include <unistd.h>
#include "../try.h"

#define NUM 20

int main(void) {
  for (int i = 0; i < NUM; i++) {
    switch (try(fork())) {
    case 0:; /* Child */
      char buf[BUFSIZ];
      snprintf(buf, sizeof(buf),
               "Je suis le numéro %02d, mon PID est %ld, et mon parent est %ld.\n",
               i + 1, (long) getpid(), (long) getppid());
      try(write(STDOUT_FILENO, buf, strlen(buf)));
      exit(EXIT_SUCCESS);  // <-- Sans ça, les enfants créent aussi des enfants !
    default: /* Parent */
      break;
    }
  }
  /* Le parent reste en vie, mais ne fait pas de `wait()`.
     Par conséquent, ses enfants restent en tant que zombies. */
  while (true) {
    pause();
  }
}
