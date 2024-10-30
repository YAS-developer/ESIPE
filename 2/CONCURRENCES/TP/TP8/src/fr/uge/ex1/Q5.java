package fr.uge.ex1;

import java.util.OptionalLong;
import java.util.concurrent.ThreadLocalRandom;

public class Q5 {
	public static boolean isPrime(long candidate) {
	    if (candidate <= 1) {
	        return false;
	    }
	    for (var i = 2; i <= Math.sqrt(candidate); i++) {
	        if (candidate % i == 0) {
	            return false;
	        }
	    }
	    return true;
	}

	public static OptionalLong findPrime() {
	    var generator = ThreadLocalRandom.current();
	    while (!Thread.interrupted()) {  // Vérifie le drapeau d'interruption
	        var candidate = generator.nextLong();
	        if (isPrime(candidate)) {
	            return OptionalLong.of(candidate);
	        }
	    }
	    return OptionalLong.empty();  // Retourne vide si interrompu
	}

	public static void main(String[] args) throws InterruptedException {
	    Thread primeThread = Thread.ofPlatform().start(() -> {
	        var result = findPrime();
	        if (result.isPresent()) {
	            System.out.println("Found a random prime : " + result.getAsLong());
	        }
	    });

	    Thread.sleep(3000);  // Main attend 3 secondes
	    primeThread.interrupt();  // Interrompt le thread de calcul
	    System.out.println("STOP");
	}

}


/*
Les modifications :

Dans findPrime() :

Remplacé la boucle infinie for(;;) par while(!Thread.interrupted())
Ajout d'un return OptionalLong.empty() si interrompu


Dans main() :

Garde la référence du thread créé
Attend 3 secondes
Interrompt le thread de calcul
Affiche "STOP"


Dans le lambda :

Vérifie si un nombre a été trouvé avant d'afficher


Note : Thread.interrupted() vérifie ET efface le drapeau d'interruption, 
ce qui est approprié ici car on arrête le calcul de toute façon. 
*/
