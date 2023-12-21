#include <stdio.h>
#include <MLV/MLV_all.h>
#include "sudoku.h"
#include "in_out.h"
#include "graphics.h"





int main(int argc, char* argv[]){
  Board originalB;
  Board B;

  if (argc != 2){
    fprintf(stderr, "Usage: %s <file>\n", argv[0]);
    return 1;
  }

  fread_board(argv[1], originalB);
  fread_board(argv[1], B);
  
  

  init_graphics();
  update_graphics_board(B);
  graphics_main(B);



  // if (solve_sudoku(B)){
  //   printf("Solved board:\n");
  //   print_board(B);
  // }
  // else{
  //   printf("No solution found\n");
  // }


  return 0;
}


