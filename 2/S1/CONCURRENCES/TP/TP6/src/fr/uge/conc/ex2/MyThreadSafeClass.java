package fr.uge.conc.ex2;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class MyThreadSafeClass {
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition condition = lock.newCondition();
	private long sum;
	private int count = 0;
	
	public void add(Long value) throws InterruptedException {
	    lock.lock();
	    try { 
	    	sum+=value;
	    	count++;
	    	condition.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	public long getSum() throws InterruptedException {
		lock.lock();
		try {
			while(count!=10) {
				condition.await();
			}
			return sum;
		} finally {
			lock.unlock();
		}
	}
	

	
	public static void main(String[] args) {
		int nbThreads = 5;
		var list = new MyThreadSafeClass();
		IntStream.range(0, nbThreads).forEach(i -> {
			Thread.ofPlatform().daemon().start(() -> {
					for (;;) {
					    long nb = 1_000_000_000L + ThreadLocalRandom.current().nextLong(1_000_000_000L);
					    if (isPrime(nb)) {
					        try {
								list.add(nb);
							} catch (InterruptedException e) {
								throw new AssertionError(e);
							}
					    }
					}
			});
		});
		try {
			System.out.println(list.getSum());
		} catch (InterruptedException e) {
			throw new AssertionError(e);
		}
		
		
	}
	
	public static boolean isPrime(long l) {
	    if (l <= 1) {
	        return false;
	    }
	    for (long i = 2L; i <= l / 2; i++) {
	        if (l % i == 0) {
	            return false;
	        }
	    }
	    return true;
	}
}
