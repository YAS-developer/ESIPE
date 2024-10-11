Fiche de révision Java
1. Concepts de base

Interfaces et classes
Classes internes (inner classes)
Records
Génériques
Héritage et polymorphisme

2. Programmation fonctionnelle

Interfaces fonctionnelles
Lambdas
Méthodes de référence
Streams

3. Collections

List, Set, Map
ArrayList, LinkedList
HashMap
Collections non modifiables

4. Gestion des erreurs

Exceptions (checked et unchecked)
Utilisation de Objects.requireNonNull()

5. Concepts avancés
5.1 Structures de données personnalisées

Implémentation d'une table de hachage (HashTableSet)
Implémentation d'une liste avec déduplication (DedupVec)

5.2 Pattern matching

Utilisation de switch expressions avec pattern matching

5.3 Encapsulation et immutabilité

Classes finales
Getters/setters
Copie défensive

Utilisez List.copyOf() quand vous voulez une copie immuable et indépendante de la liste originale.

Utilisez Collections.unmodifiableList() quand vous voulez une vue non modifiable qui reflète les changements de la liste originale.




Quand on modifie la liste originale, cela :

Ne modifie PAS la liste créée par List.copyOf()
Modifie la vue créée par Collections.unmodifiableList()

Aussi pour les maps:

Map.copyOf(original);
Collections.unmodifiableMap(original);

5.4 API de Collections

Implémentation de AbstractList
Utilisation de RandomAccess

6. Bonnes pratiques

Gestion de la nullité
Utilisation correcte des génériques
Création de classes immuables
Utilisation de @Override

7. Techniques d'optimisation

Mise en cache des résultats
Redimensionnement efficace des structures de données

8. Design Patterns

Builder pattern (implicite dans certaines implémentations)
Factory method (ex: fromSet)

9. API Java importantes

java.util.function (Consumer, Predicate, etc.)
java.util.stream
java.util.Collections

10. Nouveautés Java

Sealed classes/interfaces
Records
Pattern matching pour instanceof
Text blocks (implicite dans certains exemples de code)
