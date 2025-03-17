package fr.uge.concurrence;

import java.util.HashMap;
import java.util.Map;

public class ThePriceIsRight {
	private final int realPrice;
	private final int nbThread;
	private final Map<Thread, Integer> proposals = new HashMap<>();
	private final Object lock= new Object();
	
	public ThePriceIsRight(int realPrice, int nbThread) {
		if(realPrice < 0 || nbThread < 0) {
			throw new IllegalArgumentException("The article price or the nb thread must be positive");
		}
		this.realPrice = realPrice;
		this.nbThread = nbThread;
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
	
	public boolean propose(int price) {
			var currentThread = Thread.currentThread();
			
			synchronized (lock) {
				if(proposals.containsKey(currentThread) || proposals.size() < nbThread) {
					return false;
				}
				
				proposals.put(currentThread, price);
			
			
			try {
				while(!Thread.interrupted() && proposals.size() < nbThread) {
					lock.wait();
				}
				
				return findWinner() == currentThread;
			}
			catch (InterruptedException e) {
		        proposals.remove(currentThread);
		        if (!proposals.isEmpty()) {
		          lock.notifyAll();  
		        return false;
		      }
			
			}
		}
		return false;
	}
}
