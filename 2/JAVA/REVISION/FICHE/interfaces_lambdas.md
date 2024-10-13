Voici un résumé des différentes interfaces fonctionnelles couramment utilisées avec des lambdas dans l'API des flux (streams) en Java :

    Interface fonctionnelle 

Code abstrait 


(U, V) -> T

@FunctionalInterface


Package java.util.function 
0 à 2 paramètres 

# Interfaces fonctionnelles en Java

## Interfaces génériques

1. Runnable : 0 argument, 0 retour
2. Supplier<T> : 0 argument, 1 retour
3. Consumer<T> : 1 argument, 0 retour
4. BiConsumer<T,U> : 2 arguments, 0 retour
5. Function<T,R> : 1 argument, 1 retour
6. BiFunction<T,U,R> : 2 arguments, 1 retour
7. UnaryOperator<T> : 1 argument, 1 retour (même type que l'argument)
8. BinaryOperator<T> : 2 arguments, 1 retour (même type que les arguments)
9. Predicate<T> : 1 argument, retourne un boolean
10. BiPredicate<T,U> : 2 arguments, retourne un boolean

## Interfaces spécialisées pour les types primitifs

### Suppliers
11. IntSupplier : 0 argument, retourne un int
12. LongSupplier : 0 argument, retourne un long
13. DoubleSupplier : 0 argument, retourne un double

### Consumers

14. IntConsumer : 1 argument int, pas de retour
15. LongConsumer : 1 argument long, pas de retour
16. DoubleConsumer : 1 argument double, pas de retour

### Prédicats (Predicates)
17. IntPredicate : 1 argument int, retourne un boolean
18. LongPredicate : 1 argument long, retourne un boolean
19. DoublePredicate : 1 argument double, retourne un boolean

### Fonctions (Functions)
20. IntFunction<R> : 1 argument int, 1 retour générique
21. LongFunction<R> : 1 argument long, 1 retour générique
22. DoubleFunction<R> : 1 argument double, 1 retour générique
23. ToIntFunction<T> : 1 argument générique, retourne un int
24. ToLongFunction<T> : 1 argument générique, retourne un long
25. ToDoubleFunction<T> : 1 argument générique, retourne un double

### Opérateurs binaires primitifs
26. IntBinaryOperator : 2 arguments int, retourne un int
27. LongBinaryOperator : 2 arguments long, retourne un long
28. DoubleBinaryOperator : 2 arguments double, retourne un double

### Fonctions de conversion entre types primitifs
29. IntToLongFunction : 1 argument int, retourne un long
30. IntToDoubleFunction : 1 argument int, retourne un double
31. LongToIntFunction : 1 argument long, retourne un int
32. LongToDoubleFunction : 1 argument long, retourne un double
33. DoubleToIntFunction : 1 argument double, retourne un int
34. DoubleToLongFunction : 1 argument double, retourne un long

### Consommateurs mixtes (objets et primitifs)
35. ObjIntConsumer<T> : 1 argument générique, 1 argument int, pas de retour
36. ObjLongConsumer<T> : 1 argument générique, 1 argument long, pas de retour
37. ObjDoubleConsumer<T> : 1 argument générique, 1 argument double, pas de retour

Ces interfaces permettent d'utiliser des lambdas pour des opérations courantes sur des flux de données, offrant une manière concise et expressive de manipuler les collections en Java.

