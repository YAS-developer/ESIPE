
```java
List<? extends Number> numbers = new ArrayList<Integer>();
Number n = numbers.get(0);  // OK
// numbers.add(1);  // Erreur de compilation

List<? super Integer> integers = new ArrayList<Number>();
integers.add(1);  // OK
// Integer i = integers.get(0);  // Erreur de compilation
Object o = integers.get(0);  // OK
```