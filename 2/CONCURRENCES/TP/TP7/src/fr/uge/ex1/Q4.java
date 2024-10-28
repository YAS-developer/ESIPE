package fr.uge.ex1;

public class Q4 {
	public static void main(String[] args) {
	    // Crée et démarre le thread, en gardant sa référence
	    Thread sleepingThread = Thread.ofPlatform().start(() -> {
	        for (var i = 1;; i++) {
	            try {
	                Thread.sleep(1_000);
	                System.out.println("Thread slept " + i + " seconds.");
	            } catch (InterruptedException e) {
	                System.out.println("Thread " + Thread.currentThread().getName() + " was interrupted!");
	                return; // Sort de la boucle et termine le thread
	            }
	        }
	    });

	    try {
	        Thread.sleep(5_000); // Main thread attend 5 secondes
	        sleepingThread.interrupt(); // Interrompt le thread qui dort
	    } catch (InterruptedException e) {
	        // Gère l'interruption du thread principal si nécessaire
	    }
	}
}
