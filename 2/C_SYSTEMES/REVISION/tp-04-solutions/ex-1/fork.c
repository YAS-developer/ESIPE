#include <stdio.h>
#include <unistd.h>
#include "../try.h"

int main(void) {
  printf("Bonjour\n");
  try(fork());
  printf("Au revoir\n");
}
