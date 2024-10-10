

### TP5 java 2




#### 1- On va dans un premier temps écrire une version simple de la classe DedupVec, avec un constructeur sans paramètre, les méthodes size et get(index) ainsi que la méthode add(element) qui pour l'instant n'effectue pas la déduplication. 



```java



public final class DedupVec<T> {

  private final HashMap<T,T> map;

  //linkedlist car resize
  private final LinkedList<T> list;

  public DedupVec() {
	  map = new HashMap<>();
	  list = new LinkedList<>();
  }

  public int size(){
    return list.size();
  }

  public T get(int index){
    return list.get(index);
  }

  public void add(T elementToAdd){
    Objects.requireNonNull(elementToAdd);
    var value = map.putIfAbsent(elementToAdd, elementToAdd);
    list.add(value != null ? value : elementToAdd);
  }

}

```



#### 3- On souhaite ajouter une méthode contains(element) qui teste si une valeur est un des éléments de DedupVec. 



```java 


public boolean contains(Object elementToCheck){
    Objects.requireNonNull(elementToCheck);
    return map.containsKey(elementToCheck);
  }


```



#### 4- On souhaite ajouter une méthode addAll(dedupVec) qui permet d'ajouter tous les éléments d'un DedupVec à un autre DedupVec. 




```java

  public void addAll(DedupVec<T> elementsToAdd){
    Objects.requireNonNull(elementsToAdd);
    for(var element : elementsToAdd.list){
      add(element);
    }
  }
```


#### 5- on souhaite ajouter une méthode d'aide (helper method) pas publique, newMapFromSet(set) qui prend en paramètre un ensemble et renvoie une Map qui pour chaque élément de l'ensemble associe ce même élément



```java

static <E> Map<E, E> newMapFromSet(Set<E> baseSet){
    return new AbstractMap<E, E>() { //On doit renvoyer une AbstractMap = Map anonyme
      @Override
      public Set<Entry<E, E>> entrySet() {
        return new AbstractSet<Entry<E, E>>() {

          @Override
          public Iterator<Entry<E, E>> iterator() {
            var iterator = baseSet.iterator();
            return new Iterator<Entry<E, E>>() {
              @Override
              public boolean hasNext() {
                return iterator.hasNext();
              }

              @Override
              public Entry<E, E> next() {
                var next = iterator.next();
                return Map.entry(next, next);
              }
            };
          }

          @Override
          public int size() {
            return baseSet.size();
          }
        };
      }
    };
  }


```




#### 6 - On va maintenant écrire la méthode fromSet(set) qui prend en paramètre un ensemble et créé un DedupVec avec l'ensemble des éléments de l'ensemble. 





```java

 private DedupVec(Collection<? extends T> c) {
	  Objects.requireNonNull(c);
	  this.map = new HashMap<>(c.size());
	  this.list = new LinkedList<>();
	  for (T element : c) {
          Objects.requireNonNull(element);
          map.put(element, element);
          list.add(element);
      }
  }
  
  
  public static<E>  DedupVec<E> fromSet(Set<? extends E> set){
	  Objects.requireNonNull(set);
	  return new DedupVec<>(set); 
  }

```



#### 7- On souhaite changer l'implantation de addAll pour qu'elle soit un peu plus efficace (mais pas de quoi changer la complexité pire cas malheureusement). 


```java


 public void addAll(DedupVec<T> elementsToAdd){
    Objects.requireNonNull(elementsToAdd);
    
 
    var hasDuplicates = false;

    // Vérifier si les ensembles sont disjoints
    for (T key : elementsToAdd.map.keySet()) {
        if (map.putIfAbsent(key, key) != null) {
            hasDuplicates = true;
            break;
        }
    }

    if (!hasDuplicates) {
        // Si les ensembles sont disjoints, ajouter directement tous les éléments
        list.addAll(elementsToAdd.list);
    } else {
        // Sinon, vérifier chaque élément individuellement
        for (T element : elementsToAdd.list) {
            T existingElement = map.get(element);
            list.add(existingElement != null ? existingElement : element);
        }
    }
  }


```





#### 8 - On souhaite que la classe DedupVec se comporte comme une java.util.List. Pour nous aider, nous allons utiliser la classe AbstractList qui fournit déjà une implantation de nombreuses méthodes de l'interface List. 

```java
package fr.uge.dedup;

import java.util.*;

public final class DedupVec<T> extends AbstractList<T> implements RandomAccess {
    private final HashMap<T, T> map;
    private final ArrayList<T> list;

    public DedupVec() {
        this.map = new HashMap<>();
        this.list = new ArrayList<>();
    }

    private DedupVec(Set<? extends T> set) {
        this.map = new HashMap<>(set.size());
        this.list = new ArrayList<>(set.size());
        for (T element : set) {
            Objects.requireNonNull(element);
            map.put(element, element);
            list.add(element);
        }
    }

    @Override
    public boolean add(T element) {
        Objects.requireNonNull(element);
        T existingElement = map.putIfAbsent(element, element);
        list.add(existingElement == null ? element : existingElement);
        return true;
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean contains(Object o) {
        return o != null && map.containsKey(o);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c instanceof DedupVec) {
            return addAllDedupVec((DedupVec<T>) c);
        }
        boolean modified = false;
        for (T element : c) {
            if (add(element)) {
                modified = true;
            }
        }
        return modified;
    }

    private boolean addAllDedupVec(DedupVec<T> other) {
        boolean modified = false;
        boolean hasDuplicates = false;

        for (T key : other.map.keySet()) {
            if (map.putIfAbsent(key, key) != null) {
                hasDuplicates = true;
                break;
            }
        }

        if (!hasDuplicates) {
            list.addAll(other.list);
            modified = !other.isEmpty();
        } else {
            for (T element : other.list) {
                T existingElement = map.get(element);
                if (existingElement == null) {
                    list.add(element);
                    map.put(element, element);
                    modified = true;
                } else {
                    list.add(existingElement);
                    modified = true;
                }
            }
        }
        return modified;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return Collections.unmodifiableList(list.subList(fromIndex, toIndex));
    }

    public static <E> DedupVec<E> fromSet(Set<? extends E> set) {
        Objects.requireNonNull(set, "Set cannot be null");
        return new DedupVec<>(set);
    }

    static <E> Map<E, E> newMapFromSet(Set<E> set) {
    Objects.requireNonNull(set, "Set cannot be null");
    return new AbstractMap<E, E>() {
        @Override
        public Set<Entry<E, E>> entrySet() {
            return new AbstractSet<Entry<E, E>>() {
                @Override
                public Iterator<Entry<E, E>> iterator() {
                    return set.stream()
                        .map(e -> Map.entry(e, e))
                        .iterator();
                }

                @Override
                public int size() {
                    return set.size();
                }
            };
        }

        @Override
        public E get(Object key) {
            if (key == null) {
                throw new NullPointerException();
            }
            return set.contains(key) ? (E) key : null;
        }

        @Override
        public E getOrDefault(Object key, E defaultValue) {
            if (key == null) {
                throw new NullPointerException();
            }
            return set.contains(key) ? (E) key : defaultValue;
        }

        @Override
        public boolean containsKey(Object key) {
            if (key == null) {
                throw new NullPointerException();
            }
            return set.contains(key);
        }

        @Override
        public int size() {
            return set.size();
        }
    };
    }
}

```


#### 9- Enfin, on peut remarquer que sur DedupVec, il n'est pour l'instant pas possible d'ajouter un élément autre part qu'à la fin, une méthode comme addFirst ou un appel à la méthode add sur une subList() ne marchent pas. En relisant la documentation de la classe AbstractList, quelle méthode (une seule) doit-on implanter pour que ces méthodes fonctionnent ? 


```java


package fr.uge.dedup;

import java.util.*;

public final class DedupVec<T> extends AbstractList<T> implements RandomAccess {
    private final HashMap<T, T> map;
    private final ArrayList<T> list;

    public DedupVec() {
        this.map = new HashMap<>();
        this.list = new ArrayList<>();
    }

    private DedupVec(Collection<? extends T> c) {
        this.map = new HashMap<>(c.size());
        this.list = new ArrayList<>(c.size());
        for (T element : c) {
            add(element);
        }
    }

    @Override
    public boolean add(T element) {
        Objects.requireNonNull(element);
        T existingElement = map.putIfAbsent(element, element);
        list.add(existingElement == null ? element : existingElement);
        return true;
    }

    @Override
    public void add(int index, T element) {
        Objects.requireNonNull(element);
        if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
        T existingElement = map.putIfAbsent(element, element);
        list.add(index, existingElement == null ? element : existingElement);
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean contains(Object o) {
        return o != null && map.containsKey(o);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T element : c) {
            if (add(element)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return Collections.unmodifiableList(list.subList(fromIndex, toIndex));
    }

    public void addFirst(T element) {
        add(0, element);
    }

    public void addLast(T element) {
        add(size(), element);
    }

    public T getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return get(0);
    }

    public T getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return get(size() - 1);
    }

    @Override
    public List<T> reversed() {
        return new AbstractList<T>() {
            @Override
            public T get(int index) {
                return DedupVec.this.get(size() - 1 - index);
            }

            @Override
            public int size() {
                return DedupVec.this.size();
            }

            @Override
            public void add(int index, T element) {
                DedupVec.this.add(size() - index, element);
            }
        };
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public static <E> DedupVec<E> fromSet(Set<? extends E> set) {
        Objects.requireNonNull(set, "Set cannot be null");
        return new DedupVec<>(set);
    }

    static <E> Map<E, E> newMapFromSet(Set<E> set) {
        Objects.requireNonNull(set, "Set cannot be null");
        return new AbstractMap<E, E>() {
            @Override
            public Set<Entry<E, E>> entrySet() {
                return new AbstractSet<Entry<E, E>>() {
                    @Override
                    public Iterator<Entry<E, E>> iterator() {
                        return set.stream()
                            .map(e -> Map.entry(e, e))
                            .iterator();
                    }

                    @Override
                    public int size() {
                        return set.size();
                    }
                };
            }

            @SuppressWarnings("unchecked")
			@Override
            public E get(Object key) {
                if (key == null) {
                    throw new NullPointerException();
                }
                return set.contains(key) ? (E) key : null;
            }

            @SuppressWarnings("unchecked")
			@Override
            public E getOrDefault(Object key, E defaultValue) {
                if (key == null) {
                    throw new NullPointerException();
                }
                return set.contains(key) ? (E) key : defaultValue;
            }

            @Override
            public boolean containsKey(Object key) {
                if (key == null) {
                    throw new NullPointerException();
                }
                return set.contains(key);
            }

            @Override
            public int size() {
                return set.size();
            }
        };
    }
}


```