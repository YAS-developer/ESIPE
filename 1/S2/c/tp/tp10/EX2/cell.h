#ifndef __CELL__
#define __CELL__


typedef struct  cell
{
  char* first_name;
  char* last_name;
  int age;
  struct cell* next; 
}Cell, *List;

Cell* allocate_cell(char* first, char* last, int age);

int ageOrder(Cell* p1, Cell *p2);

int nameOrder(Cell* p1, Cell *p2);

void  orderedInsertion(List* l, Cell* newCell, int order_func(Cell*, Cell*));

void printList(List l);

void freeList(List l);


#endif

