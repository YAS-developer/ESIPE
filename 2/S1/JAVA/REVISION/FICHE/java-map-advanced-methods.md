# Méthodes avancées pour Map en Java : exemples détaillés

## 1. `putIfAbsent(key, value)`

Ajoute la paire clé-valeur si la clé n'existe pas déjà dans la map.

```java
Map<String, Integer> map = new HashMap<>();
map.put("A", 1);

Integer resultA = map.putIfAbsent("A", 100);
System.out.println("Result for A: " + resultA); // Output: Result for A: 1
System.out.println("Map after A: " + map); // Output: Map after A: {A=1}

Integer resultB = map.putIfAbsent("B", 200);
System.out.println("Result for B: " + resultB); // Output: Result for B: null
System.out.println("Map after B: " + map); // Output: Map after B: {A=1, B=200}
```

## 2. `computeIfAbsent(key, mappingFunction)`

Calcule une valeur pour une clé donnée si elle n'est pas déjà présente.

```java
Map<String, Integer> map = new HashMap<>();
map.put("A", 1);

int valueA = map.computeIfAbsent("A", k -> 100);
System.out.println("Value for A: " + valueA); // Output: Value for A: 1

int valueB = map.computeIfAbsent("B", k -> 200);
System.out.println("Value for B: " + valueB); // Output: Value for B: 200

System.out.println(map); // Output: {A=1, B=200}
```

## 3. `compute(key, remappingFunction)`

Calcule une nouvelle valeur pour une clé spécifique.

```java
Map<String, Integer> scores = new HashMap<>();
scores.put("Alice", 95);

scores.compute("Alice", (key, oldValue) -> oldValue + 5);
System.out.println("Alice's new score: " + scores.get("Alice")); // Output: Alice's new score: 100

scores.compute("Bob", (key, oldValue) -> oldValue == null ? 90 : oldValue + 10);
System.out.println("Bob's score: " + scores.get("Bob")); // Output: Bob's score: 90

scores.compute("Charlie", (key, oldValue) -> oldValue == null ? null : oldValue - 10);
System.out.println("Charlie in scores: " + scores.containsKey("Charlie")); // Output: Charlie in scores: false

System.out.println(scores); // Output: {Alice=100, Bob=90}
```

## 4. `merge(key, value, remappingFunction)`

Combine la valeur existante et la nouvelle valeur pour une clé donnée.

```java
Map<String, Integer> inventory = new HashMap<>();
inventory.put("apples", 50);
inventory.put("bananas", 30);

// Ajout de 20 pommes à l'inventaire existant
inventory.merge("apples", 20, Integer::sum);
System.out.println("Apples after merge: " + inventory.get("apples")); // Output: Apples after merge: 70

// Ajout d'oranges (nouvelle entrée)
inventory.merge("oranges", 40, Integer::sum);
System.out.println("Oranges after merge: " + inventory.get("oranges")); // Output: Oranges after merge: 40

// Utilisation de merge pour un comportement similaire à "mergeIfAbsent"
inventory.merge("grapes", 25, (oldValue, newValue) -> oldValue);
System.out.println("Grapes after merge: " + inventory.get("grapes")); // Output: Grapes after merge: 25

System.out.println(inventory); // Output: {apples=70, bananas=30, oranges=40, grapes=25}

// Suppression d'une entrée si la nouvelle valeur calculée est null
inventory.merge("bananas", 10, (oldValue, newValue) -> oldValue > newValue ? null : oldValue + newValue);
System.out.println("Bananas in inventory: " + inventory.containsKey("bananas")); // Output: Bananas in inventory: true
System.out.println("Bananas value: " + inventory.get("bananas")); // Output: Bananas value: 40

System.out.println(inventory); // Output: {apples=70, oranges=40, grapes=25, bananas=40}
```

Ces méthodes offrent des moyens puissants et flexibles de manipuler les données dans une Map :

- `putIfAbsent()` est utile pour ajouter une valeur seulement si la clé n'existe pas.
- `computeIfAbsent()` permet de calculer une valeur si la clé n'est pas présente.
- `compute()` offre une flexibilité totale pour modifier, ajouter ou supprimer des entrées.
- `merge()` est idéale pour combiner des valeurs ou pour un comportement similaire à "mergeIfAbsent".

Chaque méthode a ses cas d'utilisation spécifiques, permettant des opérations atomiques et réduisant le besoin de vérifications conditionnelles explicites.
