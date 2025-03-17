#include <stdio.h>
#include <sys/stat.h>
#include <unistd.h>
#include "../try.h"

int main(int argc, char **argv) {
  if (argc != 2) {
    fprintf(stderr, "Usage: %s FILE\n", argv[0]);
    exit(EXIT_FAILURE);
  }
  struct stat sb;
  try(lstat(argv[1], &sb));
  printf("%ld %ldB %lds ",
         sb.st_ino, sb.st_size, sb.st_mtime);
  if (!S_ISLNK(sb.st_mode)) {
    printf("%s\n",
           S_ISREG(sb.st_mode) ? "f" :
           S_ISDIR(sb.st_mode) ? "d" : "?");
  }
  else {
    char buf[BUFSIZ];
    ssize_t len = try(readlink(argv[1], buf, sizeof(buf) - 1));
    buf[len] = '\0';
    printf("l->%s\n", buf);
  }
}
