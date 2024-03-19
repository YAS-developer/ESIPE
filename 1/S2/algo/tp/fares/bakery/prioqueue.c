#include <stdlib.h>
#include <assert.h>
#include <stdio.h>
#include "prioqueue.h"
#include "event.h"

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

    // Allouer la mémoire pour un nouveau lien
    link *new_link = (link*)malloc(sizeof(link));
    new_link->e = e;
    new_link->next = NULL;

    // Si la file est vide ou si l'événement doit être inséré en premier
    if (q->first == NULL || e->etime < q->first->e->etime) {
        new_link->next = q->first;
        q->first = new_link;
    } else {
        // Trouver la position d'insertion
        link *current = q->first;
        while (current->next != NULL && current->next->e->etime <= e->etime) {
            current = current->next;
        }

        // Insérer le nouvel événement
        new_link->next = current->next;
        current->next = new_link;
    }

    q->size++; // Augmenter la taille de la file
}

void display_pq(prioqueue *pq) {
    assert(pq != NULL); // Assurer que la file de priorité n'est pas NULL

    printf("Contenu de la file de priorité (ordre croissant) :\n");
    link *current = pq->first; // Commencer par le premier élément de la liste

    while (current != NULL) {
        printf("Événement à %d\n", current->e->etime);

        // Passer au prochain élément de la liste
        current = current->next;
    }
}

event *remove_min_pq(prioqueue *q) {
    assert(q != NULL && q->size > 0); // Assurer que la file n'est pas vide

    // Sauvegarder le pointeur vers la cellule en tête de liste
    link *head = q->first;
    if (head == NULL) {
        return NULL; // Renvoyer NULL si la liste est vide
    }
    event *min_event = head->e;
    q->first = head->next;
    free(head);

    q->size--;

    // Renvoyer l'événement extrait
    return min_event;
}