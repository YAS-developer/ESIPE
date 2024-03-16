#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Function to display the strings.
void displayArgs(int argc, char** argv) {
    for (int i = 0; i < argc; i++) {
        printf("arg[%d] : %s\n", i, argv[i]);
    }
}

// Function to free allocated memory.
void freeArgs(int argc, char** argv) {
    for (int i = 0; i < argc; i++) {
        free(argv[i]);
    }
    free(argv);
}

int main(int argc, char *original_argv[]) {
    char **argv = malloc(argc * sizeof(char*));
    if (argv == NULL) {
        perror("malloc failed");
        return 1;
    }

    for (int i = 0; i < argc; i++) {
        argv[i] = malloc(strlen(original_argv[i]) + 1); 
        if (argv[i] == NULL) {
            perror("malloc failed");
            while (i-- > 0) {
                free(argv[i]);
            }
            free(argv);
            return 1;
        }
        strcpy(argv[i], original_argv[i]);
    }

    displayArgs(argc, argv);
    freeArgs(argc, argv);

    return 0;
}
