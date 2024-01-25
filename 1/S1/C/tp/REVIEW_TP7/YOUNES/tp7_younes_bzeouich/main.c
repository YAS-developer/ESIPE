#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "sudoku.h"
#include "in_out.h"

#include "interface.h"

int main(int argc, char* argv[]){
 
  
 Board B;

  fread_board(argv[1], B);
  print_board(B);


  int list[5] = {0,0,0,0,0};
  char* arr[5] = {"","","","",""};
  lire_list(argv[2], list, arr) ;
  joue(B,list, arr);
  ecrire_list(argv[2],list,arr);

  return 0;
}
