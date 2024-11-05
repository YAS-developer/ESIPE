#include <stdio.h>

int main(void) {
  setbuf(stdin, NULL);
  setbuf(stdout, NULL);
  int c;
  while ((c = fgetc(stdin)) != EOF) {
    fputc(c, stdout);
  }
}
