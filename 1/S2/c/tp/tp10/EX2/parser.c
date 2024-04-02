#include "cell.h"
#include <fcntl.h>
#include <unistd.h>
#include <stdlib.h>

void readFile(int fd){
  List* list = (List*)malloc(sizeof(List));
  char buf; char* tmp = (char*)malloc(sizeof(char));
  
  int sz=0, i=0;
  while((sz = read(fd, &buf, 1))){
    if(buf == '\n'){
      /*appeler la méthode*/ 
      i=0;
    }
    tmp[i] = buf;
    i++;
  }

  // list->next=parseList(fd);



  return list;
}


List* parseLine(char* line){
  // List* lst = (List *)malloc(sizeof(List));
  // int i=0;
  // char* tmp = (char*)malloc(sizeof(char)); 
  // for(i=0;i<strlen(line); i++){
  //   if(line[] == )
  // }
  return NULL;
}



