package fr.uge.concurrence;

import java.util.ArrayList;
import java.util.List;

public class CyclicExchanger<T> {
  private final int nbparticipants;
  private int index;
  private final List<T> list = new ArrayList<T>();
  private final Object lock = new Object();

  public CyclicExchanger(int nbparticipants) {
    this.nbparticipants = nbparticipants;
  }

  public T exchange(T value) throws InterruptedException {
    synchronized (lock){
      list.add(value);
      int current = index++;
      if (list.size() == nbparticipants)
        lock.notifyAll();
      else{
        while (list.size() < nbparticipants){
          lock.wait();
        }
      }
      if(current + 1 < nbparticipants)
        return list.get(current +1);
      else if (current + 1 == nbparticipants)
        return list.getFirst();
      else{
        throw new IllegalStateException("bad");
      }
    }
  }

  public static void main(String[] args) {
    CyclicExchanger<Integer> test= new CyclicExchanger<>(5);
    for (int i= 0; i < 5; i++){
      int finalI = i;
      Thread.ofPlatform().start(() -> {
        try {
          Thread.sleep(finalI * 1000);
          System.out.println(Thread.currentThread().getName() + " got " + test.exchange(finalI));
        } catch (InterruptedException e) {
          throw new AssertionError(e);
        }
      });
    }
  }
}
