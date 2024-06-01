# Résultats des Expérimentations AVL

## Insertion d'Éléments Aléatoires et Ordonnés

### 10 000 Éléments

- **Éléments Aléatoires :**
  - Temps d'insertion : 0.002825 secondes
  - Hauteur de l'arbre : 15

- **Éléments Ordonnés :**
  - Temps d'insertion : 0.002429 secondes
  - Hauteur de l'arbre : 13

### 100 000 Éléments

- **Éléments Aléatoires :**
  - Temps d'insertion : 0.044995 secondes
  - Hauteur de l'arbre : 19

- **Éléments Ordonnés :**
  - Temps d'insertion : 0.034494 secondes
  - Hauteur de l'arbre : 16

### 1 000 000 Éléments

- **Éléments Aléatoires :**
  - Temps d'insertion : 0.709275 secondes
  - Hauteur de l'arbre : 23

- **Éléments Ordonnés :**
  - Temps d'insertion : 0.432923 secondes
  - Hauteur de l'arbre : 19

## Remarques

- **Temps d'Insertion :**
  - Pour 10 000 éléments, l'insertion est très rapide.
  - Avec 100 000 et 1 000 000 éléments, le temps d'insertion augmente mais reste efficace.

- **Hauteur de l'Arbre :**
  - L'arbre AVL garde une hauteur basse même pour de grands nombres d'éléments.
  - La hauteur augmente légèrement avec plus d'éléments mais reste logarithmique par rapport au nombre d'éléments (log(N)).

- **Comparaison Aléatoire vs Ordonné :**
  - Les insertions ordonnées sont un peu plus rapides que les aléatoires.
  - La hauteur de l'arbre est légèrement plus basse pour les insertions ordonnées.

## Nombre d'Éléments Insérés en Temps Limité

### En 10 Secondes

- **Nombre d'éléments insérés :** 530000

### En 20 Secondes

- **Nombre d'éléments insérés :** 690000
