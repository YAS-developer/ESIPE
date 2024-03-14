
#include "cell.h"
#include "parser.h"
#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdlib.h>

int main(int argc, char** argv){
  
  int fd = open(argv[1], O_RDONLY);
  
  if(fd == -1){
    fprintf(stderr, "Erreur lors de l'ouverture du fichier %s", argv[1]);
  }
  
  readFile(fd);

  close(fd);

  return 0;
}
