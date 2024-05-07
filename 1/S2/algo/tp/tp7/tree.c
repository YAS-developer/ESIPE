#include "tree.h"
#include <stdio.h>
#include <stdlib.h>

node *create_node(int data, node *left, node *right) {
    node *n = (node *)malloc(sizeof(node));
    n->data = data;
    n->left = left;
    n->right = right;
    return n;
}

/* Display the nodes of the tree t following a pre-order traversal */
void display_prefix(node *t) {
    if (t != NULL) {
        printf("%d ", t->data);  // Visit the root
        display_prefix(t->left); // Traverse left subtree
        display_prefix(t->right); // Traverse right subtree
    }
}

/* Display the nodes of the tree t following an in-order traversal */
void display_infix(node *t) {
    if (t != NULL) {
        display_infix(t->left); // Traverse left subtree
        printf("%d ", t->data);  // Visit the root
        display_infix(t->right); // Traverse right subtree
    }
}

/* Display the nodes of the tree t following a post-order traversal */
void display_suffix(node *t) {
    if (t != NULL) {
        display_suffix(t->left); // Traverse left subtree
        display_suffix(t->right); // Traverse right subtree
        printf("%d ", t->data);  // Visit the root
    }
}
