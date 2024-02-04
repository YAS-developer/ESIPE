#include <stdio.h>
#include <stdlib.h>
#include "util.h"
#include "exercices.h"

int main() {


    char* str="UNIVEIFFEL";
    copy(str, 6, 9, 0);
    printf("%s\n", str);

    return 0;
}
