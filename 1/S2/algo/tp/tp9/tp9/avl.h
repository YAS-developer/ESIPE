#ifndef AVL_H
#define AVL_H

typedef struct _node {
    int data;                /* donnee stockee : un entier  */
    int height;              /* la hauteur de l'arbre       */
    struct _node *left;      /* pointeur sur le fils gauche */
    struct _node *right;     /* pointeur sur le fils droit  */
} node;

node *create_node(int elt, node *left, node *right);

void free_tree(node *t);

int is_avl(node *t);

node *find_avl(node *t, int elt);

node *insert_avl(node *t, int elt);

node *remove_avl(node *t, int elt);

/* Prototypes des nouvelles fonctions */
void update_height(node *t);
node *rotate_right(node *t);
node *rotate_left(node *t);
node *rotate_left_right(node *t);
node *rotate_right_left(node *t);
int compute_balance(node *t);
node *rebalance(node *t);

#endif /* AVL_H */
