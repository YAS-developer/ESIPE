#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>
#include "../try.h"

// Alternative pipeline architecture in the form of a staircase.
// A pipeline of the form CMD1 | CMD2 | ... | CMD_n
// is represented by the following process hierarchy:
//
//  Command n
//  └── Command (n - 1)
//      └── ...
//          └── Command 2
//              └── Command 1
//
// Note that the last command is at the root of the tree.
// This is important for the exit status of the program
// because the executing shell will only wait for the root.

int main(int argc, char **argv) {
  if (argc < 2) {
    fprintf(stderr, "Usage: %s CMD1 [CMD2 [CMD3 ...]]\n", argv[0]);
    exit(EXIT_FAILURE);
  }
  int nextPipe1;   // Write end of the next pipe
  int prevPipe[2]; // Read and write ends of the previous pipe
  for (int i = argc - 1; i > 1; i--) {
    bool isLastCmd = (i == argc - 1);
    try(pipe(prevPipe));
    switch (try(fork())) {
    case 0:  // Child (continues loop)
      if (!isLastCmd) {
        try(close(nextPipe1));
      }
      try(close(prevPipe[0]));
      nextPipe1 = prevPipe[1];
      break;
    default: // Parent (executes i-th command)
      if (!isLastCmd) {
        try(dup2(nextPipe1, STDOUT_FILENO));
        try(close(nextPipe1));
      }
      try(dup2(prevPipe[0], STDIN_FILENO));
      try(close(prevPipe[0]));
      try(close(prevPipe[1]));
      try(execlp(argv[i], argv[i], (char *) NULL));
      break;
    }
  }
  // Last created process (executes first command)
  bool isLastCmd = (1 == argc - 1);
  if (!isLastCmd) {
    try(dup2(nextPipe1, STDOUT_FILENO));
    try(close(nextPipe1));
  }
  try(execlp(argv[1], argv[1], (char *) NULL));
}
