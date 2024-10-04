package fr.uge.conc;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



public class Ex3 {
	class CurrentMaximum {
	    private int max = Integer.MIN_VALUE;

	    public synchronized void propose(int value) {
	        max = Math.max(max, value);
	    }

	    public synchronized int getMax() {
	        return max;
	    }
	}
	
    private static final int MAX_VALUE = 1000;
    private static final int NUM_THREADS = 4;

    public static void main(String[] args) {
        CurrentMaximum currentMax = new CurrentMaximum();
        var executor = Executors.newFixedThreadPool(NUM_THREADS + 1);

        // Threads proposant des valeurs
        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        int value = (int) (Math.random() * MAX_VALUE);
                        currentMax.propose(value);
                        System.out.println("Proposed: " + value);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        // Thread affichant le maximum courant
        executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("Current max: " + currentMax.getMax());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Laisser le programme s'exécuter pendant un certain temps
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdownNow();
    }
}