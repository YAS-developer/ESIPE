package fr.uge.banco;

import java.util.concurrent.ArrayBlockingQueue;

public class BankProcessor {
	
	public static void main(String [] args) {
		var numberOfThread = 0;
		var threads = new Thread[Banco.Bank.values().length + 5];
		var transfers = new ArrayBlockingQueue<Banco.WireTransfer>(100);
		@SuppressWarnings("unchecked")
		var transfersPerBank = (ArrayBlockingQueue<Banco.WireTransfer>[]) new ArrayBlockingQueue<?>[Banco.Bank.values().length];
		for(var bank : Banco.Bank.values()) {
			transfersPerBank[bank.ordinal()] = new ArrayBlockingQueue<Banco.WireTransfer>(100);
		}
		
		for(var i = 0 ; i < 3 ; i++) {
			threads[numberOfThread++] = Thread.ofPlatform().start(() -> {
				for(;;) {
					try {
						var transfer = Banco.retrieveWireTransfer();
						System.out.println(transfer);
						transfers.put(transfer);
					} catch (InterruptedException e) {
						throw new AssertionError(e);
					}
				}
			});
		}
		
		for(var i = 0 ; i < 2 ; i++) {
			threads[numberOfThread++] = Thread.ofPlatform().start(() -> {
				for(;;) {
					try {
						var transfer = transfers.take();
						if(Banco.isSuspect(transfer)) {
							System.out.println("Rejecting the suspicious " + transfer);
						}
						else {
							transfersPerBank[transfer.bank().ordinal()].put(transfer);
						}
					} catch (InterruptedException e) {
						throw new AssertionError(e);
					}
				}
			});
		}
		
		for(var bank : Banco.Bank.values()) {
			threads[numberOfThread++] = Thread.ofPlatform().start(() -> {
					var sum = 0;
					for(;;) {
						try {
							var transfer = transfersPerBank[bank.ordinal()].take();
							sum += transfer.amount();
							System.out.println(bank.name() + " has " + sum + " after " + transfer);
						} catch (InterruptedException e) {
							throw new AssertionError(e);
						}
					}
			});
		}
		
	}
		
}