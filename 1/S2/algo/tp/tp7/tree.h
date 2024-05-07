#ifndef TREE_H
#define TREE_H

typedef struct _node {
    int data;                /* data stored : an integer */
    struct _node *left;      /* pointer to the left child */
    struct _node *right;     /* pointer to the right child */
} node;

/* Allocate memory for a new node */
node *create_node(int data, node *left, node *right);

/* Display the nodes of the tree t following a pre-order traversal */
void display_prefix(node *t);

/* Display the nodes of the tree t following an in-order traversal */
void display_infix(node *t);

/* Display the nodes of the tree t following a post-order traversal */
void display_suffix(node *t);

#endif /* TREE_H */
