#include <stdio.h>
#include <sys/stat.h>
#include "../try.h"

int main(int argc, char **argv) {
  if (argc != 2) {
    fprintf(stderr, "Usage: %s FILE\n", argv[0]);
    exit(EXIT_FAILURE);
  }
  struct stat sb;
  try(stat(argv[1], &sb));
  printf("%ld %ldB %lds %s\n",
         sb.st_ino, sb.st_size, sb.st_mtime,
         S_ISREG(sb.st_mode) ? "f" :
         S_ISDIR(sb.st_mode) ? "d" : "?");
}
