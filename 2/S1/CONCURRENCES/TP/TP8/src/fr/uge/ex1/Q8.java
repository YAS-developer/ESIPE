package fr.uge.ex1;

import java.util.OptionalLong;
import java.util.concurrent.ThreadLocalRandom;

public class Q8 {
	  public static boolean isPrime(long candidate) throws InterruptedException {
	    if (candidate <= 1) {
	      return false;
	    }
	    for (var i = 2; i <= Math.sqrt(candidate); i++) {
	      if (Thread.interrupted()) {
	        throw new InterruptedException();  // Propage l'interruption
	      }
	      if (candidate % i == 0) {
	        return false;
	      }
	    }
	    return true;
	  }

	  public static OptionalLong findPrime() throws InterruptedException {
	    var generator = ThreadLocalRandom.current();
	    while (!Thread.interrupted()) {
	      var candidate = generator.nextLong();
	      if (isPrime(candidate)) {
	        return OptionalLong.of(candidate);
	      }
	    }
	    throw new InterruptedException();  // Propage l'interruption
	  }

	  public static void main(String[] args) throws InterruptedException {
	    Thread primeThread = Thread.ofPlatform().start(() -> {
	      try {
	        var result = findPrime();
	        System.out.println("Found a random prime : " + result.orElseThrow());
	      } catch (InterruptedException e) {
	        // Gestion propre de l'interruption
	        System.out.println("Search was interrupted");
	      }
	    });

	    Thread.sleep(3000);
	    primeThread.interrupt();
	    System.out.println("STOP");
	  }
	}


/*


Avantages de cette approche :

Plus idiomatique en Java (utilisation des exceptions pour les interruptions)
Propagation claire de l'interruption via le système d'exceptions
Force les appelants à gérer explicitement l'interruption (InterruptedException est checked)
Pas besoin de valeur spéciale (OptionalLong.empty) pour signaler l'interruption
Maintient le drapeau d'interruption dans un état cohérent

C'est la façon la plus propre et la plus Java-esque de gérer les interruptions.





*/