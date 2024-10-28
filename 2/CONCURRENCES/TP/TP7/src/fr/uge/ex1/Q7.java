package fr.uge.ex1;

import java.util.OptionalLong;
import java.util.concurrent.ThreadLocalRandom;

public class Q7 {
	 public static boolean isPrime(long candidate) {
	   if (candidate <= 1) {
	     return false;
	   }
	   for (var i = 2; i <= Math.sqrt(candidate); i++) {
	     if (Thread.interrupted()) {  // Vérifie interruption dans la boucle interne
	       return false;  // Force l'arrêt du calcul
	     }
	     if (candidate % i == 0) {
	       return false;
	     }
	   }
	   return true;
	 }

	 public static OptionalLong findPrime() {
	   var generator = ThreadLocalRandom.current();
	   while (!Thread.interrupted()) {
	     var candidate = generator.nextLong();
	     if (isPrime(candidate)) {  // Si interrompu pendant isPrime(), retournera false
	       return OptionalLong.of(candidate);
	     }
	   }
	   return OptionalLong.empty();
	 }

	 public static void main(String[] args) throws InterruptedException {
	   Thread primeThread = Thread.ofPlatform().start(() -> {
	     var result = findPrime();
	     if (result.isPresent()) {
	       System.out.println("Found a random prime : " + result.getAsLong());
	     }
	   });

	   Thread.sleep(3000);
	   primeThread.interrupt();
	   System.out.println("STOP");
	 }
	}



/*

Modifications clés :

Ajout d'un test Thread.interrupted() dans la boucle de isPrime()
Si interrompu dans isPrime(), on retourne false pour sortir rapidement
Ce false fait que findPrime() continuera sa boucle et vérifiera à nouveau l'interruption

Cette version permet une interruption plus rapide car on ne finit pas le test de primalité en cours.






*/