#ifndef VISUALTREE_H
#define VISUALTREE_H

#include "avl.h"
#include <stdio.h>

FILE* write_begin(char *name);
void write_end(FILE *f);
void write_node(FILE *f, node *n);
void write_left_link(FILE *f, node *n);
void write_right_link(FILE *f, node *n);
void write_tree_aux(FILE *f, node *t);
void write_tree(node *t);

#endif
