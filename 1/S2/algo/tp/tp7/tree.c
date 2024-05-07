#include "tree.h"
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

node *create_node(int data, node *left, node *right) {
    node *n = (node *)malloc(sizeof(node));
    assert(n != NULL);
    n->data = data;
    n->left = left;
    n->right = right;
    return n;
}
