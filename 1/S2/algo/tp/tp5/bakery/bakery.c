#include <stdio.h>
#include "event.h"
#include "customer.h"
#include "queue.h"
#include "prioqueue.h"

#define N_VENDORS 3

prioqueue*  event_queue;
queue*      customer_queue;
customer*   vendor[N_VENDORS];

void process_arrival(event *e) {
}

void process_departure(event *e) {
}

int main() {

    event_queue = create_pq();
    customer_queue = create_q();
    for(int = 0; i<N_VENDORS; i++){
        vendor[i]= create_customer(0);
    }




    free_pq(event_queue);
    free_q(customer_queue);
    for(int i=0; i<N_VENDORS; i++)
        free_customer(N_VENDORS[i]);
    free(vendor);

    return 0;
}
