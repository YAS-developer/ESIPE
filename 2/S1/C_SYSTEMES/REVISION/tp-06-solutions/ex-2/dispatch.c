#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include "../try.h"

// == Architecture ==
// Each coprocess (add, sub, mul, div) is connected to the main program
// (dispatch) by two pipes: an incoming pipe through which it receives (reads)
// requests, and an outgoing pipe through which it sends (writes) results.
//                 ┌───────┐
//                 │       │
//                 │  add  │
//                 │       │
//                 └╴0╶─╴1╶┘
//              in/↑ ┃   ┃ ↓/out
//  ┌───────┐ ← ┌───╴1╶─╴0╶───┐ → ┌───────┐
//  │       0╺━╸1             1╺━╸0       │
//  │  mul  │   │   dispatch  │   │  div  │
//  │       1╺━╸0             0╺━╸1       │
//  └───────┘ → └───╴1╶─╴0╶───┘ ← └───────┘
//                 ↓ ┃   ┃ ↑
//                 ┌╴0╶─╴1╶┐
//                 │       │
//                 │  sub  │
//                 │       │
//                 └───────┘

static struct {
  char *name;
  int in;  // Write end of incoming pipe
  int out; // Read end of outgoing pipe
} proc[] = {{"add", 0, 0}, {"sub", 0, 0},
            {"mul", 0, 0}, {"div", 0, 0},
            {NULL, 0, 0}};

static void startProcs(void) {
  for (int i = 0; proc[i].name; i++) {
    int pIn[2], pOut[2];
    try(pipe(pIn));
    try(pipe(pOut));
    switch (try(fork())) {
    case 0:  // Child
      for (int j = 0; j < i; j++) { // Close pipes of previous children
        try(close(proc[j].in));
        try(close(proc[j].out));
      }
      try(dup2(pIn[0], STDIN_FILENO));
      try(dup2(pOut[1], STDOUT_FILENO));
      try(close(pIn[0]));
      try(close(pIn[1]));
      try(close(pOut[0]));
      try(close(pOut[1]));
      try(execl(proc[i].name, proc[i].name, (char *) NULL));
      break;
    default: // Parent
      try(close(pIn[0]));
      try(close(pOut[1]));
      proc[i].in = pIn[1];
      proc[i].out = pOut[0];
      break;
    }
  }
}

static void dispatch(void) {
  char buf[BUFSIZ];
  while (fgets(buf, sizeof(buf), stdin)) {
    char *procName = strtok(buf, " \t\n"); // First non-white-space token
    if (!procName) {
      procName = "";
    }
    char *request = strtok(NULL, "");      // Remainder of string in buffer
    if (!request || !strchr(request, '\n')) {
      request = "\n";
    }
    for (int i = 0; proc[i].name; i++) {
      if (!strcmp(procName, proc[i].name)) {
        try(write(proc[i].in, request, strlen(request)));
        ssize_t len = try(read(proc[i].out, buf, sizeof(buf)));
        try(write(STDOUT_FILENO, buf, len));
        goto next;
      }
    }
    printf("No process '%s'\n", procName);
  next:
    continue;
  }
}

int main(void) {
  startProcs();
  dispatch();
}
