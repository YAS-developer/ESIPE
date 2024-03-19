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
        next = current->next; // Sauvegarder le pointeur suivant avant de libérer l'actuel
        // Ici, vous libérez le client et le maillon actuel
        free_customer(current->c); 
        free(current);
        current = next; // Passer au prochain maillon
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
        return NULL; // Retourne NULL si la file est vide ou si q est NULL.
    }

    link* head = q->first; // Sauvegarde de la tête de la file pour la libération.
    customer* c = head->c; // Sauvegarde du client à retourner.

    q->first = q->first->next; // Met à jour la tête de la file.
    if (q->first == NULL) {
        q->last = NULL; // Si la file est maintenant vide, ajuste aussi le dernier élément.
    }
    q->size--; // Décrémente la taille de la file.

    free(head); // Libère la cellule de la liste, mais pas le client.

    return c; // Retourne le client extrait.
    

}

void display_q(queue *q){

    if (q == NULL || q->size == 0) {
        printf("La file est vide.\n");
        return;
    }

    link *current = q->first;

    while (current != NULL) {
        // Afficher l'identifiant du client ici
        // Adaptez cette partie en fonction de la structure de `customer`
        printf("Client : %d\n", current->c->atime);  // Exemple fictif, remplacez `id` par la propriété appropriée
        current = current->next;
    }
}