#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>
#include "../try.h"

// Pipeline architecture used by the classic Bourne shell.
// A pipeline of the form CMD1 | CMD2 | ... | CMD_n
// is represented by the following process hierarchy:
//
//  Command n
//  ├── Command 1
//  ├── Command 2
//  │   ...
//  └── Command (n - 1)
//
// Note that the last command is at the root of the tree.
// This is important for the exit status of the program
// because the executing shell will only wait for the root.

int main(int argc, char **argv) {
  if (argc < 2) {
    fprintf(stderr, "Usage: %s CMD1 [CMD2 [CMD3 ...]]\n", argv[0]);
    exit(EXIT_FAILURE);
  }
  int prevPipe0;   // Read end of the previous pipe
  int nextPipe[2]; // Read and write ends of the next pipe
  for (int i = 1; i < argc - 1; i++) {
    bool isFirstCmd = (i == 1);
    try(pipe(nextPipe));
    switch (try(fork())) {
    case 0:  // Child (executes i-th command)
      if (!isFirstCmd) {
        try(dup2(prevPipe0, STDIN_FILENO));
        try(close(prevPipe0));
      }
      try(dup2(nextPipe[1], STDOUT_FILENO));
      try(close(nextPipe[0]));
      try(close(nextPipe[1]));
      try(execlp(argv[i], argv[i], (char *) NULL));
      break;
    default: // Parent (continues loop)
      if (!isFirstCmd) {
        try(close(prevPipe0));
      }
      try(close(nextPipe[1]));
      prevPipe0 = nextPipe[0];
      break;
    }
  }
  // Parent (executes last command)
  bool isFirstCmd = (argc - 1 == 1);
  if (!isFirstCmd) {
    try(dup2(prevPipe0, STDIN_FILENO));
    try(close(prevPipe0));
  }
  try(execlp(argv[argc - 1], argv[argc - 1], (char *) NULL));
}
