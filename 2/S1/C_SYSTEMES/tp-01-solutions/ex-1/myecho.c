#include <stdio.h>

int main(int argc, char** argv) {
  char *sep = "";
  for (char **argp = argv + 1; *argp; argp++) {
    printf("%s%s", sep, *argp);
    sep = " ";
  }
  printf("\n");
}
