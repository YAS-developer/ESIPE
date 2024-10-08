package fr.uge.conc.ex1;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class HelloListBug {
	public static void main(String[] args) throws InterruptedException {
	    var nbThreads = 4;
	    var threads = new Thread[nbThreads]; 
	    
	    var list = new ArrayList<Integer>(5_000 * nbThreads);
	    final var lock = new Object();

	    IntStream.range(0, nbThreads).forEach(j -> {
	      Runnable runnable = () -> {
	        for (var i = 0; i < 5_000; i++) {
	        	synchronized(lock) { // Bloc synchronisé pour l'ajout d'éléments
	        		 list.add(i);
	        	}
	        }
	      };

	      threads[j] = Thread.ofPlatform().start(runnable);
	    });

	    for (var thread : threads) {
	      thread.join();
	    }

	    System.out.println("taille de la liste:" + list.size());
	  }	
}



/*
 Synchronisation :

La synchronisation se produit uniquement au niveau du bloc synchronized(lock).
Lorsqu'un thread atteint ce bloc, il tente d'acquérir le verrou (lock).
Si le verrou est disponible, le thread l'acquiert et exécute le code à l'intérieur du bloc.
Si le verrou est déjà détenu par un autre thread, le thread actuel se met en attente jusqu'à ce que le verrou soit libéré.
 
 */