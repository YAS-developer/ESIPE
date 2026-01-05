package fr.uge.concurrence.exo1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SecretSanta {
  private final int nbParticipants;
  private final List<Integer> order;
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition allSubmitted = lock.newCondition();
  private final Condition enoughObserved = lock.newCondition();

  private final List<String> names = new ArrayList<>();
  private int submittedCount = 0;

  private boolean observationStarted = false;
  private boolean observationFailed = false;
  private boolean observationDone = false;

  public SecretSanta(int nbParticipants, List<Integer> order) {
    this.nbParticipants = nbParticipants;
    this.order = List.copyOf(order);
  }

  public String submit(String value) throws InterruptedException {
    final int myIndex;
    lock.lock();
    try {
      if (submittedCount >= nbParticipants) {
        throw new IllegalStateException("All participants already submitted");
      }

      myIndex = submittedCount;
      names.add(value);
      submittedCount++;

      enoughObserved.signalAll();

      while (submittedCount < nbParticipants) {
        allSubmitted.await();
      }

      var targetIndex = order.get(myIndex);
      allSubmitted.signalAll();
      return names.get(targetIndex);
    } finally {
      lock.unlock();
    }
  }

  public List<String> observe(int n) throws InterruptedException {
    lock.lock();
    try {
      if (observationStarted) {
        if (observationDone) {
          throw new IllegalStateException("Observation already completed");
        }
        if (observationFailed) {
          throw new IllegalStateException("Observation failed due to interruption");
        }
      } else {
        observationStarted = true;
      }

      while (submittedCount < n) {
        if (observationFailed) {
          throw new InterruptedException("Observation interrupted by another thread");
        }
        try {
          enoughObserved.await();
        } catch (InterruptedException e) {
          observationFailed = true;
          enoughObserved.signalAll();
          throw new InterruptedException("Observation interrupted");
        }
      }

      observationDone = true;
      return new ArrayList<>(names.subList(0, n));
    } finally {
      lock.unlock();
    }
  }
}
