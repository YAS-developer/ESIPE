#ifndef TREE_H
#define TREE_H

typedef struct _node {
    int data;                /* data stored : an integer    */
    struct _node *left;      /* pointer to the left child   */
    struct _node *right;     /* pointer to the right child  */
} node;

/*
 * Allocate memory for a new node.
 */
node *create_node(int data, node *left, node *right);

#endif /* TREE_H */
