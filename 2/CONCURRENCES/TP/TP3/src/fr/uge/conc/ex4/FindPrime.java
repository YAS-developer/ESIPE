package fr.uge.conc.ex4;


// EX4

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class FindPrime {
  public static final long BIG_LONG = 10_000_000_000L;

  public static boolean isPrime(long l) {
    if (l <= 1)
      return false;
    for (long i = 2L; i <= l / 2; i++) {
      if (l % i == 0) {
        return false;
      }
    }
    return true;
  }
  
  public static void main(String[] args) {
    var nbThreads = 4;
    var rdv = new StupidRendezVous<Long>();
    
    IntStream.range(0, nbThreads).forEach(i -> {
      Thread.ofPlatform().daemon().start(() -> {
        for (;;) {
          var nb = BIG_LONG + ThreadLocalRandom.current().nextLong(BIG_LONG);
          if (isPrime(nb)) {
            rdv.set(nb);
            System.out.println("A prime number was found in thread " + i);
            return;
          }
        }
      });
    });
    
    try {
      System.out.println("I found a large prime number : " + rdv.get());
    } catch (InterruptedException e) {
      throw new AssertionError();
    }
  }
}





/*
StupidRendezVous : problèmes de concurrence

Avec Thread.sleep(1) :

Fonctionne mais inefficace
Sleep masque partiellement les problèmes


Sans Thread.sleep(1) :

Risque de blocage ou surconsommation CPU
Problèmes de concurrence révélés



Bugs principaux :

Visibilité : value non volatile
Busy-waiting sans sleep
Race condition sur value == null
Absence de notification entre threads

Solution :

Synchronisation avec synchronized
Utilisation de wait() et notifyAll()
Vérification de value dans bloc synchronisé

Résultat : Code thread-safe et efficace
 */