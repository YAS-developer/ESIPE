#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/***
 * Exercice 1
 */
int palindrome_rec(char str[], int lo, int hi) {

    if(lo > hi){
        return 1;
    }
    else if(str[lo] == str[hi]){
        return palindrome_rec(str, lo+1, hi-1);
    }
    return 0;
}

int palindrome(char str[]) {
    return palindrome_rec(str, 0, strlen(str) - 1);
}

/***
 * Exercice 2
 */
void increasing_sequence_rec(int n) {
    if(n == 0){
        return;
    }
    increasing_sequence_rec(n-1);
    printf("%d ", n);
}

void decreasing_sequence_rec(int n) {
    if(n == 1){
        printf("%d ", n);
    }
    printf("%d ", n);
    decreasing_sequence_rec(n-1);
}

/***
 * Exercice 3
 */
int count(int t[], int lo, int hi, int elt) {
    if(lo > hi){
        return 0;
    }
    else if(t[lo] == elt){
        return 1 + count(t,lo+1,hi,elt);
    }
    return count(t,lo+1,hi,elt);
}

int max_count(int t[], int lo, int hi) {
    if(lo > hi){
        return 0;
    }
    else if(count(t,lo,hi,t[lo]) > max_count(t,lo+1,hi)){
        return count(t,lo,hi,t[lo]);
    }
    return max_count(t, lo+1, hi);
    
}

/***
 * Exercice 4
 */
int sum_digits_iter(int n) {
    int sum=0;
    for(int i=0; i<n; i++){
        sum += n % 10;
        n /= 10;
    }
    return sum;
}

int sum_digits_rec(int n) {
    if(n < 0){
        return n;
    }
    return n%10+sum_digits_iter(n/10);
}

/***
 * Exercice 5
 */
int digit_sum_digits_iter(int n) {
    return -1;
}

int digit_sum_digits_rec(int n) {
    return -1;
}

/***
 * Exercice 6
 */
int longest_incr_iter(int t[], int lo, int hi) {
    return -1;
}

int first_incr(int t[], int lo, int hi) {
    return -1;
}

int longest_incr_rec(int t[], int lo, int hi) {
    return -1;
}
