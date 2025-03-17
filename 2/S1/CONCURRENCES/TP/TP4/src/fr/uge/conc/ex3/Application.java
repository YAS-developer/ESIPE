package fr.uge.conc.ex3;

import java.util.concurrent.ThreadLocalRandom;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        BlockingMaximum blockingMax = new BlockingMaximum();

        // Démarrer 4 threads
        for (int i = 0; i < 4; i++) {
            Thread t = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        int value = ThreadLocalRandom.current().nextInt(BlockingMaximum.MAX_VALUE);
                        System.out.println(Thread.currentThread().getName() + " propose " + value);
                        blockingMax.propose(value);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            t.setName("Thread-" + i);
            t.start();
        }

        // Thread principal
        blockingMax.waitAndGetMax();  // Attendre qu'un thread propose une valeur > 8000
        System.out.println("Un thread a tiré une valeur supérieure à 8 000");

        while (true) {
            Thread.sleep(1000);
            System.out.println("Maximum actuel : " + blockingMax.getMax());
        }
    }
}