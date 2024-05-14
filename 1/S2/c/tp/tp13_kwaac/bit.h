/*! \file bit.h
 *  \brief File containing function to manipulate a bit array
 */

#ifndef TP12_BIT_H
#define TP12_BIT_H

/**
 * Print every bits of the bit array
 *
 * @param n the bit array
 */
void print_ULI(unsigned long int n);

/**
 * Return the value of the given position in the bit array
 *
 * @param n The bit array
 * @param position Position of the bit
 * @return 1 if the bit is at 1, 0 if the bit is at 0
 * */
int bit_value_ULI(unsigned long int n, int position);


/**
 * Set the bit at the given position in the bitarray to 1
 *
 * @param n the bit array
 * @param position the position
 */
void set_positive_bit_ULI(unsigned long int *n, int position);

/**
 * Set the bit at the given position in the bitarray to 0
 *
 * @param n the bit array
 * @param position the position
 */
void set_negative_bit_ULI(unsigned long int *n, int position);

#endif /*TP12_BIT_H*/
