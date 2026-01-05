package banco;

import java.lang.invoke.VarHandle;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;



public class Banco {

    /* All the banks supported by the API */
    public enum Bank { PICSOUBANK, DESSOUSSOUS, THUNE, OSEILLE, BLE, MASSETHUNE};

    /* Record representing the information of a wireTransfer */
    public record WireTransfer(int bankAccount, Bank bank, int amount){
        public WireTransfer {
            Objects.requireNonNull(bank);
        }
    }

    /* Method simulating the reception of wireTransfer from the internet */
    public static WireTransfer retrieveWireTransfer() throws InterruptedException {
        var rng = ThreadLocalRandom.current();
        var time = rng.nextInt(1,1_000);
        Thread.sleep(time);
        var bankAccount = rng.nextInt(1,1_000_000);
        var bank = Bank.values()[rng.nextInt(0,Bank.values().length)];
        var amount = rng.nextInt(-10_000,10_000);
        return new WireTransfer(bankAccount,bank,amount);

    }

    /* Method to determine if a wireTransfer is suspect */
    public static boolean isSuspect(WireTransfer wireTransfer) throws InterruptedException {
        Objects.requireNonNull(wireTransfer);
        var rng = ThreadLocalRandom.current();
        var time = rng.nextInt(1,1_000);
        Thread.sleep(time);
        return wireTransfer.hashCode()%10 == 0;
    }
    
    
    
    
    public static void main(String[] args) {
		
    	var wiretransferQueue = new ArrayBlockingQueue<WireTransfer>(10);
    	/*var PICSOUBANKQueue = new ArrayBlockingQueue<String>(10);
    	var PICSOUBANKQueue = new ArrayBlockingQueue<String>(10);
    	var PICSOUBANKQueue = new ArrayBlockingQueue<String>(10);
    	var PICSOUBANKQueue = new ArrayBlockingQueue<String>(10);*/
    	
    	@SuppressWarnings("unchecked")
		ArrayBlockingQueue<WireTransfer>[] queues = new ArrayBlockingQueue[6];
    	for (int i = 0; i < queues.length; i++) {
    	    queues[i] = new ArrayBlockingQueue<WireTransfer>(10);
    	}
    	
    	
    	for(int i=0; i<3; i++) {
    		Thread.ofPlatform().daemon().start(()->{
    			for(;!Thread.interrupted();) {
    				try {
    					var wireTransfer = retrieveWireTransfer();
    					wiretransferQueue.put(wireTransfer);
    					
    				} catch (InterruptedException e) {
    					return;
    				}
    			}
    		});
    	}
    	
    	
    	
    	for(int i=0; i<2; i++) {
    		Thread.ofPlatform().daemon().start(()->{
    			for(;!Thread.interrupted();) {
    				
					try {
						var wireTransfer = wiretransferQueue.take();
						if(isSuspect(wireTransfer)) {
							System.out.println("Rejecting the suspicious " +wireTransfer.toString());
							continue;
						}
						
						System.out.println(wireTransfer.toString());
						switch (wireTransfer.bank()) {
						
							case PICSOUBANK -> {
								System.out.println(wireTransfer.toString());
								queues[0].put(wireTransfer);
							}
							

							case DESSOUSSOUS -> {
								System.out.println(wireTransfer.toString());
								queues[1].put(wireTransfer);
							}
	
							case THUNE -> {
								System.out.println(wireTransfer.toString());
								queues[2].put(wireTransfer);
							}
							
							case OSEILLE -> {
								System.out.println(wireTransfer.toString());
								queues[3].put(wireTransfer);
							}
							
							case BLE -> {
								System.out.println(wireTransfer.toString());
								queues[4].put(wireTransfer);
							}
								
							case MASSETHUNE -> {
								System.out.println(wireTransfer.toString());
								queues[5].put(wireTransfer);
							}
							
							default -> throw new IllegalArgumentException("Unexpected value: " + wireTransfer.bank);
							
						}
						
					} catch (InterruptedException e) {
						return;
					}
    			}
    		});
    	}
    	
    	for(int i=0; i<6; i++) {
    		var nBank = i;
			Thread.ofPlatform().daemon().start(()->{
				for(;!Thread.interrupted();) {
					try {
						var wireTransfer = queues[nBank].take();
						System.out.println(wireTransfer.bank()+ "has "+ wireTransfer.amount() +" after "+ wireTransfer.toString());
					} catch (InterruptedException e) {
						return;
					}
				}
			});
		}
    	
    	

	}
}
