#include <unistd.h>
#include "../try.h"

int main(void) {
  try(execlp("ls", "ls", (char *) NULL));
}
