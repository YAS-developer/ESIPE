#include "tree.h"
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

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


node *scan_tree() {
    int data;
    if (scanf("%d", &data) != 1 || data == 0) {
        return NULL;  // Base case: if the input is 0 or fails, return NULL.
    }
    node *left = scan_tree();  // Recursively build the left subtree.
    node *right = scan_tree();  // Recursively build the right subtree.
    return create_node(data, left, right);  // Construct the node.
}


int count_nodes(node *t) {
    if (t == NULL)  // Base case: empty tree has zero nodes
        return 0;
    return 1 + count_nodes(t->left) + count_nodes(t->right);
}

int count_leaves(node *t) {
    if (t == NULL)  // Base case: empty tree has no leaves
        return 0;
    if (t->left == NULL && t->right == NULL)  // Leaf node check
        return 1;
    return count_leaves(t->left) + count_leaves(t->right);
}


int count_only_children(node *t) {
    if (t == NULL)  // Base case: empty tree has no single-child nodes
        return 0;
    int count = 0;
    // Check if the node has exactly one child
    if ((t->left != NULL && t->right == NULL) || (t->left == NULL && t->right != NULL))
        count = 1;
    return count + count_only_children(t->left) + count_only_children(t->right);
}


int height(node *t) {
    if (t == NULL)  // Base case: the height of an empty tree is -1
        return -1;
    int left_height = height(t->left);
    int right_height = height(t->right);
    return 1 + (left_height > right_height ? left_height : right_height);  // Return the greater height
}

node *find_bst(node *t, int elt) {
    if (t == NULL) {
        return NULL; // Element not found
    }
    if (elt == t->data) {
        return t; // Element found
    } else if (elt < t->data) {
        return find_bst(t->left, elt); // Search in the left subtree
    } else {
        return find_bst(t->right, elt); // Search in the right subtree
    }


    //  while (t != NULL) {
    //     if (elt == t->data) {
    //         return t; // Element found
    //     } else if (elt < t->data) {
    //         t = t->left; // Move to the left subtree
    //     } else {
    //         t = t->right; // Move to the right subtree
    //     }
    // }
    // return NULL; // Element not found
}


node *insert_bst(node *t, int elt) {
    if (t == NULL) {
        // If the tree is empty, create a new node and return it
        return create_node(elt, NULL, NULL);
    }

    if (elt < t->data) {
        // Insert in the left subtree
        t->left = insert_bst(t->left, elt);
    } else if (elt > t->data) {
        // Insert in the right subtree
        t->right = insert_bst(t->right, elt);
    }
    // If elt is equal to t->data, do nothing (no duplicates in BST)
    return t;
}



int is_bst_helper(node *t, int min, int max) {
    if (t == NULL) {
        return 1;  // void tree is BST
    }
    if (t->data <= min || t->data >= max) {
        return 0;  // not BST
    }
    return is_bst_helper(t->left, min, t->data) && is_bst_helper(t->right, t->data, max);
}

int is_bst(node *t) {
    return is_bst_helper(t, INT_MIN, INT_MAX);
}

void fill_random_permutation(int *array, int size) {
    for (int i = 0; i < size; i++) {
        array[i] = i + 1;
    }
    for (int i = size - 1; i > 0; i--) {
        int j = rand() % (i + 1);
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

node* insert_random_elements(int N) {
    int *array = (int *)malloc(N * sizeof(int));
    fill_random_permutation(array, N);
    node *root = NULL;
    for (int i = 0; i < N; i++) {
        root = insert_bst(root, array[i]);
    }
    free(array);
    return root;
}

node* insert_sequential_elements(int N) {
    node *root = NULL;
    for (int i = 1; i <= N; i++) {
        root = insert_bst(root, i);
    }
    return root;
}



void free_tree(node *t) {
    if (t == NULL) {
        return;
    }
    free_tree(t->left);  // Free the left subtree
    free_tree(t->right);  // Free the right subtree
    free(t);  // Free the current node
}

