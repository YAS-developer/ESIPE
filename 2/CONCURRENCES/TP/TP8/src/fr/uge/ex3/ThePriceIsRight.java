package fr.uge.ex3;

import java.util.*;

public class ThePriceIsRight {
  private final int realPrice;
  private final int nbParticipants;
  private final Map<Thread, Integer> proposals = new HashMap<>();
  private final Object lock = new Object();

  public ThePriceIsRight(int realPrice, int nbParticipants) {
    if (nbParticipants <= 0) {
      throw new IllegalArgumentException("nbParticipants must be positive");
    }
    this.realPrice = realPrice;
    this.nbParticipants = nbParticipants;
  }

  private int distance(int price) {
    return Math.abs(price - realPrice);
  }

  private Thread findWinner() {
    return proposals.entrySet().stream()
        .min(Map.Entry.<Thread, Integer>comparingByValue((price1, price2) -> {
          var dist1 = distance(price1);
          var dist2 = distance(price2);
          if (dist1 != dist2) {
            return Integer.compare(dist1, dist2);
          }
          // En cas d'égalité, on prend le plus ancien (ordre d'insertion dans la Map)
          return 1;
        }))
        .map(Map.Entry::getKey)
        .orElse(null);
  }

  public boolean propose(int price) throws InterruptedException {
    var currentThread = Thread.currentThread();
    
    synchronized(lock) {
      // Si le thread a déjà proposé ou si on a déjà assez de participants
      if (proposals.containsKey(currentThread) || proposals.size() >= nbParticipants) {
        return false;
      }

      // Enregistre la proposition
      proposals.put(currentThread, price);

      try {
        // Attend que tous les participants aient proposé
        while (!Thread.interrupted() && proposals.size() < nbParticipants) {
          lock.wait();
        }

        // Si le thread a été interrompu
        if (Thread.interrupted()) {
          proposals.remove(currentThread);
          if (!proposals.isEmpty()) {
            lock.notifyAll();  // Débloque les autres threads
          }
          throw new InterruptedException();
        }

        return findWinner() == currentThread;

      } catch (InterruptedException e) {
        proposals.remove(currentThread);
        if (!proposals.isEmpty()) {
          lock.notifyAll();  // Débloque les autres threads
        }
        throw e;
      }
    }
  }
}