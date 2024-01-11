#ifndef __FUNCS_H__
#define __FUNCS_H__

/*** Algo 1, TP2 ***/

/***
 * Exercice 1
 */
int palindrome_rec(char str[], int lo, int hi);

int palindrome(char str[]);

/***
 * Exercice 2
 */
void increasing_sequence_rec(int n);

void decreasing_sequence_rec(int n);

/***
 * Exercice 3
 */
int count(int t[], int lo, int hi, int elt);

int max_count(int t[], int lo, int hi);

/***
 * Exercice 4
 */
int sum_digits_iter(int n);

int sum_digits_rec(int n);

/***
 * Exercice 5
 */
int digit_sum_digits_iter(int n);

int digit_sum_digits_rec(int n);

/***
 * Exercice 6
 */
int longest_incr_iter(int t[], int lo, int hi);

int first_incr(int t[], int lo, int hi);

int longest_incr_rec(int t[], int lo, int hi);

#endif /* __FUNCS_H__ */
