#include <stdio.h>
#include <stdlib.h>
#include "queue.h"

queue *create_q() {
    queue *q = (queue*)malloc(sizeof(queue));
    q->first = NULL;
    q->last = NULL;
    q->size = 0;
    return q;
}

void free_q(queue *q) {
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
        l->next = q->last;
        q->last=l;
    }
    
    q->size++;
    // printf("%d\n",  q->last->c->atime);
    free(l);
}

customer* dequeue(queue* q){
    // if(q->size == 0){
    //     return NULL;
    // }
    // else if(q->size == 1){
    //     customer *c  = q -> last;

    // }
    return NULL;
}



void display_q(queue *q){
    link *l=NULL;
//    l=q->first;
    if (q->first->c == NULL)
    {
    printf("TEST");
    }


/*
    for(l=q->first; l->next != NULL; l = l->next){
        printf("%d\n", l->c->atime);
    }
*/
}

// int main(int argc, char const *argv[])
// {
//     queue* q = create_q();
    // customer* client1 = create_customer(60);
    // customer* client2 = create_customer(60);
    // customer* client3 = create_customer(60);
    // customer* client4 = create_customer(60);
    // customer* client5 = create_customer(60);

    // enqueue_q(q,client1);
    // enqueue_q(q,client2);
    // enqueue_q(q,client3);
    // enqueue_q(q,client4);
    // enqueue_q(q,client5);
    
//     return 0;
// }