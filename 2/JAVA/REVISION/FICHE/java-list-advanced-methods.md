# Méthodes avancées pour List en Java : exemples détaillés

## 1. `replaceAll(UnaryOperator<E> operator)`

Remplace chaque élément de la liste par le résultat de l'application de l'opérateur.

```java
List<String> fruits = new ArrayList<>(Arrays.asList("apple", "banana", "cherry"));
fruits.replaceAll(String::toUpperCase);
System.out.println(fruits); // Output: [APPLE, BANANA, CHERRY]
```

## 2. `removeIf(Predicate<? super E> filter)`

Supprime tous les éléments de la liste qui satisfont le prédicat donné.

```java
List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
numbers.removeIf(n -> n % 2 == 0);
System.out.println(numbers); // Output: [1, 3, 5, 7, 9]
```

## 3. `sort(Comparator<? super E> c)`

Trie la liste selon le comparateur fourni.

```java
List<String> names = new ArrayList<>(Arrays.asList("Alice", "Bob", "Charlie", "David"));
names.sort(Comparator.reverseOrder());
System.out.println(names); // Output: [David, Charlie, Bob, Alice]
```

## 4. `subList(int fromIndex, int toIndex)`

Retourne une vue de la portion de la liste entre fromIndex (inclus) et toIndex (exclus).

```java
List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
List<Integer> subList = numbers.subList(1, 4);
subList.set(1, 10); // Modifie la liste originale aussi
System.out.println(subList); // Output: [2, 10, 4]
System.out.println(numbers); // Output: [1, 2, 10, 4, 5]
```

## 5. `retainAll(Collection<?> c)`

Conserve uniquement les éléments de cette liste qui sont contenus dans la collection spécifiée.

```java
List<String> list1 = new ArrayList<>(Arrays.asList("A", "B", "C", "D"));
List<String> list2 = Arrays.asList("B", "D", "E");
list1.retainAll(list2);
System.out.println(list1); // Output: [B, D]
```

## 6. `listIterator()`

Retourne un itérateur de liste qui permet des modifications bidirectionnelles.

```java
List<String> colors = new ArrayList<>(Arrays.asList("red", "green", "blue"));
ListIterator<String> iterator = colors.listIterator();
while (iterator.hasNext()) {
    String color = iterator.next();
    if (color.equals("green")) {
        iterator.add("yellow");
    }
}
System.out.println(colors); // Output: [red, green, yellow, blue]
```

## 7. `forEach(Consumer<? super E> action)`

Effectue l'action donnée pour chaque élément de la liste.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
numbers.forEach(n -> System.out.print(n * n + " ")); // Output: 1 4 9 16 25 
```

Ces méthodes offrent des moyens puissants et flexibles de manipuler les données dans une List :

- `replaceAll()` permet de transformer tous les éléments de la liste en une seule opération.
- `removeIf()` offre un moyen concis de supprimer des éléments basés sur une condition.
- `sort()` permet de trier la liste avec un comparateur personnalisé.
- `subList()` crée une vue modifiable d'une portion de la liste.
- `retainAll()` est utile pour l'intersection de listes.
- `listIterator()` permet une manipulation fine et bidirectionnelle des éléments.
- `forEach()` offre une syntaxe concise pour effectuer une action sur chaque élément.

Ces méthodes permettent d'effectuer des opérations complexes sur les listes de manière efficace et lisible.
