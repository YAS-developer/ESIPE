package fr.uge.conc.ex2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class UnboundedSafeQueue<V> {
	private final Queue<V> queue = new LinkedList<>();
    private final Object lock = new Object();
	
    public void add(V value) {
        synchronized (lock) {
            queue.add(value);
            lock.notify();  // Réveille un thread en attente, s'il y en a
        }
    }
	
    public V take() throws InterruptedException {
        synchronized (lock) {
            while (queue.isEmpty()) {
                lock.wait();  
            }
            return queue.poll();
        }
    }
    
    
    public static void main(String[] args) {
        UnboundedSafeQueue<String> queue = new UnboundedSafeQueue<>();

        
        for (int i = 0; i < 3; i++) {
            Thread producerThread = new Thread(() -> {
                try {
                    while (true) {
                        String name = Thread.currentThread().getName();
                        queue.add(name);
                        System.out.println(name + " a ajouté un élément");
                        TimeUnit.SECONDS.sleep(2);
                    }
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " interrompu");
                }
            });
            producerThread.setName("Producer-" + i);
            producerThread.start();
        }

 
        try {
            while (true) {
                String item = queue.take();
                System.out.println("Consommé : " + item);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread principal interrompu");
        }
    }
}
