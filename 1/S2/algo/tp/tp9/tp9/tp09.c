#include "avl.h"
#include "visualtree.h"
#include <stdio.h>
#include <stdlib.h>

int main() {
    node *t = NULL;
    for (int i = 0; i < 100; i++) {
        t = insert_avl(t, i);
        if (!is_avl(t)) {
            printf("The tree is not balanced\n");
            exit(-1);
        }
    }
    printf("Ok!\n");
    write_tree(t);
    free_tree(t);

    return 0;
}
