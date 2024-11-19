#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include "../try.h"
#include <string.h>

#define BUFSIZE 8192

int main(int argc, char** argv){

  

  int pipefd[2];

  try(pipe(pipefd));
  pid_t pid;

  switch(pid = try(fork())){
    case 0:{
      try(close(pipefd[0]));
      char* buf = "Hello word";
      try(write(pipefd[1], buf, strlen(buf)));
      try(close(pipefd[1]));
      return 0;
    }

    default:{
      int status;
      try(wait(&status));

      if(WIFEXITED(status)){
        printf("Le processus enfant %d s'est bien termine\n", pid);
      }
      else if(WIFSIGNALED(status)){
        printf("Le processus enfant termine par un signal: %d\n", WTERMSIG(status));

      }
      else{
        printf("Le processus enfant %d termine de maniere inconnu\n", pid);
      }
      


      try(close(pipefd[1]));
      char buf[BUFSIZE];
      try(read(pipefd[0], buf, sizeof(buf)));
      printf("%s\n", buf);

      try(close(pipefd[0]));

      break;
    }
  }
}