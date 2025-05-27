# UC-04 – Recevoir une requête de mise à jour

**Numéro** : UC-02  
**Nom** : Mettre à jour un article  
**Acteur principal** : Administrateur Interne  
**Paquetage** : Gestion des stocks  
**Importance** : Élevée  
**Fréquence** : Variable (selon les commandes acceptées)  
**Objet du domaine** : Interface de gestion de stock

---

## Préconditions

- Un commercial a créé un devis.
- Le client a accepté le devis.
- Le client a signé le bon de commande.
- Une requête de mise à jour de stock est générée automatiquement par le système.
- L’administrateur est connecté à son compte.

---

## Postconditions

- Le stock est mis à jour (quantité décrémentée selon le bon de commande).
- L’historique des mouvements de stock est actualisé.
- Le statut de la requête est marqué comme **accepté** ou **refusé**.

---

## Scénario nominal

| Étape | Utilisateur (Administrateur)        | Système                                                              |
|-------|-------------------------------------|----------------------------------------------------------------------|
| 1     | Consulte la liste des requêtes de mise à jour | Affiche les requêtes en attente avec détails du bon de commande     |
| 2     | Sélectionne une requête             | Affiche les articles et quantités concernées                        |
| 3     | Accepte la requête                  | Vérifie la disponibilité du stock                                   |
| 4     | —                                   | Met à jour les quantités d’articles dans le stock                   |
| 5     | —                                   | Enregistre l’opération dans l’historique                            |
| 6     | —                                   | Met à jour le statut de la requête (acceptée)                       |
| 7     | —                                   | Notifie l’administrateur de la réussite de l’opération              |

---

## Scénario alternatif – Stock insuffisant

| Étape | Utilisateur (Administrateur)        | Système                                                              |
|-------|-------------------------------------|----------------------------------------------------------------------|
| 3a    | Accepte la requête                  | Vérifie la disponibilité du stock                                   |
| 4a    | —                                   | Si stock insuffisant, affiche un message d’erreur                   |
| 5a    | —                                   | Annule la mise à jour, marque la requête comme **refusée**          |
| 6a    | —                                   | Notifie l’administrateur du refus et invite à ajuster le stock      |

---

## FQM – Fonction / Qualité / Mesure

| Fonction               | Qualité    | Mesure                                      |
|------------------------|------------|---------------------------------------------|
| Validation des requêtes| Sécurité   | Action manuelle obligatoire avant exécution |
| Mise à jour de stock   | Fiabilité  | Quantités ajustées conformément au bon      |
|                        | Traçabilité| Historique conservé automatiquement         |
|                        | Performance| Traitement et notification en < 1 seconde   |
