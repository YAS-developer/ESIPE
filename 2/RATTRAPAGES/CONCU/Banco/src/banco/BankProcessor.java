package banco;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import banco.Banco.Bank;
import banco.Banco.WireTransfer;


public class BankProcessor {
	
	
	
	
public static void main(String[] args) {	
    	var wiretransferQueue = new ArrayBlockingQueue<WireTransfer>(50);	
    	
		ArrayBlockingQueue<WireTransfer>[] queues = new ArrayBlockingQueue[6];
    	for (int i = 0; i < queues.length; i++) {
    	    queues[i] = new ArrayBlockingQueue<WireTransfer>(10);
    	}
    	
    	var threads = new ArrayList<Thread>();
    	
    	
    	for(int i=0; i<3; i++) {
    		var t = Thread.ofPlatform().daemon().start(()->{
    			for(;!Thread.interrupted();) {
    				try {
    					var wireTransfer = Banco.retrieveWireTransfer();
    					wiretransferQueue.put(wireTransfer);
    					
    				} catch (InterruptedException e) {
    					throw new AssertionError(e);
    				}
    			}
    		});
    		threads.add(t);
    	}
    	
    	
    	
    	for(int i=0; i<2; i++) {
    		var t = Thread.ofPlatform().daemon().start(()->{
    			for(;!Thread.interrupted();) {
    				
					try {
						var wireTransfer = wiretransferQueue.take();
						if(Banco.isSuspect(wireTransfer)) {
							System.out.println("Rejecting the suspicious " +wireTransfer);
							continue;
						}
						
						System.out.println(wireTransfer.toString());
						switch (wireTransfer.bank()) {
						
							case Bank.PICSOUBANK -> {
								System.out.println(wireTransfer);
								queues[0].put(wireTransfer);
							}
							

							case Bank.DESSOUSSOUS -> {
								System.out.println(wireTransfer);
								queues[1].put(wireTransfer);
							}
	
							case Bank.THUNE -> {
								System.out.println(wireTransfer);
								queues[2].put(wireTransfer);
							}
							
							case Bank.OSEILLE -> {
								System.out.println(wireTransfer);
								queues[3].put(wireTransfer);
							}
							
							case Bank.BLE -> {
								System.out.println(wireTransfer);
								queues[4].put(wireTransfer);
							}
								
							case Bank.MASSETHUNE -> {
								System.out.println(wireTransfer);
								queues[5].put(wireTransfer);
							}
							
							default -> throw new IllegalArgumentException("Unexpected value: " + wireTransfer.bank());
							
						}
						
					} catch (InterruptedException e) {
						throw new AssertionError(e);
					}
    			}
    		});
    		threads.add(t);
    	}
    	
    	for(int i=0; i<6; i++) {
    		var nBank = i;
			var t = Thread.ofPlatform().daemon().start(()->{
				var sum = 0;
				for(;!Thread.interrupted();) {
					try {
						var wireTransfer = queues[nBank].take();
						sum += wireTransfer.amount();
						System.out.println(wireTransfer.bank()+ "has "+ sum +" after "+ wireTransfer.toString());
					} catch (InterruptedException e) {
						throw new AssertionError(e);
					}
				}
			});
			threads.add(t);
		}
    	
    	
    	
    	threads.forEach(Thread::interrupt);
	}
}
