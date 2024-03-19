#include <stdlib.h>
#include <assert.h>
#include <stdio.h>
#include "prioqueue.h"
#include "event.h"

#define MAX_QUEUE_SIZE 100

typedef struct _link {
    event* e;
    struct _link* next;
} link;

struct _prioqueue {
    link* first;
    int size;
};



prioqueue *create_pq(){
    prioqueue *q = (prioqueue*)malloc(sizeof(prioqueue));
    q->first = NULL;
    q->size = 0; 
    return q;
}


void free_pq(prioqueue *q){
    link *current = q->first;
    link *next;

    while (current != NULL) {
        next = current->next;
        free_event(current->e); 
        free(current); 
        current = next;
    }

    free(q); 
}

int size_pq(prioqueue *q){
    return q->size;
}

void insert_pq(prioqueue *q, event *e){
   assert(q != NULL);

    
    link *new_link = (link*)malloc(sizeof(link));
    new_link->e = e;
    new_link->next = NULL;

    if (q->first == NULL || e->etime < q->first->e->etime) {
        new_link->next = q->first;
        q->first = new_link;
    } else {
        link *current = q->first;
        while (current->next != NULL && current->next->e->etime <= e->etime) {
            current = current->next;
        }

       
        new_link->next = current->next;
        current->next = new_link;
    }

    q->size++; 
}

void display_pq(prioqueue *pq) {
    assert(pq != NULL); 

    printf("Contenu de la file de priorité (ordre croissant) :\n");
    link *current = pq->first; 

    while (current != NULL) {
        printf("Événement à %d\n", current->e->etime);
        current = current->next;
    }
}

event *remove_min_pq(prioqueue *q) {
    assert(q != NULL && q->size > 0); 

    link *head = q->first;
    if (head == NULL) {
        return NULL; 
    }

    event *min_event = head->e;

    q->first = head->next;

    free(head);

    q->size--;

    return min_event;
}