# UC-02 – Mettre à jour un article

**Numéro** : UC-02  
**Nom** : Mettre à jour un article  
**Acteur principal** : Administrateur Interne  
**Paquetage** : Gestion des stocks  
**Importance** : Élevée  
**Fréquence** : Variable (selon les commandes validées)  
**Objet du domaine** : Interface de gestion de stock

---

## Préconditions

- L’administrateur est authentifié.
- Une requête de mise à jour de stock est en attente (issue d'une commande client validée).

---

## Postconditions

- Le stock est mis à jour (quantité décrémentée selon le bon de commande).
- L’historique des mouvements de stock est actualisé.
- Le statut de la requête est marqué comme **acceptée** ou **refusée**.

---

## Scénario nominal

| Étape | Utilisateur (Administrateur Interne) | Système                                                              |
|-------|--------------------------------------|----------------------------------------------------------------------|
| 1     | Consulte la liste des requêtes de mise à jour | Affiche les requêtes avec les détails (articles, quantités, commande associée) |
| 2     | Sélectionne une requête              | Affiche les détails complets du bon de commande                     |
| 3     | Valide la requête                    | Vérifie la disponibilité des quantités en stock                     |
| 4     | —                                    | Met à jour les quantités d’articles dans le stock                   |
| 5     | —                                    | Enregistre l’opération dans l’historique de mouvements              |
| 6     | —                                    | Marque la requête comme **acceptée**                                |
| 7     | —                                    | Notifie les autres systèmes (suivi commande, facturation)           |

---

## Scénario alternatif – Requête refusée

| Étape | Utilisateur (Administrateur Interne) | Système                                       |
|-------|--------------------------------------|-----------------------------------------------|
| 3a    | Refuse la requête                    | Marque la requête comme **refusée**           |
| 4a    | —                                    | Aucune mise à jour du stock n’est effectuée   |
| 5a    | —                                    | Enregistre le refus dans l’historique         |

---

## FQM – Fonction / Qualité / Mesure

| Fonction               | Qualité    | Mesure                                      |
|------------------------|------------|---------------------------------------------|
| Validation de requête  | Sécurité   | L’opération n’est possible qu’avec authentification |
| Mise à jour de stock   | Fiabilité  | Quantités ajustées uniquement après validation |
| Historique             | Traçabilité| Mouvements de stock tracés automatiquement  |
| Réactivité             | Performance| Traitement en moins de 2 secondes           |
