#include <stdio.h>
#include <stdlib.h> 
#include "in_out.h"

int fread_board(const char* file, Board board){
  FILE* f;
  int i,j;
  int entry;

  f = fopen(file, "r");
  if (f == NULL){
    fprintf(stderr, "Erreur d'ouverture du fichier %s\n", file);
    return 0;
  }

  for (i=0 ; i<9 ; i++){
    for (j=0 ; j<9 ; j++){
      fscanf(f, "%d", &entry);
      board[i][j] = entry;
    }
  }
  return 1;
}
int ecrire_list(const char* file, int* list, char* arr[]) {
    
    FILE* fp = fopen(file, "w");
    if (fp == NULL) {
        perror("Erreur d'ouverture du fichier ");
        return 1;
    }

   int i;
    for (i = 0; i < 5; i++) {
        fprintf(fp, "%s %d\n", arr[i], list[i]);
    }

    
    fclose(fp);

    return 0;
}


int lire_list(const char* file, int* list,char* arr[]){
     FILE *f = fopen(file, "r");
    if (f == NULL){
    fprintf(stderr, "Erreur d'ouverture du fichier %s\n", file);
    return 0;
  }

    int i;
    for (i = 0; i < 5; i++) {
        arr[i] = (char*)malloc(100 * sizeof(char)); 
        
        if (fscanf(f, "%s %d", arr[i], &list[i]) != 2) {
            arr[i][0] = '\0'; 
            list[i] = 0;
            
        }
    }
    fclose(f);

    return 1;
}
