package fr.uge.conc.ex2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class BoundedSafeQueue<V> {
    private final Queue<V> queue = new LinkedList<>();
    private final int capacity;
    private final Object lock = new Object();

    public BoundedSafeQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
    }

    public void put(V value) throws InterruptedException {
        synchronized (lock) {
            while (queue.size() >= capacity) {
                lock.wait();  
            }
            queue.add(value);
            lock.notifyAll();  
        }
    }

    public V take() throws InterruptedException {
        synchronized (lock) {
            while (queue.isEmpty()) {
                lock.wait();  
            }
            V value = queue.poll();
            lock.notifyAll();  
            return value;
        }
    }

    public int size() {
        synchronized (lock) {
            return queue.size();
        }
    }
    
}