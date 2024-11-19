#include <stdbool.h>
#include <sys/wait.h>
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
      exit(i);  // <-- Permet au parent de récupérer le numéro de l'enfant.
    default: /* Parent */
      break;
    }
  }
  pid_t pid;
  int wstatus;
  while ((pid = wait(&wstatus)) != -1) {
    if (!WIFEXITED(wstatus)) {
      continue;
    }
    char buf[BUFSIZ];
    snprintf(buf, sizeof(buf),
             "Le processus numéro %02d de PID %ld vient de terminer.\n",
             WEXITSTATUS(wstatus) + 1, (long) pid);
    try(write(STDOUT_FILENO, buf, strlen(buf)));
  }
}
