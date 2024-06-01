#include "avl.h"
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <time.h>

/* Create a new node */
node *create_node(int elt, node *left, node *right) {
    node *n = (node *)malloc(sizeof(node));
    assert(n != NULL);
    n->data = elt;
    n->height = 0;  // Initialize height to 0 for new nodes (leaves)
    n->left = left;
    n->right = right;
    return n;
}

/* Free the tree */
void free_tree(node *t) {
    if (t != NULL) {
        free_tree(t->left);
        free_tree(t->right);
        free(t);
    }
}

/* Find an element in the AVL tree */
node *find_avl(node *t, int elt) {
    node *ptr = t;
    while (ptr != NULL && ptr->data != elt) {
        if (ptr->data > elt)
            ptr = ptr->left;
        else
            ptr = ptr->right;
    }
    return ptr;
}

/* Update height of a node */
void update_height(node *t) {
    if (t != NULL) {
        int left_height = (t->left) ? t->left->height : -1;
        int right_height = (t->right) ? t->right->height : -1;
        t->height = (left_height > right_height ? left_height : right_height) + 1;
    }
}

/* Compute balance factor */
int compute_balance(node *t) {
    if (t == NULL) return 0;
    int left_height = (t->left) ? t->left->height : -1;
    int right_height = (t->right) ? t->right->height : -1;
    return left_height - right_height;
}

/* Rotate right */
node *rotate_right(node *t) {
    node *new_root = t->left;
    t->left = new_root->right;
    new_root->right = t;
    update_height(t);
    update_height(new_root);
    return new_root;
}

/* Rotate left */
node *rotate_left(node *t) {
    node *new_root = t->right;
    t->right = new_root->left;
    new_root->left = t;
    update_height(t);
    update_height(new_root);
    return new_root;
}

/* Rotate left-right */
node *rotate_left_right(node *t) {
    t->left = rotate_left(t->left);
    return rotate_right(t);
}

/* Rotate right-left */
node *rotate_right_left(node *t) {
    t->right = rotate_right(t->right);
    return rotate_left(t);
}

/* Rebalance the tree */
node *rebalance(node *t) {
    update_height(t);
    int balance = compute_balance(t);

    if (balance > 1) {
        if (compute_balance(t->left) < 0) {
            t->left = rotate_left(t->left);
        }
        t = rotate_right(t);
    } else if (balance < -1) {
        if (compute_balance(t->right) > 0) {
            t->right = rotate_right(t->right);
        }
        t = rotate_left(t);
    }
    update_height(t); // Ensure the height is updated after rebalancing
    return t;
}

/* Insert an element into the AVL tree */
node *insert_avl(node *t, int elt) {
    if (t == NULL) {
        return create_node(elt, NULL, NULL);
    }

    if (elt < t->data) {
        t->left = insert_avl(t->left, elt);
    } else if (elt > t->data) {
        t->right = insert_avl(t->right, elt);
    } else {
        // Élément déjà présent, pas d'insertion nécessaire
        return t;
    }

    return rebalance(t);
}

/* Find the minimum node in the AVL tree */
node *find_min(node *t) {
    while (t->left != NULL) {
        t = t->left;
    }
    return t;
}

/* Remove the minimum node from the AVL tree */
node *remove_min(node *t) {
    if (t->left == NULL) {
        return t->right;
    }
    t->left = remove_avl(t->left, t->data);
    return rebalance(t);
}

/* Remove an element from the AVL tree */
node *remove_avl(node *t, int elt) {
    if (t == NULL) {
        return NULL;
    }

    if (elt < t->data) {
        t->left = remove_avl(t->left, elt);
    } else if (elt > t->data) {
        t->right = remove_avl(t->right, elt);
    } else {
        if (t->left == NULL) {
            node *right_child = t->right;
            free(t);
            return right_child;
        } else if (t->right == NULL) {
            node *left_child = t->left;
            free(t);
            return left_child;
        } else {
            node *min_right_subtree = find_min(t->right);
            t->data = min_right_subtree->data;
            t->right = remove_min(t->right);
        }
    }

    return rebalance(t);
}

/* Check if the tree is AVL */
int is_avl(node *t) {
    if (t == NULL) {
        return 1;
    }

    int left_height = (t->left) ? t->left->height : -1;
    int right_height = (t->right) ? t->right->height : -1;

    // Vérifier si la hauteur du noeud est correcte
    if (t->height != (left_height > right_height ? left_height : right_height) + 1) {
        return 0;
    }

    // Calculer le facteur d'équilibrage
    int balance = left_height - right_height;

    // Vérifier si le facteur d'équilibrage est -1, 0 ou 1
    if (balance < -1 || balance > 1) {
        return 0;
    }

    // Vérifier récursivement pour les sous-arbres gauche et droit
    return is_avl(t->left) && is_avl(t->right);
}

/* Fill an array with a random permutation of 1, 2, ..., n */
void fill_random_permutation(int *array, int n) {
    for (int i = 0; i < n; i++) {
        array[i] = i + 1;
    }
    for (int i = n - 1; i > 0; i--) {
        int j = rand() % (i + 1);
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
