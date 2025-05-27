# UC-06 – Passer commande

**Numéro** : UC-07  
**Nom** : Passer commande  
**Acteur principal** : Client  
**Paquetage** : Gestion des commandes  
**Importance** : Très élevée  
**Fréquence** : Fréquente  
**Objet du domaine** : Interface de commande

---

## Préconditions

- Le client est authentifié.
- Un commercial a créé un devis pour le client.

---

## Postconditions

- La commande est enregistrée avec un statut "en attente de traitement".
- Une requête de mise à jour du stock est générée automatiquement pour l’administrateur interne.
- Une facture est créée et associée à la commande.
- Une notification est envoyée au client (confirmation de commande).
- Les points de fidélité du client sont mis à jour si le programme le prévoit.

---

## Scénario nominal

| Étape | Utilisateur (Client)               | Système                                                              |
|-------|------------------------------------|----------------------------------------------------------------------|
| 1     | Consulte et accepte le devis       | Génère un bon de commande basé sur le devis                         |
| 2     | Consulte le bon de commande        | Affiche le résumé des articles, conditions et montant               |
| 3     | Signe électroniquement le bon      | Enregistre la signature et valide le bon de commande                |
| 4     | —                                  | Crée la commande avec un identifiant unique                         |
| 5     | —                                  | Génère une facture associée à la commande                           |
| 6     | —                                  | Crée une requête de mise à jour du stock                            |
| 7     | —                                  | Met à jour les points de fidélité (si applicable)                   |
| 8     | —                                  | Envoie une notification de confirmation au client                   |

---

## Scénario alternatif – Refus ou absence de signature

| Étape | Utilisateur (Client)      | Système                                                |
|-------|---------------------------|--------------------------------------------------------|
| 3a    | Refuse ou ne signe pas    | Annule la procédure, le bon de commande reste en attente |
| 4a    | —                         | Aucune commande n’est créée, aucune requête émise      |

---

## FQM – Fonction / Qualité / Mesure

| Fonction               | Qualité       | Mesure                                                  |
|------------------------|---------------|---------------------------------------------------------|
| Création commande      | Fiabilité     | Commande enregistrée avec intégrité de données          |
| Génération facture     | Performance   | Créée en moins de 1 seconde                             |
| Requête de stock       | Traçabilité   | Créée automatiquement et liée à la commande             |
| Notification           | Disponibilité | Client notifié dans les 30 secondes                     |
| Fidélité               | Cohérence     | Points mis à jour dès commande validée                  |
