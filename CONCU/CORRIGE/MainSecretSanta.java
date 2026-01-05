package fr.uge.concurrence.exo1;

import java.util.List;

public class MainSecretSanta {
  public static void main(String[] args) {
    var secretSanta = new SecretSanta(5, List.of(0, 1, 3, 4, 2));
    int[] waitingTimes = {500, 1000, 1500, 2000, 2500};
    for (var id = 0; id < waitingTimes.length; id++) {
      var waitingTime = waitingTimes[id];
      Thread.ofPlatform().name("Thread" + id).start(() -> {
        try {
          Thread.sleep(waitingTime);
          var currentThreadName = Thread.currentThread().getName();
          System.out.println(currentThreadName + " received " + secretSanta.submit(currentThreadName));
        } catch (InterruptedException e) {
          throw new AssertionError(e);
        }
      });

      int[] observedSized = {1,2,3,4,5};
      var observers = new Thread[observedSized.length];
      for (var id2 = 0; id2 < observedSized.length; id2++) {
        var observedSize = observedSized[id2];
        observers[id2] = Thread.ofPlatform().name("Observer " + id2).start(() -> {
          var observerName = Thread.currentThread().getName();
          try {
            System.out.println(observerName + " observed " + secretSanta.observe(observedSize));
          } catch (InterruptedException e) {
            System.out.println(observerName + " did not observe any thing as a InterruptedException was thrown");
          }
        });

      }
    }
  }
}
