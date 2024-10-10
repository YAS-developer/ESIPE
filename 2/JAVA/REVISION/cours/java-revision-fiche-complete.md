# Fiche de révision Java complète

## 1. Concepts de base en Java

### Points clés
- Java est un langage orienté objet, fortement typé et portable.
- Utilise la JVM (Java Virtual Machine) pour l'exécution du bytecode.
- Supporte les paradigmes impératif, orienté objet et fonctionnel.

### Exemple de code

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

## 2. Lambdas et interfaces fonctionnelles

### Points clés
- Les lambdas sont des fonctions anonymes concises.
- Les interfaces fonctionnelles ont une seule méthode abstraite.
- Permettent une programmation plus fonctionnelle en Java.

### Exemple de code

```java
// Interface fonctionnelle
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}

// Utilisation de lambda
Calculator addition = (a, b) -> a + b;
int result = addition.calculate(5, 3); // résultat: 8
```

## 3. Classes internes et énumérations

### Points clés
- Classes internes : classes définies à l'intérieur d'une autre classe.
- Énumérations : type spécial de classe pour représenter un ensemble fixe de constantes.

### Exemple de code

```java
public class OuterClass {
    private int value = 10;

    // Classe interne
    class InnerClass {
        void print() {
            System.out.println("Valeur: " + value);
        }
    }

    // Énumération
    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
```

## 4. Pattern Matching

### Points clés
- Introduit dans Java 14 et amélioré dans les versions suivantes.
- Permet une vérification de type et une extraction de données plus concise.
- Utilisé avec `instanceof` et `switch` expressions.

### Exemple de code

```java
Object obj = "Hello";

if (obj instanceof String s && s.length() > 5) {
    System.out.println("C'est une chaîne longue: " + s);
}

// Switch expression avec pattern matching (Java 17+)
String result = switch (obj) {
    case Integer i -> "C'est un entier: " + i;
    case String s -> "C'est une chaîne: " + s;
    default -> "Type inconnu";
};
```

## 5. Exceptions

### Points clés
- Mécanisme pour gérer les erreurs et les situations exceptionnelles.
- Deux types : vérifiées (checked) et non vérifiées (unchecked).
- Utilisation de `try`, `catch`, `finally`, et `try-with-resources`.

### Exemple de code

```java
public void readFile(String path) {
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    } catch (IOException e) {
        System.err.println("Erreur de lecture: " + e.getMessage());
    }
}
```

## 6. Types et méthodes paramétrés (Generics)

### Points clés
- Permettent de créer des classes et méthodes type-safe.
- Améliorent la réutilisation du code et la sécurité du type.
- Utilisent la notation `<T>` pour les types génériques.

### Exemple de code

```java
public class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() { return key; }
    public V getValue() { return value; }
}

Pair<String, Integer> pair = new Pair<>("Age", 30);
```

## 7. Collections

### Points clés
- Framework pour manipuler des groupes d'objets.
- Interfaces principales : List, Set, Map, Queue.
- Implémentations courantes : ArrayList, HashSet, HashMap, LinkedList.

### Exemple de code

```java
// Liste
List<String> list = new ArrayList<>();
list.add("Java");
list.add("Python");

// Map
Map<String, Integer> map = new HashMap<>();
map.put("Un", 1);
map.put("Deux", 2);

// Parcours d'une Map
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}

// Utilisation de Stream avec une collection
list.stream()
    .filter(lang -> lang.startsWith("J"))
    .forEach(System.out::println);
```

