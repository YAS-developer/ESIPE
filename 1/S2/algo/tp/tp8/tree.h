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

/* Constructs a binary tree from a sequence of integers. */ 
node *scan_tree(void);



// Function to count all nodes in a tree
int count_nodes(node *t);


// Function to count leaves in a tree
int count_leaves(node *t);


// Recursively counts nodes that have only one child in the binary tree
int count_only_children(node *t);


// The height of a tree is the number of edges on the longest path from the root to a leaf
int height(node *t);


/* Find an element in the binary search tree */
node *find_bst(node *t, int elt);


/* Insert an element into the binary search tree */
node *insert_bst(node *t, int elt);


/*Check if the tree is BST*/
int is_bst(node *t);  

node* insert_random_elements(int N);

node* insert_sequential_elements(int N);


void free_tree(node *t);


#endif /* TREE_H */
