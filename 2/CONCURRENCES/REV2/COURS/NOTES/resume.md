Voici le résumé au format Markdown :

# Bonnes pratiques pour le code concurrent en Java

## 1. Gestion des exceptions et interruptions

- Utilisez les exceptions `InterruptedException` et `IOInterruptedException` pour gérer l'interruption des threads.
- Ré-interrompez le thread dans le `catch` pour vous assurer de toujours sortir de la boucle de la bonne manière.
- Évitez de simplement imprimer la pile d'appels dans le `catch`, cela ne résout pas le problème.

Exemple :

```java
var thread = Thread.ofPlatform().start(() -> {
    var sum = 0;
    while (!Thread.interrupted()) { 
        try {
            Thread.sleep(5_000); // méthode bloquante
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            continue;
        }
        sum += findPrime(); // calcul sans appel bloquant mais lent    
    }
    System.out.println(sum); 
});
```

## 2. Utilisation des `BlockingQueue`

- Préférez les méthodes bloquantes comme `put()` et `take()` aux méthodes renvoyant des valeurs spéciales.
- Choisissez l'implémentation de `BlockingQueue` adaptée à votre cas d'utilisation (taille fixe, taille illimitée, etc.).

Exemple :

```java
var queue = new ArrayBlockingQueue<String>(10);
for (var i = 0; i < 3; i++) {
    Thread.ofPlatform().start(() -> {
        for(;;) {
            try {
                Thread.sleep(100);
                queue.put(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                return;
            }
        }
    });
}
```

## 3. Utilisation de l'`ExecutorService`

- Utilisez les méthodes `invokeAll()` et `invokeAny()` pour soumettre et récupérer facilement les résultats de plusieurs tâches.
- N'oubliez pas de fermer correctement l'`ExecutorService` avec `shutdown()` ou `shutdownNow()`.

Exemple :

```java
var executorService = Executors.newFixedThreadPool(2);
var callables = new ArrayList<Callable<Integer>>();
IntStream.range(1, 100).forEach(i -> callables.add(() -> bigComputation(i)));
var futures = executorService.invokeAll(callables);
for (var future : futures) {
    System.out.println(future.resultNow());
}
```

En résumé, les points clés sont la gestion des interruptions, l'utilisation appropriée des `BlockingQueue` et la bonne utilisation de l'`ExecutorService` pour simplifier la gestion des threads. Suivre ces bonnes pratiques vous aidera à écrire du code concurrent robuste et maintenable.