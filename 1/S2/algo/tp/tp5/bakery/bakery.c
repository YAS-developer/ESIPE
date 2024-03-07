#include <stdio.h>
#include "event.h"
#include "customer.h"
#include "queue.h"
#include "prioqueue.h"
#include <stdlib.h>

#define N_VENDORS 3
#define CLOSING_TIME 

int current_time=0;
prioqueue*  event_queue;
queue*      customer_queue;
customer*   vendor[N_VENDORS];


void display(){
    printf("%d | ", current_time);
    for(int i=0; i<N_VENDORS; i++){
        if(vendor[i] == NULL){
            printf("_");
        }
        else{
            printf("X");
        }
    }
    printf(" | ");

    for(int i=0; i<size_q(customer_queue); i++){
        printf("X");
    } 
    printf("\n");
}

void add_customer(customer *c){
    for(int i=0; i<N_VENDORS; i++){
        if(vendor[i] == NULL){
            vendor[i] = c;
            break; 
        }
        else if(i == N_VENDORS-1 && vendor[i] != NULL){
            enqueue_q(customer_queue, c);
        }
    }
}

void process_arrival(event *e) {
    add_customer(e->c);
    
    customer *cus= create_customer(current_time+60);
    event *eve = create_arrival(current_time+60, cus);
    insert_pq(event_queue, eve);

    free(eve);
    free(cus);
}

void process_departure(event *e){

}


void check_pq(){
    while(size_pq(event_queue) > 0){
        event * e = remove_min_pq(event_queue);
        current_time = e->etime;
        // printf("Heure d'événement: %d\n", current_time);

        if(e->type == EVENT_ARRIVAL){
            process_arrival(e);
        }
        else if(e->type == EVENT_DEPARTURE){
            process_departure(e);
        }

        display();
        free(e);
    }
}

int main() {

    event_queue = create_pq();
    customer_queue = create_q();
    

    customer* c = create_customer(2);
    event* ev = create_arrival(100, c);
    insert_pq(event_queue,  ev);


    check_pq();

    free_pq(event_queue);
    free_q(customer_queue);
    for(int i=0; i<N_VENDORS; i++)
        free_customer(vendor[i]);

    free(c);
    free(ev);


    return 0;
}
