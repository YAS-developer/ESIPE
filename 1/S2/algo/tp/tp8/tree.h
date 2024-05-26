#ifndef TREE_H
#define TREE_H

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <math.h>



#define MAX_WORD_LENGTH 100

typedef struct node {
    char *word;
    struct node *left;
    struct node *right;
} node;

// Create a new node with a given word
node *create_node(char *word, node *left, node *right);

// Display functions
void display_prefix(node *t);
void display_infix(node *t);
void display_suffix(node *t);

// Tree manipulation functions
node *scan_tree();
int count_nodes(node *t);
int count_leaves(node *t);
int count_only_children(node *t);
int height(node *t);
node *find_bst(node *t, char *word);
node *insert_bst(node *t, char *word);
int is_bst(node *t);
void free_tree(node *t);
node *extract_min_bst(node *t, node **min);
node *remove_bst(node *t, char *word);

// Display words in a given range
void display_range(node *t, char *start, char *end);

// Compare height with the ideal height
void compare_height(node *t, int n);

// Read words from a file and insert them into a BST
node *read_file_to_bst(const char *filename);

// Find words in t1 that are not in t2
void find_unique_words(node *t1, node *t2);

#endif
