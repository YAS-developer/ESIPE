#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>
#include <time.h>
#include <stdlib.h>
#include <time.h>
#include "arrays.h"
#include "verif_func.h"
#include "parse.h"


FILE *fh; /* file handle */

/*
 * Display the number of milliseconds that has elapsed since start.
 */
clock_t timer(clock_t start) {
    clock_t diff = clock()-start;
    int msec = diff*1000/CLOCKS_PER_SEC;
    printf("    time: %d ms\n", msec);
    return diff;
}

void usage() {
    printf("Usage: verif [options] [testfile1] [testfile2] ...\n");
    printf("  -h   Print this message and exit.\n");
    /* printf("  -t   Time each test.\n"); */
    printf("  -v   Verbose.\n");
}

int main(int argc, char* argv[]) {
    char exo=0;
    
    
    printf("Veuillez choisir votre exercice:\n\t"
    "exercice 1: taper '1'\n\t"
    "exercice 2: taper '2'\n");
    while(1){
        scanf("%c", &exo);
        if(exo == '1'){
            int c;
            int vflag = 0; /* , tflag = 0; */
            if (argc == 1) {
                usage();
                return 0;
            }
            while ((c = getopt(argc, argv, "hv")) != -1)
            switch (c) {
                case 'h':
                    usage();
                    return 0;
                case 'v':
                    /* tflag = 1; */
                    vflag = 1;
                    break;
                case '?':
                    usage();
                    return 1;
                default:
                    abort();
            }

            int tried = 0, passed = 0;

            int i;
            for (i = optind; i < argc; i++) {

                fh = fopen(argv[i], "r");
                if (fh == NULL) {
                    fprintf(stderr, "Error opening test file: \"%s\"\n", argv[i]);
                    continue;
                }

                if (vflag)
                    printf("Opening test file: \"%s\"\n", argv[i]);

                char test_id[MAX_STRING_LENGTH];
                char test_function[MAX_STRING_LENGTH];

                while (read_test_header(test_id, test_function)) {

                    if (vflag)
                        printf("*** Running test \033[1m%s\033[0m ...\n", test_id);

                    int res = -1;

                    /*
                    clock_t msec;
                    if (tflag) msec = clock();
                    */

                    /* run test case */
                    res = run_test(test_id, test_function);
                    tried++;

                    if (res == 1) {
                        if (vflag)
                            printf("Test \033[1m%s \033[92mPASSED\033[0m\n", test_id);
                        passed++;
                    } else {
                        printf("Test \033[1m%s \033[91mFAILED\033[0m\n", test_id);
                    }

                    /*
                    if (tflag) timer(msec);
                    */
                }

                fclose(fh);
                
            }

            printf("%d of %d tests passed\n", passed, tried);

            break;
        }
        else if(exo == '2'){
            clock_t start, end;
            double cpu_time_used;

            int max_size = 250000000; // Taille maximale du tableau
            int elt = 42; // Elément à insérer ou chercher dans le tableau

            // Mesures pour la fonction create_array
            start = clock();
            int *arr = create_array(max_size);
            end = clock();
            cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
            printf("Time taken to create_array: %f\n", cpu_time_used);

            // Mesures pour la fonction insert_unsorted
            start = clock();
            int size = 0;
            for (int i = 0; i < max_size; i++) {
                insert_unsorted(arr, &size, elt);
            }
            end = clock();
            cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
            printf("Time taken to insert_unsorted: %f\n", cpu_time_used);

            // Mesures pour la fonction find_unsorted
            // start = clock();
            // for (int i = 0; i < max_size; i++) {
            //     find_unsorted(arr, size, elt);
            // }
            // end = clock();
            // cpu_time_used = ((double)(end - start)) / CLOCKS_PER_SEC;
            // printf("Time taken to find_unsorted: %f\n", cpu_time_used);

            // Libérer la mémoire allouée pour le tableau
            free_array(arr);

            return 0;
        }
        else{
            printf("Veuillez mettre un nom d'exercice valide:\n\t"
            "exercice 1: taper '1'\n\t"
            "exercice 2: tqper '2'\n");
        }
    }

    return 0;
}
