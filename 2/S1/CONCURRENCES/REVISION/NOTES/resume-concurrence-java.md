# Résumé des cours de concurrence en Java

## 1. Problèmes liés à la concurrence

Les principaux problèmes liés à la concurrence incluent :
- La non-atomicité des instructions
- L'interruption des threads par le scheduler
- Les accès concurrents à la mémoire partagée
- Les optimisations du compilateur/JIT qui peuvent réordonner le code

Exemple de problème de non-atomicité :

```java
public class Counter {
    private int count = 0;

    public void increment() {
        count++; // Cette opération n'est pas atomique
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) counter.increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) counter.increment();
        });
        
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        
        System.out.println("Count: " + counter.getCount()); // Peut être moins que 2000
    }
}
```

## 2. Synchronisation et classes thread-safe

- Utilisation de `synchronized` pour créer des sections critiques
- Importance d'avoir un objet lock privé et final
- Concept de classe thread-safe
- Nécessité de synchroniser toutes les méthodes qui accèdent aux données partagées

Exemple de classe Counter thread-safe :

```java
public class ThreadSafeCounter {
    private int count = 0;
    private final Object lock = new Object();

    public void increment() {
        synchronized(lock) {
            count++;
        }
    }

    public int getCount() {
        synchronized(lock) {
            return count;
        }
    }
}
```

## 3. Signaux et communication inter-threads

- Utilisation de `wait()` et `notify()/notifyAll()` pour la communication
- Importance d'utiliser `wait()` dans une boucle while pour éviter les spurious wakeups
- Nécessité de toujours associer un booléen à la condition d'attente

Exemple de communication inter-threads :

```java
public class SharedResource {
    private boolean isReady = false;
    private final Object lock = new Object();

    public void prepare() {
        synchronized(lock) {
            isReady = true;
            lock.notify();
        }
    }

    public void use() throws InterruptedException {
        synchronized(lock) {
            while (!isReady) {
                lock.wait();
            }
            System.out.println("Resource is ready for use!");
        }
    }
}
```

## 4. Deadlocks et problèmes de publication

- Risques d'interblocage avec plusieurs verrous
- Toujours prendre les verrous dans le même ordre
- Problèmes potentiels lors de la publication d'objets partiellement construits

Exemple de situation pouvant mener à un deadlock :

```java
public class DeadlockRisk {
    private final Object resource1 = new Object();
    private final Object resource2 = new Object();

    public void method1() {
        synchronized(resource1) {
            System.out.println("Method 1 has resource 1");
            synchronized(resource2) {
                System.out.println("Method 1 has resource 2");
            }
        }
    }

    public void method2() {
        synchronized(resource2) {
            System.out.println("Method 2 has resource 2");
            synchronized(resource1) {
                System.out.println("Method 2 has resource 1");
            }
        }
    }
}
```

Pour éviter le deadlock, il faut toujours acquérir les verrous dans le même ordre dans toutes les méthodes.

## 5. ReentrantLock

- Alternative à `synchronized` offrant plus de flexibilité
- Nécessité d'utiliser un bloc try-finally pour s'assurer de relâcher le verrou
- Possibilité d'utiliser plusieurs conditions sur un même verrou

Exemple d'utilisation de ReentrantLock :

```java
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class BoundedBuffer<E> {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    private final E[] items;
    private int putPtr, takePtr, count;

    public BoundedBuffer(int capacity) {
        items = (E[]) new Object[capacity];
    }

    public void put(E x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)
                notFull.await();
            items[putPtr] = x;
            if (++putPtr == items.length) putPtr = 0;
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await();
            E x = items[takePtr];
            if (++takePtr == items.length) takePtr = 0;
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }
}
```

Ce résumé couvre les principaux concepts abordés dans les cours de concurrence en Java, avec des exemples de code pour illustrer chaque point important.
