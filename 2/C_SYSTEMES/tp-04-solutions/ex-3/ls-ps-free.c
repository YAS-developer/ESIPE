#include <unistd.h>
#include "../try.h"

int main(void) {
  switch (try(fork())) {
  case 0:    /* Child 1 */
    try(execlp("ls", "ls", (char *) NULL));
  default:   /* Parent */
    switch (try(fork())) {
    case 0:  /* Child 2 */
      try(execlp("ps", "ps", (char *) NULL));
    default: /* Parent */
      try(execlp("free", "free", (char *) NULL));
    }
  }
}
