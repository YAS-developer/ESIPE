#include "tree.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <limits.h>
#include <math.h>




// Helper function to compare strings
int equal(char *s1, char *s2) {
    return strcmp(s1, s2) == 0;
}

int less(char *s1, char *s2) {
    return strcmp(s1, s2) < 0;
}

// Create a new node with a given word
node *create_node(char *word, node *left, node *right) {
    node *n = (node *)malloc(sizeof(node));
    n->word = (char *)malloc(sizeof(char));
    strcpy(n->word, word);
    n->left = left;
    n->right = right;
    return n;
}

// Display the nodes of the tree t following an in-order traversal (lexicographical order)
void display_infix(node *t) {
    if (t != NULL) {
        display_infix(t->left); // Traverse left subtree
        printf("%s ", t->word);  // Visit the root
        display_infix(t->right); // Traverse right subtree
    }
}

// Function to insert a word into the BST
node *insert_bst(node *t, char *word) {
    if (t == NULL) {
        return create_node(word, NULL, NULL);
    }

    if (less(word, t->word)) {
        t->left = insert_bst(t->left, word);
    } else if (!equal(word, t->word)) {
        t->right = insert_bst(t->right, word);
    }
    return t;
}

// Function to find a node with a given word in the BST
node *find_bst(node *t, char *word) {
    if (t == NULL) {
        return NULL; // Element not found
    }
    if (equal(word, t->word)) {
        return t; // Element found
    } else if (less(word, t->word)) {
        return find_bst(t->left, word); // Search in the left subtree
    } else {
        return find_bst(t->right, word); // Search in the right subtree
    }
}

// Function to count the nodes in the tree
int count_nodes(node *t) {
    if (t == NULL) // Base case: empty tree has zero nodes
        return 0;
    return 1 + count_nodes(t->left) + count_nodes(t->right);
}

// Function to calculate the height of the tree
int height(node *t) {
    if (t == NULL) // Base case: the height of an empty tree is -1
        return -1;
    int left_height = height(t->left);
    int right_height = height(t->right);
    return 1 + (left_height > right_height ? left_height : right_height); // Return the greater height
}

// Helper function to find the minimum node in a BST
node *find_min(node *t) {
    while (t->left != NULL) {
        t = t->left;
    }
    return t;
}

// Function to remove a node with a given word from the BST
node *remove_bst(node *t, char *word) {
    if (t == NULL) {
        return NULL; // If the tree is empty, return NULL
    }

    if (less(word, t->word)) {
        // The word to be deleted is in the left subtree
        t->left = remove_bst(t->left, word);
    } else if (!less(word, t->word) && !equal(word, t->word)) {
        // The word to be deleted is in the right subtree
        t->right = remove_bst(t->right, word);
    } else {
        // Found the node to be deleted
        if (t->left == NULL && t->right == NULL) {
            // Case 1: No children (leaf node)
            free(t->word);
            free(t);
            return NULL;
        } else if (t->left == NULL) {
            // Case 2: One child (right)
            node *temp = t->right;
            free(t->word);
            free(t);
            return temp;
        } else if (t->right == NULL) {
            // Case 2: One child (left)
            node *temp = t->left;
            free(t->word);
            free(t);
            return temp;
        } else {
            // Case 3: Two children
            node *min_right_subtree = find_min(t->right);
            free(t->word);
            strcpy(t->word, min_right_subtree->word);
            t->right = remove_bst(t->right, min_right_subtree->word);
        }
    }
    return t; // Return the (possibly new) root pointer
}

// Function to free the memory allocated for the tree
void free_tree(node *t) {
    if (t == NULL) {
        return;
    }
    free_tree(t->left); // Free the left subtree
    free_tree(t->right); // Free the right subtree
    free(t->word); // Free the word
    free(t); // Free the current node
}

// Function to extract the minimum node from the BST
node *extract_min_bst(node *t, node **min) {
    if (t == NULL) {
        *min = NULL;
        return NULL;
    }
    
    node *parent = NULL;
    node *current = t;
    
    // Find the minimum node
    while (current->left != NULL) {
        parent = current;
        current = current->left;
    }
    
    // The minimum node is found
    *min = current;
    
    // If the parent of the minimum node is NULL, it means the minimum node is the root
    if (parent == NULL) {
        // The new root is the right subtree of the minimum node
        return t->right;
    } else {
        // The parent of the minimum node should point to the right subtree of the minimum node
        parent->left = current->right;
        return t;
    }
}

// Function to display the nodes in a given range
void display_range(node *t, char *start, char *end) {
    if (t == NULL) {
        return;
    }
    if (less(start, t->word)) {
        display_range(t->left, start, end);
    }
    if (!less(t->word, start) && !less(end, t->word)) {
        printf("%s ", t->word);
    }
    if (less(t->word, end)) {
        display_range(t->right, start, end);
    }
}

// Function to compare the height of the tree with the ideal height
// void compare_height(node *t, int n) {
//     int h = height(t);
//     int ideal_height = (int)(log(n) / M_LN2);
//     printf("Height of the tree: %d\n", h);
//     printf("Ideal height: %d\n", ideal_height);
// }


// void compare_height(node *t, int n) {
//     int h = height(t);
//     int ideal_height = (int)log2(n);
//     printf("Height of the tree: %d\n", h);
//     printf("Ideal height: %d\n", ideal_height);
// }

// Function to check if the tree is a binary search tree
int is_bst_helper(node *t, char *min, char *max) {
    if (t == NULL) {
        return 1;  // Empty tree is BST
    }
    if ((min != NULL && strcmp(t->word, min) <= 0) || (max != NULL && strcmp(t->word, max) >= 0)) {
        return 0;  // Not BST
    }
    return is_bst_helper(t->left, min, t->word) && is_bst_helper(t->right, t->word, max);
}

int is_bst(node *t) {
    return is_bst_helper(t, NULL, NULL);
}

// Function to read words from a file and insert them into a BST
node *read_file_to_bst(const char *filename) {
    FILE *f = fopen(filename, "r");
    if (f == NULL) {
        fprintf(stderr, "Could not open file %s.\n", filename);
        return NULL;
    }
    node *root = NULL;
    char word[MAX_WORD_LENGTH + 1];
    while (fscanf(f, "%s", word) != EOF) {
        root = insert_bst(root, word);
    }
    fclose(f);
    return root;
}

// Function to find words in t1 that are not in t2
void find_unique_words(node *t1, node *t2) {
    if (t1 == NULL) {
        return;
    }
    if (find_bst(t2, t1->word) == NULL) {
        printf("%s ", t1->word);
    }
    find_unique_words(t1->left, t2);
    find_unique_words(t1->right, t2);
}
