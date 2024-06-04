#ifndef AVL_H
#define AVL_H

typedef struct node {
    int data;
    int height;
    struct node *left;
    struct node *right;
} node;

/* Create a new node */
node *create_node(int elt, node *left, node *right);

/* Free the tree */
void free_tree(node *t);

/* Find an element in the AVL tree */
node *find_avl(node *t, int elt);

/* Update height of a node */
void update_height(node *t);

/* Compute balance factor */
int compute_balance(node *t);

/* Rotate right */
node *rotate_right(node *t);

/* Rotate left */
node *rotate_left(node *t);

/* Rotate left-right */
node *rotate_left_right(node *t);

/* Rotate right-left */
node *rotate_right_left(node *t);

/* Rebalance the tree */
node *rebalance(node *t);

/* Insert an element into the AVL tree */
node *insert_avl(node *t, int elt);

/* Find the minimum node in the AVL tree */
node *find_min(node *t);

/* Remove the minimum node from the AVL tree */
node *remove_min(node *t);

/* Remove an element from the AVL tree */
node *remove_avl(node *t, int elt);

/* Check if the tree is AVL */
int is_avl(node *t);

/* Fill an array with a random permutation of 1, 2, ..., n */
void fill_random_permutation(int *array, int n);

#endif
