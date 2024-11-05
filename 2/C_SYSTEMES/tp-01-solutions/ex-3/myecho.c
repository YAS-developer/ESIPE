#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char** argv) {
  struct {
    char *sep;
    char *end;
  } cfg = {" ", "\n"};
  int opt;
  while ((opt = getopt(argc, argv, "nsS:")) != -1) {
    switch (opt) {
    case 'n':
      cfg.end = "";
      break;
    case 's':
      cfg.sep = "";
      break;
    case 'S':
      cfg.sep = optarg;
      break;
    default:
      fprintf(stderr, "Usage: %s [-n] [-s] [-S STRING]\n", argv[0]);
      exit(EXIT_FAILURE);
    }
  }
  char *sep = "";
  for (char **argp = argv + optind; *argp; argp++) {
    printf("%s%s", sep, *argp);
    sep = cfg.sep;
  }
  printf("%s", cfg.end);
}
