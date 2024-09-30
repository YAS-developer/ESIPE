



#### 1

#### c'est une question d'organisation et de protection du code. Cela rend l'implémentation de HashTableSet plus claire et plus sûre.

```java
package fr.uge.set;

import java.util.Objects;
import java.util.function.Consumer;

public final class HashTableSet {
    private static record Entry(Object value, Entry next) {
    }
}

```

#### 2 - On souhaite maintenant ajouter un constructeur sans paramètre, une méthode add qui permet d'ajouter un élément non null et une méthode size qui renvoie le nombre d'éléments insérés (avec une complexité en O(1)). Pour l'instant, on va dire que la taille du tableau est toujours 16, on fera en sorte que la table de hachage s'agrandisse toute seule plus tard. Dans la classe HashTableSet, implanter le constructeur et les méthodes add et size. 

```java
package fr.uge.set;

import java.util.Objects;
import java.util.function.Consumer;

public final class HashTableSet {
    private static final int INITIAL_CAPACITY = 16;
    private Entry[] entries;
    private int size;

    public HashTableSet() {
        entries = new Entry[INITIAL_CAPACITY];
        size = 0;
    }

    public void add(Object value) {
        Objects.requireNonNull(value);
        int index = hash(value);
        
        for (var e = entries[index]; e != null; e = e.next) {
            if (value.equals(e.value)) {
                return; 
            }
        }
        
        entries[index] = new Entry(value, entries[index]);
        size++;
    }

    public int size() {
        return size;
    }

    private int hash(Object value) {
        return Math.abs(value.hashCode()) % entries.length;
    }
   
    private static record Entry(Object value, Entry next) {
    }
}
```


#### 3 - On cherche maintenant à implanter une méthode forEach qui prend en paramètre une fonction. La méthode forEach parcourt tous les éléments insérés et pour chaque élément, appelle la fonction prise en paramètre avec l'élément courant. Quelle doit être la signature de la functional interface prise en paramètre de la méthode forEach ? Quel est le nom de la classe du package java.util.function qui a une méthode ayant la même signature ? 

```java
public void forEach(Consumer<Object> action) {
    Objects.requireNonNull(action);
    for (var entry : entries) {
        for (var e = entry; e != null; e = e.next) {
            action.accept(e.value);
        }
    }
}		
```


#### 4 - On souhaite maintenant ajouter une méthode contains qui renvoie si un objet pris en paramètre est un élément de l'ensemble ou pas, sous forme d'un booléen. Expliquer pourquoi nous n'allons pas utiliser forEach pour implanter contains (Il y a deux raisons, une algorithmique et une spécifique à Java). Écrire la méthode contains. 

#### Car on ne peux sortir au cours d'un forEach.


#### 5 - 



```java
    public boolean contains(Object value) {
    	Objects.requireNonNull(value, "Value cannot be null");
    	
        var index = value.hashCode() & (entries.length -1);
        for (var entry = entries[index];entry != null;entry = entry.next) {
            if (value.equals(entry.value)) {
                return true;
            }
       
        }
        return false;
    }
```




#### 6- 


```java
package fr.uge.set;

import java.util.Objects;
import java.util.function.Consumer;

public final class HashTableSet {
    private static final int INITIAL_CAPACITY = 16;
    private Entry[] entries;
    private int size;

    public HashTableSet() {
        entries = new Entry[INITIAL_CAPACITY];
        size = 0;
    }

    public void add(Object value) {
        Objects.requireNonNull(value);
        if (size >= entries.length / 2) {
            resize();
        }
        addInternal(value);
    }

    private void addInternal(Object value) {
        int index = hash(value);
        for (var e = entries[index]; e != null; e = e.next) {
            if (value.equals(e.value)) {
                return;
            }
        }
        entries[index] = new Entry(value, entries[index]);
        size++;
    }

    private void resize() {
        Entry[] oldEntries = entries;
        entries = new Entry[oldEntries.length * 2];
        size = 0;
        for (Entry entry : oldEntries) {
            for (Entry e = entry; e != null; e = e.next) {
                addInternal(e.value);
            }
        }
    }

    public int size() {
        return size;
    }

    public void forEach(Consumer<? super Object> action) {
        Objects.requireNonNull(action);
        for (Entry entry : entries) {
            for (Entry e = entry; e != null; e = e.next) {
                action.accept(e.value);
            }
        }
    }

    public boolean contains(Object value) {
        Objects.requireNonNull(value, "Value cannot be null");
        int index = hash(value);
        for (var entry = entries[index]; entry != null; entry = entry.next) {
            if (value.equals(entry.value)) {
                return true;
            }
        }
        return false;
    }

    private int hash(Object value) {
        return value.hashCode() & (entries.length - 1);
    }

    private static record Entry(Object value, Entry next) {
    }
}
```