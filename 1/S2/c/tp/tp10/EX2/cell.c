#include "cell.h"
#include <stdlib.h>

Cell* allocate_cell(char* first, char* last, int age){
  Cell* newCell = (Cell *)malloc(sizeof(Cell));
  
  newCell->first_name = first;
  newCell->last_name = last;
  newCell->age = age;

  return newCell;
}


int ageOrder(Cell* p1, Cell *p2){
  if(p1->age > p2->age){
    return p1->age;
  }
  return p2->age;
}

int nameOrder(Cell* p1, Cell *p2){return 0;}

void  orderedInsertion(List* l, Cell* newCell, int order_func(Cell*, Cell*)){}

void printList(List l){}

void freeList(List l){}