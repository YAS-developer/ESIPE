#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <time.h>
#include "event.h"
#include "customer.h"
#include "queue.h"
#include "prioqueue.h"

#define N_VENDORS 5
#define CLOSING_TIME 3500
#define ARRIVAL_RATE (1.0/18)
#define MEAN_SERVICE_TIME 76

prioqueue*  event_queue;
queue*      customer_queue;
customer*   vendor[N_VENDORS];
int     current_time;

int total_customers_served = 0;
int total_time_spent = 0;

int bakery_closed = 0;

double normal_delay(double mean) {
    return -mean*log(1-((double)rand()/RAND_MAX));
}

void add_customer(customer *c){
    int customerWithVendor = 0;
    for(int i = 0; i < N_VENDORS; i++){
        if(vendor[i] == NULL){
            customerWithVendor = 1;
            vendor[i] = c;
            event* ev = create_departure(current_time + normal_delay(MEAN_SERVICE_TIME), c);
            insert_pq(event_queue, ev);
            break;
        }
    }

    if(customerWithVendor == 0){
        enqueue_q(customer_queue, c);
    }
}

void remove_customer(customer *c){

    int k = 0;

    for(int i = 0; i < N_VENDORS; i++){
        if(vendor[i] == c){
            free_customer(vendor[i]);
            vendor[i] = NULL;
            k = i;
            break;
        }
    }

    if(size_q(customer_queue) > 0){
        customer *c2 = dequeue_q(customer_queue);
        vendor[k] = c2; 
        event* ev = create_departure(current_time + normal_delay(MEAN_SERVICE_TIME), c2);
        insert_pq(event_queue, ev);
    }
}

void process_arrival(event *e) {
    if(bakery_closed == 0){
        add_customer(e->c);
        customer* next_client = create_customer(current_time + normal_delay(1.0/ARRIVAL_RATE));
        event* next_event = create_arrival(current_time + normal_delay(1.0/ARRIVAL_RATE), next_client);
        insert_pq(event_queue, next_event);
    }
}

void process_departure(event *e) {
    total_customers_served++;
    total_time_spent += (current_time - e->c->atime);
    remove_customer(e->c);
}



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

    current_time = 0;
    event_queue = create_pq();
    customer_queue = create_q();


    for(int i = 0; i < N_VENDORS; i++){
        vendor[i] = NULL;
    }

    customer* c1 = create_customer(normal_delay(1.0/ARRIVAL_RATE));

    event* e_c1 = create_arrival(normal_delay(1.0/ARRIVAL_RATE), c1);
    insert_pq(event_queue, e_c1);

    while(size_pq(event_queue)>0){
        event* e = remove_min_pq(event_queue);
        current_time=e->etime;

        if (current_time >= CLOSING_TIME) {
            bakery_closed = 1;
        }

        if(e->type == EVENT_ARRIVAL && !bakery_closed){
            process_arrival(e);
            afficher();
        }else if(e->type == EVENT_DEPARTURE){
            process_departure(e);
            afficher();
        }
        else{
            free_customer(e->c);
        }
        free_event(e);
    }

    free_q(customer_queue);
    free_pq(event_queue);

    if (total_customers_served > 0) {
        double average_response_time = (double)total_time_spent / total_customers_served;
        printf("Nombre de clients servis : %d\n", total_customers_served);
        printf("Temps moyen de réponse : %.2f\n", average_response_time);
    } else {
        printf("Aucun client n'a été servi.\n");
    }

    return 0;
}
