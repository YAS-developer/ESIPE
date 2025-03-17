#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>
#include "../try.h"

#define BSIZ 64

int main(int argc, char **argv) {
  for (int i = 0; ; i++) {
    char key[BSIZ];
    snprintf(key, sizeof(key), "RUN_%d", i);
    char *val = getenv(key);
    if (!val) {
      break;
    }
    switch (try(fork())) {
    case 0:  /* Child */
      argv[0] = val;
      try(execvp(val, argv));
    default: /* Parent */
      try(wait(NULL));
    }
  }
}
