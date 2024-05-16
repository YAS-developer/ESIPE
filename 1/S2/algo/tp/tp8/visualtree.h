#ifndef VISUALTREE_H
#define VISUALTREE_H
#include "stdio.h"
#include "tree.h"  // Include this since the functions use the node structure

// Function to open a file and start writing the DOT code for a tree.
// Returns a pointer to the file.
FILE* write_begin(char *name);

// Function to write the terminating brace and close the file.
void write_end(FILE *f);

// Function to write the DOT code for a single node n to an open file f.
void write_node(FILE *f, node *n);

// Function to write the DOT code declaring a left child of node n to an open file f.
void write_left_link(FILE *f, node *n);

// Function to write the DOT code declaring a right child of node n to an open file f.
void write_right_link(FILE *f, node *n);

// Function to recursively write DOT code for the tree starting from node t.
void write_tree_aux(FILE *f, node *t);

// Function to open a file current-tree.dot, write the DOT code for a tree t, 
// and convert the .dot-file to a PDF.
void write_tree(node *t);

#endif /* VISUALTREE_H */
