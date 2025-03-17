#include <stdlib.h>
#include <unistd.h>
#include "../try.h"

#define BSIZ 64

int main(void) {
  for (int i = 0; ; i++) {
    char key[BSIZ];
    snprintf(key, sizeof(key), "RUN_%d", i);
    const char *val = getenv(key);
    if (!val) {
      break;
    }
    printf("%s=%s\n", key, val);
  }
}
