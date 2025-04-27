package fr.uge.concurrence.exo1;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;



class TreasureSafe {
	
	private final ArrayList<Integer> deque = new ArrayList(); 
	private final int total;
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition condition1 = lock.newCondition();
	private final Condition condition2 = lock.newCondition();
	private int waitSum;
	
	public TreasureSafe(int total) {
	
		this.total = total;
		waitSum=0;
		
	}
	
	public void putTreasure(int value) throws InterruptedException {
		lock.lock();
		try {
			while(true) {
				var tot = deque.stream().mapToInt(Integer::intValue).sum();
				if(tot+value < total) {
					break;
				}
				waitSum+=value;
				condition1.await();
				waitSum-=value;
			}
			deque.add(value);
			condition2.signal();
		}
		finally {
			lock.unlock();
		}
		
	}
	
	
	public int takeTreasure() throws InterruptedException {
		lock.lock();
		try {
			while(true) {
				if(!deque.isEmpty()) {
					break;
				}
				condition2.await();
			}
			var lastTreasure = deque.removeLast(); 
			condition1.signalAll();
			return lastTreasure;
		}
		finally {
			lock.unlock();
		}
	}
	
	
	int incomingValue() {
		lock.lock();
		try {
			return waitSum;
		}
		finally {
			lock.unlock();
		}
	}
	
	public void putManyTreasures(List<Integer> values) throws InterruptedException{
		lock.lock();
		try {
			values.forEach(value ->{
				try {
					putTreasure(value);
				} catch (InterruptedException e) {
					return;
				}
			});
		}
		finally {
			lock.unlock();
		}
				
	}
	
	
	
	
	
	
	
	
	
	
}
