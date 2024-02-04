#include "exercices.h"
#include "util.h"

/* Exercice 1 réponse 

*/


/* Exercice 2 */
int fibo_less(int k) {
    return -1;
}


/* Exercice 3 */
void copy(char str[], int src_lo, int src_hi, int dest_lo) {
    int taille=src_hi-src_lo+1, i, inc=0;
    char str2[taille];
    for(i=src_lo; i <= src_hi; i++){
        str2[i]=str[src_lo];
    }
    for(i=dest_lo; i <= taille; i++){
        str[dest_lo]=str2[inc];`
        inc++;
    }
}


/* Exercice 4 */
int equivalent(int t1[], int size1, int t2[], int size2) {
    return -1;
}


/* Exercice 5 */
int longest_arithmetic(int t[], int size) {
    return -1;
}


/* Exercice 6 */
int skolem(int n, int sol[], int pos) {
    return -1;
}
