#include <stdio.h>
#include <stdlib.h>
#include <time.h>

/* Allocate memory for an array which can contain `size`
   integers. The returned C array has memory for an extra last
   integer labelling the end of the array. */
int* allocate_integer_array(int size){
    int* new_tab;

    new_tab = (int*)malloc((size+1)*sizeof(int));
    if (new_tab == NULL){
        fprintf(stderr, "Memory allocation error\n");
        return NULL;
    }
    return new_tab;
}

/* Free an integer array */
void free_integer_array(int* tab){
    free(tab);
}

int array_size(int* array){
    int i = 0;

    while (array[i] != -1)
    {
        i++;
    }

    return i;
}

void print_array(int* array){
    int size = array_size(array);
    for(int i=0; i < size; i++){
        printf("%d\n", array[i]);
    }

}

int are_arrays_equal(int* first, int* second){
    int size_first = array_size(first);
    int size_second = array_size(second);

    if(size_first != size_second){
        return 0;
    }
    else{

        for(int i=0; i<size_first; i++){
            if(first[i] != second[i]){
                return 0;
            }
        }
        return 1;
    }
}


int* copy_array(int* array){
    int size = array_size(array);
    int* new_array = allocate_integer_array(size);
    new_array[size] = -1;
    for(int i=0; i<size; i++){
        new_array[i] = array[i];
    }

    return new_array;
}

// EX2

int* fill_array(void){
    int size = 0;
    printf("Quelle est la taille que vous souhaitez allouer au tableau d'entiers ?\n");
    scanf("%d", &size);
    
    int* tab = allocate_integer_array(size);
    tab[size]=-1;

    printf("\nVeuillez entrez les valeurs du tableaux");

    for(int i=0; i<size; i++){
       printf("\ntab[%d]: ", i); 
       scanf("%d", tab+i);
    }

    for(int i=0; i<size; i++){
       printf("\ntab[%d]=%d", i, tab[i]); 
    }
    printf("\n");
    return tab;
}


int* random_array(int size, int max_entry){
    int* new_array = allocate_integer_array(size);
    new_array[size]=-1;
    int rand_number=0;
    srand(time(NULL));
    for(int i=0; i<size; i++){
        rand_number=rand()%max_entry;
        new_array[i] = rand_number;
    }
    
    return new_array;
}

int* concat_array(int* first, int* second){
    int size_first = array_size(first);
    int size_second = array_size(second);

    // printf("%d\n", size_first);
    // printf("%d\n", size_second);
    int size = size_first + size_second;
    // printf("%d\n", size);
    int* new_array = allocate_integer_array(size);
    new_array[size]=-1;
    
    int count_second = 0;
    for(int i=0; i<size;i++){
        if(i < size_first){
            // printf("first[%d] : %d\n", i,first[i]);
            new_array[i] = first[i];
        }
        else if(i >= size_first){
            // printf("second[%d] : %d\n", count_second, second[count_second]);
            new_array[i] = second[count_second];
            count_second++;
        }
    }

    return new_array;
}


// int* merge_sorted_arrays(int* first, int* second){
    
//     int size_first = array_size(first);
//     int size_second = array_size(second);

//     for(int i = 0; i<size_first;i++){

//     }
// }


/* An empty main to test the compilation of the allocation and free
   functions. */
int main(int argc, char* argv[]){
    
    int size_tab = 4;

    int* tab = allocate_integer_array(size_tab);
    tab[2] = 2;
    tab[size_tab]=-1;

    int size_tab2 = 6;
    int* tab2 = allocate_integer_array(size_tab2);
    tab2[5] = 122;
    tab2[size_tab2]=-1;


    int* concat = concat_array(tab, tab2);
    print_array(concat);


    // print_array(tab);
    // printf("%d\n", are_arrays_equal(tab, tab2));
    // print_array(tab2);
    // printf("\n\n\n");
    int* copy_tab2 = copy_array(tab2);
    // print_array(copy_tab2);

    int* tab3 = random_array(5, 100);
    // print_array(tab3);
    int* tab4 = random_array(10, 100);
    // print_array(tab4);

   
    

    free_integer_array(tab);
    free_integer_array(tab2);
    free_integer_array(copy_tab2);
    free_integer_array(tab3);
    free_integer_array(tab4);

    return 0;
}
