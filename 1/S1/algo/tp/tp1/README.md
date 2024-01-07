l'exo 2 se trouve dans le main de verif.c

EX2

create_array

Le temps d'allocation mémoire est constant et ne dépend pas de la taille du tableau. Cela est cohérent avec une complexité de temps de O(1).
insert_unsorted

insert_unsorted
Le temps d'exécution augmente linéairement avec la taille du tableau. Les mesures sont en accord avec une complexité de temps de O(n), où n est la taille du tableau.

find_unsorted
Le temps d'exécution augmente linéairement avec la taille du tableau. Les résultats observés correspondent à une complexité de temps de O(n) pour la recherche linéaire non triée.
insert_sorted et find_sorted


insert_sorted et find_sorted
Pour ces fonctions, les tests nécessitent des tableaux triés, ce qui implique une génération aléatoire suivie d'un tri avant l'application des fonctions. Les mesures correspondent à une complexité de temps proche de O(n) pour l'insertion et O(log n) pour la recherche dans un tableau trié.