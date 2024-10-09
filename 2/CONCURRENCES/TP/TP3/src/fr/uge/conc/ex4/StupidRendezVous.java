package fr.uge.conc.ex4;

import java.util.Objects;

public class StupidRendezVous<V> {
  private V value;

  private final Object lock = new Object();

  public void set(V value) {
      Objects.requireNonNull(value);
      synchronized (lock) {
          this.value = value;
          
      }
  }

  public V get() throws InterruptedException {
      synchronized (lock) {
          while (value == null) {
           
          }
          return value;
      }
  }
}
