package fr.uge.conc.ex3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Ex3 {
	static class CurrentMaximum {
        private static final int MAX_VALUE = 10_000;
        private int max = Integer.MIN_VALUE;
        private Thread maxThread;

        public synchronized void propose(int value, Thread thread) {
            if (value > max) {
                max = value;
                maxThread = thread;
            }
        }

        public synchronized int getMax() {
            return max;
        }

        public synchronized String getMaxWithThread() {
            return max + " (proposé par " + maxThread.getName() + ")";
        }
    }

    private static final int NUM_THREADS = 4;

    public static void main(String[] args) throws InterruptedException {
        CurrentMaximum currentMaximum = new CurrentMaximum();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < NUM_THREADS; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    int value = ThreadLocalRandom.current().nextInt(CurrentMaximum.MAX_VALUE);
                    System.out.println(Thread.currentThread() + " propose " + value);
                    currentMaximum.propose(value, Thread.currentThread());
                }
            });
            threads.add(t);
            t.start();
        }

        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            System.out.println("Max courant : " + currentMaximum.getMaxWithThread());
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Max final : " + currentMaximum.getMaxWithThread());
    }
}








/* 
 
 Pourquoi une classe thread-safe ?
Nous avons besoin d'une classe thread-safe car plusieurs threads accèderont et modifieront simultanément la valeur maximale. 
Sans synchronisation, nous pourrions avoir des problèmes de concurrence.
 
 
 
 
 
 
 
 
 
 
 
 */