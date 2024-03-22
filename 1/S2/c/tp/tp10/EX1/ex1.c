#include <stdio.h>
#include <stdlib.h>

void swap_mem(void* z1, void* z2, size_t size){
  char tmp;
  size_t i;
  for (i = 0; i < size; i++) {
      tmp = *(char*)(z1 + i);
      *(char*)(z1 + i) = *(char*)(z2 + i);
      *(char*)(z2 + i) = tmp;
  }
}



int main(int argc, char **argv){
  
  int a = 1, b=0;
  
  printf("a: %d, b: %d\n", a, b);
  swap_mem(&a, &b, sizeof(int));
  printf("a: %d, b: %d\n", a, b);
  return 0;
}