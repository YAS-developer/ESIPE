#include "tree.h"
#include "visualtree.h"  
#include <stdio.h>
#include <stdlib.h>

int main() {



    // Creating the tree nodes as per the given structure
    // node *root = create_node(3, NULL, NULL);
    // node *node5 = create_node(5, NULL, NULL);
    // node *node2 = create_node(2, NULL, NULL);
    // node *node12 = create_node(12, NULL, NULL);
    // node *node1 = create_node(1, NULL, NULL);
    // node *node7 = create_node(7, NULL, NULL);
    // node *node4 = create_node(4, NULL, NULL);

    // Setting up the structure of the tree
    // root->left = node5;
    // root->right = node2;

    // node5->left = node12;
    // node5->right = node1;

    // node1->left = node4;

    // node2->right = node7;

    // Assuming you want to use visualtree functions to create a visual representation
    // write_tree(root);




    


    // Test the traversal functions
    // printf("Pre-order traversal: ");
    // display_prefix(root);
    // printf("\n");

    // printf("In-order traversal: ");
    // display_infix(root);
    // printf("\n");

    // printf("Post-order traversal: ");
    // display_suffix(root);
    // printf("\n");



    // node *t = NULL;
    // write_tree(t);




    printf("Please enter the pre-order tree sequence (0 for NULL nodes):\n");
    node *root = scan_tree();


    // Use the tree functions
    int num_nodes = count_nodes(root);
    int num_leaves = count_leaves(root);
    int num_only_children = count_only_children(root);
    int tree_height = height(root);

    // Print the results
    printf("Number of nodes: %d\n", num_nodes);
    printf("Number of leaves: %d\n", num_leaves);
    printf("Number of only children: %d\n", num_only_children);
    printf("Height of tree: %d\n", tree_height);


    // Optionally, use the visualtree functions to visualize the constructed tree
    if (root != NULL) {
        write_tree(root);
    } else {
        printf("The tree is empty (NULL).\n");
    }





    // Freeing the tree to avoid memory leaks
    free_tree(root);


    // Freeing all nodes to prevent memory leaks
    // free(node4);
    // free(node1);
    // free(node12);
    // free(node7);
    // free(node2);
    // free(node5);
    // free(root);

    return 0;
}
