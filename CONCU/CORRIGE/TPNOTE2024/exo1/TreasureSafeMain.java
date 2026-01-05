package fr.uge.concurrence.exo1;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TreasureSafeMain {
    private static void sleepAndForwardInterruption(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        var safe = new TreasureSafe(500);

        Thread.ofPlatform().start(() -> {
            while (true) {
                try {
                    Thread.sleep(100 + ThreadLocalRandom.current().nextInt(100));
                    var value = 1 + ThreadLocalRandom.current().nextInt(20);
                    safe.putTreasure(value);
                    System.out.println("deposit " + value);
                } catch (InterruptedException e) {
                    throw new AssertionError(e);
                }
            }
        });

        Thread.ofPlatform().start(() -> {
            while (true) {
                try {
                    Thread.sleep(100 + ThreadLocalRandom.current().nextInt(100));
                    System.out.println("get " + safe.takeTreasure());
                } catch (InterruptedException e) {
                    throw new AssertionError();
                }
            }
        });

		Thread.ofPlatform().start(() -> {
			while (!Thread.interrupted()) {
				sleepAndForwardInterruption(1000);
				var treasures = List.of(100, 50, 200, 75, 100, 25, 50, 30);
				System.out.println(Thread.currentThread().getName() + " tries to deposit " + treasures);
				try {
					safe.putManyTreasures(treasures);
				} catch (InterruptedException e) {
					throw new AssertionError();
				}
				System.out.println(Thread.currentThread().getName() + " out ");
			}
		});

		Thread.ofPlatform().start(() -> {
			while (!Thread.interrupted()) {
				sleepAndForwardInterruption(500);
				System.out.println(">>> incoming " + safe.incomingValue());
			}
		});
    }
}