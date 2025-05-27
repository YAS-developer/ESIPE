

### Base de données  
Composant central qui stocke et gère les informations utilisateurs, produits, devis, commandes, paiements et historiques de mouvement. Assure l’authentification, l’autorisation et la persistance des données.

### Système d’autocomplétion devis  
Service intelligent qui suggère et complète automatiquement les champs sensibles (adresse, numéro de SIRET, etc.) pour réduire les erreurs de saisie lors de la création de devis et bons de commande.

### Système de notification  
Mécanisme d’envoi automatique de notifications (e-mail, push) aux clients (confirmation de commande, expédition) et aux administrateurs (anomalies, requêtes de mise à jour).

### Système de paiement en ligne  
Module sécurisé qui traite les transactions bancaires, génère les factures associées et notifie le système de l’issue (succès ou échec) du paiement.

### Système du meilleur commercial  
Algorithme de scoring qui collecte les données d’activité de chaque commercial et élit, chaque mois, le « meilleur commercial » selon des critères (chiffre d’affaires, nombre de devis signés, etc.).

## Concepts Clés

### Bon de commande  
Document généré automatiquement à partir d’un devis accepté et signé électroniquement par le client, servant de référence formelle pour la création de la commande.

### Catalogue  
Ensemble des produits proposés, accessible depuis la tablette ; filtré selon le rôle (prix masqués pour les commerciaux internes, prix affichés pour les clients).

### Devis  
Offre commerciale détaillant les produits, quantités, conditions et tarifs proposée par le commercial. Doit être acceptée et signée électroniquement pour devenir un bon de commande.

### Fidélisation  
Programme de points cumulés par le client en fonction du montant et du nombre de commandes, consultable et actualisé après chaque commande validée.

### Intégrations  
Connecteurs techniques vers :
- **CRM (SugarCRM)** pour la gestion des fiches clients et prospects.  
- **ERP (SAGE)** pour la synchronisation des stocks, la facturation avancée et la comptabilité.  
- **Boutique en ligne** pour refléter en temps réel les ventes réalisées en salon dans l’inventaire.

### Signature électronique  
Procédure sécurisée permettant au client de signer un devis ou un bon de commande, avec conservation chiffrée du document et journalisation de la date, de l’heure et de l’identité.

### Traçabilité  
Capacité à remonter l’historique complet des actions (création, modification, synchronisation) grâce à la journalisation et aux horodatages, garantissant auditabilité et conformité.
