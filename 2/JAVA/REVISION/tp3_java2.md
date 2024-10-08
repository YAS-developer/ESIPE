### TP3 JAVA 2 YASSINE HAMROUNI



### 1- Écrire l'interface Slice et sa méthode of sachant que l'on va declarer l'implantation de l'interface SliceImpl en tant que classe interne de l'interface.


```java


```

### 2- Sachant qu'il existe une méthode java.util.Arrays.stream(array, start, end), implanter la méthode d'affichage en utilisant un stream. 

```java

import java.util.Arrays;
import java.util.stream.Collectors;

public interface Slice<E> {
    // ... (autres méthodes comme précédemment)

    final class SliceImpl<E> implements Slice<E> {
        // ... (autres méthodes comme précédemment)

        @Override
        public String toString() {
            return Arrays.stream(elements, from, to).toList().toString();
        }
    }
}
```


### 3- Implanter la méthode subSlice



```java

package fr.uge.slice;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public interface Slice<E> {
    int size();
    E get(int index);
    Slice<E> subSlice(int from, int to);

    static <E> Slice<E> of(E[] elements, int from, int to) {
        Objects.requireNonNull(elements, "elements array is null");
        Objects.checkFromToIndex(from, to, elements.length);
        return new SliceImpl<>(elements, from, to);
    }

    final class SliceImpl<E> implements Slice<E> {
        private final E[] elements;
        private final int from;
        private final int to;

        private SliceImpl(E[] elements, int from, int to) {
            this.elements = elements;
            this.from = from;
            this.to = to;
        }

        @Override
        public int size() {
            return to - from;
        }

        @Override
        public E get(int index) {
            Objects.checkIndex(index, size());
            return elements[from + index];
        }

        @Override
        public Slice<E> subSlice(int fromIndex, int toIndex) {
            Objects.checkFromToIndex(fromIndex, toIndex, size());
            return new SliceImpl<>(elements, from + fromIndex, from + toIndex);
        }

        @Override
        public String toString() {
            return Arrays.stream(elements, from, to)
                    .map(e -> e == null ? "null" : e.toString())
                    .collect(Collectors.joining(", ", "[", "]"));
        }
    }
}
```


### 4- Implanter la méthode reversed qui renvoie un nouveau slice qui permet de voir les éléments en sens inverse (sans copier les éléments).

```java

package fr.uge.slice;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public interface Slice<E> {
    int size();
    E get(int index);
    Slice<E> subSlice(int from, int to);

    default Slice<E> reversed() {
        return new Slice<>() {
            @Override
            public int size() {
                return Slice.this.size();
            }

            @Override
            public E get(int index) {
                return Slice.this.get(size() - 1 - index);
            }

            @Override
            public Slice<E> subSlice(int from, int to) {
                throw new UnsupportedOperationException("subSlice not implemented for reversed slice");
            }

            @Override
            public Slice<E> reversed() {
                return Slice.this;
            }
        };
    }

    static <E> Slice<E> of(E[] elements, int from, int to) {
        Objects.requireNonNull(elements, "elements array is null");
        Objects.checkFromToIndex(from, to, elements.length);
        return new SliceImpl<>(elements, from, to);
    }

    final class SliceImpl<E> implements Slice<E> {
        private final E[] elements;
        private final int from;
        private final int to;

        private SliceImpl(E[] elements, int from, int to) {
            this.elements = elements;
            this.from = from;
            this.to = to;
        }

        @Override
        public int size() {
            return to - from;
        }

        @Override
        public E get(int index) {
            Objects.checkIndex(index, size());
            return elements[from + index];
        }

        @Override
        public Slice<E> subSlice(int fromIndex, int toIndex) {
            Objects.checkFromToIndex(fromIndex, toIndex, size());
            return new SliceImpl<>(elements, from + fromIndex, from + toIndex);
        }

        @Override
        public String toString() {
            return Arrays.stream(elements, from, to)
                    .map(e -> e == null ? "null" : e.toString())
                    .collect(Collectors.joining(", ", "[", "]"));
        }
    }
}

```

### 5- On souhaite ajouter à l'interface Slice une méthode replaceAll qui permet de remplacer chaque élément en appelant une fonction avec l'ancienne valeur de l'élément, la fonction renvoyant la nouvelle valeur de l'élément. Par exemple, pour ajouter des étoiles autour des chaînes de caractères, on peut écrire. Quelle est l'interface fonctionnelle que l'on doit utiliser en paramètre de replaceAll ? Ajouter la méthode replaceAll à l'interface Slice et modifier les implantations en conséquence. 

```java

public interface Slice<E> {
    // ... autres méthodes existantes ...

    void replaceAll(UnaryOperator<E> operator);

    default Slice<E> reversed() {
        return new Slice<>() {
            // ... autres méthodes existantes ...

            @Override
            public void replaceAll(UnaryOperator<E> operator) {
                Objects.requireNonNull(operator);
                Slice.this.replaceAll(operator);
            }
        };
    }

    final class SliceImpl<E> implements Slice<E> {
        // ... autres méthodes existantes ...

        @Override
        public void replaceAll(UnaryOperator<E> operator) {
            Objects.requireNonNull(operator);
            for (int i = from; i < to; i++) {
                elements[i] = operator.apply(elements[i]);
            }
        }
    }
}

```


### 6 - On souhaite maintenant implanter la méthode subSlice(from, to) quand le Slice est reversed. Implanter la méthode subSlice(from, to) dans la classe anonyme dans la méthode reversed(). 

```java
@Override
public Slice<E> subSlice(int from, int to) {
    Objects.checkFromToIndex(from, to, size());
    return Slice.this.subSlice(size() - to, size() - from).reversed();
}

```



### 7- méthode d'affichage dans le cas où le Slice est reversed(). 


```java


 @Override
public String toString() {
  return IntStream.range(0, size()).mapToObj(i -> get(i)).toList().toString();
}


```


