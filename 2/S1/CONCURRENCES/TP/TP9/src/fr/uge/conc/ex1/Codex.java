package fr.uge.conc.ex1;

import java.util.concurrent.ArrayBlockingQueue;



public class Codex {
	public static void main(String[] args) {
		var size = 10;
		var toDecodeQueue = new ArrayBlockingQueue<String>(size);
		var toArchiveQueue = new ArrayBlockingQueue<String>(size);

		var nbrDecodeThreads = 2;
		var nbrArchiveThreads = 1;
		var nbrAllThreads = nbrArchiveThreads + nbrDecodeThreads;

		for (var i = 0; i < nbrDecodeThreads; i++) {
			Thread.ofPlatform().name("Thread-" + i).start(() -> {
				for(;;) {
					try {
						var value = CodeAPI.receive();
						System.out.println(Thread.currentThread().getName() + " collects value: " + value);
						toDecodeQueue.put(value);
					} catch (InterruptedException e) {
						// return; // If we want to interrupt them
						throw new AssertionError(); // Ignore, i'm not supposed to be here
					}
				}
			});
		}
		
		for (var i = 0; i < nbrArchiveThreads; i++) {
			Thread.ofPlatform().name("Thread-" + i).start(() -> {
				for(;;) {
					try {
						var encodedValue = toDecodeQueue.take();
						System.out.println(Thread.currentThread().getName() + " take value: " + encodedValue);
						toArchiveQueue.put(CodeAPI.decode(encodedValue));
					} catch (InterruptedException e) {
						throw new AssertionError(); // Ignore, i'm not supposed to be here
					} catch (IllegalArgumentException e) {
						// do nothing
					}
				}
			});
		}
		
		for (var i = 0; i < nbrAllThreads; i++) {
			Thread.ofPlatform().name("Thread-" + i).start(() -> {
				for(;;) {
					try {
						var archivedValue = toArchiveQueue.take();
						System.out.println(Thread.currentThread().getName() + " take archived value: " + archivedValue);
						CodeAPI.archive(archivedValue);
					} catch (InterruptedException e) {
						throw new AssertionError(); // Ignore, i'm not supposed to be here
					}
				}
			});
		}
		
	}
}
