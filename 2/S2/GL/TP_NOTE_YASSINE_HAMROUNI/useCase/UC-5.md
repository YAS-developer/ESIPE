# UC-05 – Consulter le catalogue

**Numéro** : UC-05  
**Nom** : Consulter le catalogue  
**Acteur principal** : Client, Commercial  
**Paquetage** : Navigation Produits  
**Importance** : Élevée  
**Fréquence** : Quotidienne  
**Objet du domaine** : Interface de navigation du catalogue

---

## Préconditions

- L’utilisateur (client ou commercial) est authentifié.
- Le catalogue est disponible et à jour dans la base de données.

---

## Postconditions

- L’utilisateur visualise les articles disponibles dans le catalogue.
- Les informations affichées sont filtrées selon le rôle (par exemple : les commerciaux n’ont pas accès aux prix).

---

## Scénario nominal

| Étape | Utilisateur (Client ou Commercial)  | Système                                                            |
|-------|-------------------------------------|--------------------------------------------------------------------|
| 1     | Accède à la section "Catalogue"     | Affiche les catégories de produits                                |
| 2     | Sélectionne une catégorie ou utilise un moteur de recherche | Filtre les articles et affiche les résultats                      |
| 3     | Parcourt la liste d’articles        | Affiche les fiches produits avec nom, description, etc.           |
| 4     | Sélectionne un article              | Affiche la fiche détaillée du produit                             |
| 5     | —                                   | Adapte les informations selon le rôle : prix affiché uniquement au client |

---

## Scénario alternatif – Aucun produit trouvé

| Étape | Utilisateur                          | Système                                         |
|-------|---------------------------------------|------------------------------------------------|
| 2a    | Saisit un mot-clé trop restrictif     | Affiche un message : "Aucun produit trouvé"    |
| 3a    | —                                     | Invite à élargir la recherche ou à changer de filtre |

---

## FQM – Fonction / Qualité / Mesure

| Fonction              | Qualité       | Mesure                                                  |
|-----------------------|---------------|---------------------------------------------------------|
| Consultation catalogue| Accessibilité | Catalogue disponible 24/7 sans interruption             |
|                       | Pertinence    | Résultats précis via filtres ou recherche par mot-clé   |
|                       | Sécurité      | Affichage conditionnel des prix selon le rôle           |
|                       | Performance   | Chargement < 1 seconde pour chaque requête catalogue     |
