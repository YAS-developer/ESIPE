# Exemples de code Java - Révisions

## 1. Concepts de base

### 1.1 Interfaces et classes

```java
public interface Shape {
    double area();
}

public class Circle implements Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}
```

### 1.2 Classes internes

```java
public class OuterClass {
    private int value = 10;

    class InnerClass {
        public void print() {
            System.out.println("Value from outer class: " + value);
        }
    }
}

// Usage
OuterClass outer = new OuterClass();
OuterClass.InnerClass inner = outer.new InnerClass();
inner.print();
```

### 1.3 Records

```java
public record Person(String name, int age) {
    // Compact constructor
    public Person {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
    }
}

// Usage
Person p = new Person("Alice", 30);
System.out.println(p.name()); // Alice
```

### 1.4 Génériques

```java
public class Box<T> {
    private T content;

    public void set(T content) {
        this.content = content;
    }

    public T get() {
        return content;
    }
}

// Usage
Box<Integer> intBox = new Box<>();
intBox.set(10);
Integer content = intBox.get();
```

## 2. Programmation fonctionnelle

### 2.1 Interfaces fonctionnelles et Lambdas

```java
@FunctionalInterface
interface MathOperation {
    int operate(int a, int b);
}

// Usage with lambda
MathOperation addition = (a, b) -> a + b;
System.out.println(addition.operate(5, 3)); // 8
```

### 2.2 Méthodes de référence

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.forEach(System.out::println);

names.forEach((name) -> {
    String upperCase = name.toUpperCase();
    System.out.println("Name: " + upperCase);
});

```

### 2.3 Streams

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CollectorsJoiningExample {
    public static void main(String[] args) {
        List<String> fruits = Arrays.asList("pomme", "banane", "orange", "kiwi", "fraise");

        String result = fruits.stream()
            .filter(fruit -> fruit.length() > 4)  // Filtrer les fruits avec plus de 4 lettres
            .map(String::toUpperCase)             // Convertir en majuscules
            .collect(Collectors.joining(
                " | ",    // délimiteur
                "{ ",     // préfixe
                " }"      // suffixe
            ));

        System.out.println(result);
    }
}
```




## 3. Collections

### 3.1 ArrayList et LinkedList

```java
List<String> arrayList = new ArrayList<>();
arrayList.add("First");
arrayList.add("Second");

List<String> linkedList = new LinkedList<>();
linkedList.add("First");
linkedList.add("Second");
```

### 3.2 HashMap

```java
Map<String, Integer> ages = new HashMap<>();
ages.put("Alice", 30);
ages.put("Bob", 25);

int aliceAge = ages.get("Alice"); // 30
```

### 3.3 Collections non modifiables

```java
List<String> mutableList = new ArrayList<>(Arrays.asList("a", "b", "c"));
List<String> immutableList = Collections.unmodifiableList(mutableList);

// This will throw UnsupportedOperationException
// immutableList.add("d");
```

## 4. Gestion des erreurs

```java
public void processInput(String input) {
    Objects.requireNonNull(input, "Input cannot be null");
    
    try {
        // Process input
    } catch (IllegalArgumentException e) {
        System.err.println("Invalid input: " + e.getMessage());
    }
}
```

## 5. Concepts avancés

### 5.1 Structures de données personnalisées (exemple simplifié)

```java
public class SimpleHashSet<E> {
    private static final int INITIAL_CAPACITY = 16;
    private Object[] buckets;

    public SimpleHashSet() {
        buckets = new Object[INITIAL_CAPACITY];
    }

    public void add(E element) {
        int index = Math.abs(element.hashCode() % buckets.length);
        buckets[index] = element;
    }

    public boolean contains(E element) {
        int index = Math.abs(element.hashCode() % buckets.length);
        return buckets[index] != null && buckets[index].equals(element);
    }
}
```

### 5.2 Pattern matching avec switch

```java
public static String getTypeDescription(Object obj) {
    return switch (obj) {
        case Integer i -> "It's an integer: " + i;
        case String s -> "It's a string: " + s;
        case List<?> l -> "It's a list of size: " + l.size();
        default -> "It's something else";
    };
}
```

### 5.3 Classe immuable

```java
public final class ImmutablePerson {
    private final String name;
    private final int age;

    public ImmutablePerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
```

### 5.4 Implémentation de AbstractList

```java
public class SimpleList<E> extends AbstractList<E> {
    private Object[] elements = new Object[10];
    private int size = 0;

    @Override
    public E get(int index) {
        if (index >= size) throw new IndexOutOfBoundsException();
        @SuppressWarnings("unchecked")
        E element = (E) elements[index];
        return element;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(E e) {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, size * 2);
        }
        elements[size++] = e;
        return true;
    }
}
```

## 6. Bonnes pratiques

```java
public class BestPractices<T> {
    private final List<T> items;

    public BestPractices(List<T> items) {
        this.items = new ArrayList<>(Objects.requireNonNull(items, "Items list cannot be null"));
    }

    @Override
    public String toString() {
        return "BestPractices{" +
               "items=" + items +
               '}';
    }
}
```

## 7. Techniques d'optimisation

```java
public class CachedCalculator {
    private final Map<Integer, Integer> cache = new HashMap<>();

    public int fibonacci(int n) {
        return cache.computeIfAbsent(n, this::calculateFibonacci);
    }

    private int calculateFibonacci(int n) {
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
```

## 8. Design Patterns

### Factory Method

```java
public interface Animal {
    void speak();
}

public class Dog implements Animal {
    @Override
    public void speak() {
        System.out.println("Woof!");
    }
}

public class Cat implements Animal {
    @Override
    public void speak() {
        System.out.println("Meow!");
    }
}

public class AnimalFactory {
    public static Animal createAnimal(String type) {
        return switch (type.toLowerCase()) {
            case "dog" -> new Dog();
            case "cat" -> new Cat();
            default -> throw new IllegalArgumentException("Unknown animal type");
        };
    }
}
```

## 9. API Java importantes

```java
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.List;

public class APIExample {
    public static List<String> filterAndUppercase(List<String> input) {
        Predicate<String> longerThan5 = s -> s.length() > 5;
        
        return input.stream()
                    .filter(longerThan5)
                    .map(String::toUpperCase)
                    .collect(Collectors.toList());
    }
}
```

## 10. Nouveautés Java

### Sealed Classes

```java
public sealed interface Shape
    permits Circle, Rectangle, Triangle {
    double area();
}

public final class Circle implements Shape {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

// Similar implementations for Rectangle and Triangle
```

Ces exemples couvrent les concepts principaux abordés dans vos TPs. Utilisez-les comme référence rapide lors de vos révisions. N'hésitez pas à les modifier et à expérimenter avec pour approfondir votre compréhension.

