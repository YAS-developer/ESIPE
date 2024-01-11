#include "verif_func.h"
#include "funcs.h"
#include "parse.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

static int tab[MAX_SEQ_LENGTH];
// static int tab_exp[MAX_SEQ_LENGTH];
static char str[MAX_SEQ_LENGTH];

/*
 * Return -1 if the arrays are equal.
 * Otherwise, return the first index for they differ.
 */
int index_diff(int *t1, int size1, int *t2, int size2) {
    if (size1 != size2) return 0;
    int i;
    for (i = 0; i < size1; i++)
        if (t1[i] != t2[i])
            return i;
    return -1;
}

void display_array(int *t, int size) {
    int i = 0;
    printf("[");
    for (i = 0; i < size; i++) {
        printf("%d",t[i]);
        if (i < size-1) printf(", ");
    }
    printf("]\n");
}

void display_value_error(char *str, int exp, int rec) {
    printf("\033[1m\033[91mERROR\033[0m: %s\n", str);
    printf("Expected: %d\n", exp);
    printf("Received: %d\n", rec);
}

/*
void display_array_error(char *str, int *tab_exp, int size_exp, int *tab_rec, int size_rec) {
    printf("\033[1m\033[91mERROR\033[0m: %s\n", str);
    printf("Expected: "); display_array(tab_exp, size_exp);
    printf("Received: "); display_array(tab_rec, size_rec);
}
*/

/*
void display_multi_error(char *str, int *tab, int size, int elt, int exp, int rec) {
    printf("\033[1m\033[91mERROR\033[0m: %s\n", str);
    printf("Find (%d) in array: ", elt); display_array(tab, size);
    printf("Expected: %d\n", exp);
    printf("Received: %d\n", rec);
}
*/

int verify_palindrome_rec() {

    int lo, hi, res, exp;
    
    /* read test */
    read_string(str);
    read_int(&lo);
    read_int(&hi);
    
    read_int(&exp);

    /* run test */
    res = palindrome_rec(str, lo, hi);

    int ok;

    ok = res == exp;

    if (!ok) {
        display_value_error("the return value differs", exp, res);
    }

    return ok;
}

int verify_palindrome() {

    int res, exp;
    
    /* read test */
    read_string(str);

    read_int(&exp);

    /* run test */
    res = palindrome(str);

    int ok;

    ok = res == exp;

    if (!ok) {
        display_value_error("the return value differs", exp, res);
    }

    return ok;
}

int verify_one_int_arg(int (*fn)(int)) {

    int n, res, exp;
    
    /* read test */
    read_int(&n);
    
    read_int(&exp);

    /* run test */
    res = (*fn)(n);

    int ok;

    ok = res == exp;

    if (!ok) {
        display_value_error("the return value differs", exp, res);
    }

    return ok;

}

int verify_tab_lo_hi(int (*fn)(int*, int, int)) {

    int lo, hi, res, exp;
    
    /* read test */
    read_int_array(tab, MAX_SEQ_LENGTH);
    read_int(&lo);
    read_int(&hi);
    
    read_int(&exp);

    /* run test */
    res = (*fn)(tab, lo, hi);

    int ok;

    ok = res == exp;

    if (!ok) {
        display_value_error("the return value differs", exp, res);
    }

    return ok;
}

int verify_tab_lo_hi_elt(int (*fn)(int*, int, int, int)) {

    int lo, hi, elt, res, exp;
    
    /* read test */
    read_int_array(tab, MAX_SEQ_LENGTH);
    read_int(&lo);
    read_int(&hi);
    read_int(&elt);
    
    read_int(&exp);

    /* run test */
    res = (*fn)(tab, lo, hi, elt);

    int ok;

    ok = res == exp;

    if (!ok) {
        display_value_error("the return value differs", exp, res);
    }

    return ok;
}

int run_test(char test_id[], char test_function[]) {

    int res = -1;

    if (strcmp(test_function, "verify_palindrome_rec") == 0)
    {
        res = verify_palindrome_rec();
    } else if (strcmp(test_function, "verify_palindrome") == 0)
    {
        res = verify_palindrome();
    } else if (strcmp(test_function, "verify_count") == 0)
    {
        res = verify_tab_lo_hi_elt(&count);
    } else if (strcmp(test_function, "verify_max_count") == 0)
    {
        res = verify_tab_lo_hi(&max_count);
    } else if (strcmp(test_function, "verify_sum_digits_iter") == 0)
    {
        res = verify_one_int_arg(&sum_digits_iter);
    } else if (strcmp(test_function, "verify_sum_digits_rec") == 0)
    {
        res = verify_one_int_arg(&sum_digits_rec);
    } else if (strcmp(test_function, "verify_digit_sum_digits_iter") == 0)
    {
        res = verify_one_int_arg(&digit_sum_digits_iter);
    } else if (strcmp(test_function, "verify_digit_sum_digits_rec") == 0)
    {
        res = verify_one_int_arg(&digit_sum_digits_rec);
    } else if (strcmp(test_function, "verify_longest_incr_iter") == 0)
    {
        res = verify_tab_lo_hi(&longest_incr_iter);
    } else if (strcmp(test_function, "verify_first_incr") == 0)
    {
        res = verify_tab_lo_hi(&first_incr);
    } else if (strcmp(test_function, "verify_longest_incr_rec") == 0)
    {
        res = verify_tab_lo_hi(&longest_incr_rec);
    } else {
        fprintf(stderr, "Error in test case %s: test function %s does not exist\n", test_id, test_function);
        exit(1);
    }

    return res;
}
