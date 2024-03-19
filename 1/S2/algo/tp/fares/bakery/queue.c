#include <stdio.h>
#include <stdlib.h>
#include "queue.h"
#include "customer.h"

typedef struct _link {
    customer* c;
    struct _link* next;
} link;


struct _queue {
    link* first;
    link* last;
    int size;
};


queue* create_q() {
    queue *q = (queue*)malloc(sizeof(queue));
    q->first = NULL;
    q->last = NULL;
    q->size = 0;
    return q;
}

void free_q(queue *q) {
    link *current = q->first;
    link *next;

    while (current != NULL) {
        next = current->next; 
        free_customer(current->c); 
        free(current);
        current = next; 
    }

    free(q);
}

int size_q(queue *q) {
    return q->size;
}

void enqueue_q(queue *q, customer *c){
    link* l = (link*)malloc(sizeof(link));
    l->c = c;
    l->next = NULL;

    if(q->first == NULL)
    {
        q->first = l;
        q->last = l;
    }else{
        q->last->next = l;
        q->last = l;
    }
    
    q->size++;
}

customer* dequeue_q(queue *q){

    if (q == NULL || q->first == NULL) {
        printf("La file est vide ou non existante.\n");
        return NULL; 
    }

    link* head = q->first; 
    customer* c = head->c;

    q->first = q->first->next; 
    if (q->first == NULL) {
        q->last = NULL; 
    }
    q->size--; 

    free(head); 

    return c; 
    

}

void display_q(queue *q){

    if (q == NULL || q->size == 0) {
        printf("La file est vide.\n");
        return;
    }

    link *current = q->first;

    while (current != NULL) {
        printf("Client : %d\n", current->c->atime);  
        current = current->next;
    }
}