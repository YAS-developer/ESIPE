#include <fcntl.h>
#include <unistd.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "../try.h"

static void writeAll(int fd, const void *buf, size_t len) {
  while (len > 0) {
    ssize_t wrLen = try(write(fd, buf, len));
    buf += wrLen;
    len -= wrLen;
  }
}

static void writeNum(int fd, const char *format, int num) {
  char buf[BUFSIZ];
  snprintf(buf, sizeof(buf), format, num);
  writeAll(fd, buf, strlen(buf));
}

static size_t lineLength(const char *linePtr, size_t max) {
  size_t i = 0;
  while (i < max) {
    if (linePtr[i++] == '\n') {
      break;
    }
  }
  return i;
}

static void catDefault(int fd) {
  char buf[BUFSIZ];
  ssize_t rdLen;
  while ((rdLen = try(read(fd, buf, sizeof(buf)))) > 0) {
    writeAll(STDOUT_FILENO, buf, rdLen);
  }
}

static void catNumbered(int fd) {
  bool atLineStart = true;
  size_t lineNum = 1;
  char buf[BUFSIZ];
  ssize_t rdLen;
  while ((rdLen = try(read(fd, buf, sizeof(buf)))) > 0) {
    char *linePtr = buf;
    while (rdLen > 0) {
      if (atLineStart) {
        writeNum(STDOUT_FILENO, "%6d\t", lineNum++);
      }
      size_t lineLen = lineLength(linePtr, rdLen);
      writeAll(STDOUT_FILENO, linePtr, lineLen);
      atLineStart = linePtr[lineLen - 1] == '\n';
      linePtr += lineLen;
      rdLen -= lineLen;
    }
  }
}

int main(int argc, char **argv) {
  void (*cat)(int fd) = catDefault;
  int opt;
  while ((opt = getopt(argc, argv, "n")) != -1) {
    switch (opt) {
    case 'n':
      cat = catNumbered;
      break;
    default:
      fprintf(stderr, "Usage: %s [-n]\n", argv[0]);
      exit(EXIT_FAILURE);
    }
  }
  if (optind == argc) {
    cat(STDIN_FILENO);
  }
  else {
    for (char **argp = argv + optind; *argp; argp++) {
      if (!strcmp(*argp, "-")) {
        cat(STDIN_FILENO);
      }
      else {
        int fd = open(*argp, O_RDONLY);
        if (fd == -1) {
          perror(*argp);
          continue;
        }
        cat(fd);
        try(close(fd));
      }
    }
  }
}
