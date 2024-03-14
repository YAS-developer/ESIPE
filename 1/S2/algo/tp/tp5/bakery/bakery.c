#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <time.h>
#include "event.h"
#include "customer.h"
#include "queue.h"
#include "prioqueue.h"

#define N_VENDORS 3
#define CLOSING_TIME 360
#define ARRIVAL_RATE (1.0/60)
#define MEAN_SERVICE_TIME 150

prioqueue*  event_queue;
queue*      customer_queue;
customer*   vendor[N_VENDORS];
int     current_time;

double normal_delay(double mean) {
    return -mean*log(1-((double)rand()/RAND_MAX));
}

void add_customer(customer *c){
    int ok = 0;
    for(int i = 0; i < N_VENDORS; i++){
        if(vendor[i] == NULL){
            ok = 1;
            vendor[i] = c;
            event* ev = create_departure(current_time + normal_delay(MEAN_SERVICE_TIME), c);
            insert_pq(event_queue, ev);
            break;
        }
    }

    if(ok == 0){
        enqueue_q(customer_queue, c);
    }
}

// void remove_customer(customer *c){

//     int k = 0;

//     /* Libérer le vendeur */
//     for(int i = 0; i < N_VENDORS; i++){
//         if(vendor[i] == c){
//             free_customer(vendor[i]);
//             vendor[i] = NULL;
//             k = i;
//             break;
//         }
//     }

//     if(size_q(customer_queue) > 0){
//         customer *c2 = dequeue_q(customer_queue);
//         vendor[k] = c2; 
//         event* ev = create_departure(current_time + normal_delay(MEAN_SERVICE_TIME), c2);
//         insert_pq(event_queue, ev);
//     }
    

// }

void process_arrival(event *e) {
    add_customer(e->c);
    customer* client = create_customer(current_time + normal_delay(1.0/ARRIVAL_RATE));
    event* ev = create_arrival(current_time + normal_delay(1.0/ARRIVAL_RATE), client);
    insert_pq(event_queue, ev);
}

// void process_departure(event *e) {
//     remove_customer(e->c);
// }



void afficher(){
    printf("%d", current_time);
    printf(" | ");
    for(int i = 0; i < N_VENDORS; i++){
        if(vendor[i] != NULL){
            printf("X");
        }
        else{
            printf("_");
        }
    }
    printf(" | ");
    for(int i = 0; i < size_q(customer_queue); i++){
        printf("X");
    }
    printf("\n");
}

int main() {

    srand(time(NULL));

    // current_time = 0;
    // event_queue = create_pq();
    // customer_queue = create_q();


    // for(int i = 0; i < N_VENDORS; i++){
    //     vendor[i] = NULL;
    // }

    // customer* client1 = create_customer(normal_delay(1.0/ARRIVAL_RATE));

    // event* e1 = create_arrival(normal_delay(1.0/ARRIVAL_RATE), client1);
    // insert_pq(event_queue, e1);

    
    // while (size_pq(event_queue) > 0 && current_time < CLOSING_TIME)
    // {
    //     event* e = remove_min_pq(event_queue);   
    //     current_time = e->etime;
    //     if(e->type == EVENT_ARRIVAL){
    //         process_arrival(e);
    //     }
    //     else if(e->type == EVENT_DEPARTURE){
    //         process_departure(e);
    //     }
    //     afficher();
    //     free_event(e);
    // }

    // free_pq(event_queue);
    // for(int i = 0; i < N_VENDORS; i++){
    //     if(vendor[i] != NULL){
    //         free_customer(vendor[i]);
    //     }
        
    // }

    // free_q(customer_queue);

    queue *q = create_q();

    customer* client1 = create_customer(13);

    enqueue_q(q, client1);
    // enqueue_q(q, client1);
    // enqueue_q(q, client1);
    // enqueue_q(q, client1);

    printf("%d\n",  q->last->c->atime);

    // display_q(q);

    free(q);

    return 0;
}
