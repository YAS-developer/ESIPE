package fr.uge.conc.ex1;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class BoundedSafeQueue<V> {
	  private final ArrayDeque<V> fifo = new ArrayDeque<>();
	  private final int capacity;
	  private final ReentrantLock lock = new ReentrantLock();
	  private final Condition condition = lock.newCondition();
	  private final Condition condition2 = lock.newCondition();
	  
	  public BoundedSafeQueue(int capacity) {
	    if (capacity <= 0) {
	      throw new IllegalArgumentException();
	    }
	    this.capacity = capacity;
	  }

	  public void put(V value) throws InterruptedException {
	    lock.lock();
	    try {
	    	while (fifo.size() == capacity) {
		        condition.await();
		      }
		      fifo.add(value);
		      condition2.signal();
		} finally {
			lock.unlock();
		}
	  }

	  public V take() throws InterruptedException {
	    lock.lock();
	    try {
	    	while (fifo.isEmpty()) {
		        condition2.await();
		      }
		      condition.signal();
		      return fifo.remove();
		} finally {
			lock.unlock();
		}
	  }

	public static void main(String[] args) {
		int nbThreads = 10;
		var file = new BoundedSafeQueue<String>(5);
		IntStream.range(0, nbThreads).forEach(i -> {
			Thread.ofPlatform().start(() -> {
				while(true) {
					try {
						Thread.sleep(2_000);
						file.put(Thread.currentThread().getName());
					} catch (InterruptedException e) {
						throw new AssertionError();
					}
				}
			});
		});

		for (;;) {
			try {
				System.out.println(file.take());
			} catch (InterruptedException e) {
				throw new AssertionError();
			}
		}
	}
	
	//3) on utilise notifyAll car on fait 2 wait donc on reveille tous les threads pour éviter qu'on se retrouve dans une configuratino où les threads
	//s'attendent mutuelement
}
