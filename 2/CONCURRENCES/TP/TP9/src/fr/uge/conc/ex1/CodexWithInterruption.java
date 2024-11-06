package fr.uge.conc.ex1;

import java.util.concurrent.ArrayBlockingQueue;

public class CodexWithInterruption {
    public static void main(String[] args) {
        var size = 10;
        var toDecodeQueue = new ArrayBlockingQueue<String>(size);
        var toArchiveQueue = new ArrayBlockingQueue<String>(size);

        var nbrDecodeThreads = 2;
        var nbrArchiveThreads = 1;
        var nbrAllThreads = nbrArchiveThreads + nbrDecodeThreads;

        // Thread pour recevoir les messages
        for (var i = 0; i < nbrDecodeThreads; i++) {
            Thread.ofPlatform().name("Receiver-Thread-" + i).start(() -> {
                for(;;) {
                    try {
                        var value = CodeAPI.receive();
                        System.out.println(Thread.currentThread().getName() + " collects value: " + value);
                        toDecodeQueue.put(value);
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName() + " interrupted while receiving.");
                        Thread.currentThread().interrupt(); // Interrompre proprement
                        return;
                    }
                }
            });
        }

        // Thread pour décoder les messages
        for (var i = 0; i < nbrArchiveThreads; i++) {
            Thread.ofPlatform().name("Decoder-Thread-" + i).start(() -> {
                for(;;) {
                    try {
                        var encodedValue = toDecodeQueue.take();
                        System.out.println(Thread.currentThread().getName() + " takes value: " + encodedValue);
                        var decodedValue = CodeAPI.decode(encodedValue);
                        toArchiveQueue.put(decodedValue);
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName() + " interrupted while decoding.");
                        Thread.currentThread().interrupt(); // Interrompre proprement
                        return;
                    } catch (IllegalArgumentException e) {
                        System.out.println(Thread.currentThread().getName() + " encountered a decoding error, stopping all threads.");
                        // Interrompre tous les threads
                        Thread.currentThread().getThreadGroup().interrupt(); // Arrêter tous les threads courant
                        return;
                    }
                }
            });
        }

        // Thread pour archiver les messages
        for (var i = 0; i < nbrAllThreads; i++) {
            Thread.ofPlatform().name("Archiver-Thread-" + i).start(() -> {
                for(;;) {
                    try {
                        var archivedValue = toArchiveQueue.take();
                        System.out.println(Thread.currentThread().getName() + " takes archived value: " + archivedValue);
                        CodeAPI.archive(archivedValue);
                    } catch (InterruptedException e) {
                        System.out.println(Thread.currentThread().getName() + " interrupted while archiving.");
                        Thread.currentThread().interrupt(); // Interrompre proprement
                        return;
                    }
                }
            });
        }
    }
}
