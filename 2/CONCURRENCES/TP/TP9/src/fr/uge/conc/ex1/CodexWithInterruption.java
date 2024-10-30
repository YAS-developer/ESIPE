package fr.uge.conc.ex1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class CodexWithInterruption {
  private static final int CODED_QUEUE_CAPACITY = 100;
  private static final int DECODED_QUEUE_CAPACITY = 100;
  
  public static void main(String[] args) throws InterruptedException {
    BlockingQueue<String> codedMessages = new ArrayBlockingQueue<>(CODED_QUEUE_CAPACITY);
    BlockingQueue<String> decodedMessages = new ArrayBlockingQueue<>(DECODED_QUEUE_CAPACITY);
    AtomicBoolean shouldStop = new AtomicBoolean(false);
    Thread[] allThreads = new Thread[6]; // Pour stocker tous les threads
    int threadIndex = 0;
    
    // 3 threads pour recevoir les messages
    for (int i = 0; i < 3; i++) {
      final int id = i;
      Thread receiverThread = new Thread(() -> {
        try {
          while (!Thread.interrupted() && !shouldStop.get()) {
            String codedMsg = CodeAPI.receive();
            System.out.println("Receiver " + id + " received: " + codedMsg);
            codedMessages.put(codedMsg);
          }
        } catch (InterruptedException e) {
          return;
        }
      });
      allThreads[threadIndex++] = receiverThread;
      receiverThread.start();
    }
    
    // 2 threads pour décoder les messages
    for (int i = 0; i < 2; i++) {
      final int id = i;
      Thread decoderThread = new Thread(() -> {
        try {
          while (!Thread.interrupted() && !shouldStop.get()) {
            String codedMsg = codedMessages.take();
            System.out.println("Decoder " + id + " processing: " + codedMsg);
            try {
              String decodedMsg = CodeAPI.decode(codedMsg);
              System.out.println("Decoder " + id + " decoded: " + decodedMsg);
              decodedMessages.put(decodedMsg);
            } catch (IllegalArgumentException e) {
              System.out.println("Decoder " + id + " encountered an error. Stopping all threads.");
              shouldStop.set(true);
              // Interrompre tous les threads
              for (Thread thread : allThreads) {
                if (thread != null) {
                  thread.interrupt();
                }
              }
              return;
            }
          }
        } catch (InterruptedException e) {
          return;
        }
      });
      allThreads[threadIndex++] = decoderThread;
      decoderThread.start();
    }
    
    // 1 thread pour archiver les messages
    Thread archiverThread = new Thread(() -> {
      try {
        while (!Thread.interrupted() && !shouldStop.get()) {
          String decodedMsg = decodedMessages.take();
          System.out.println("Archiver processing: " + decodedMsg);
          CodeAPI.archive(decodedMsg);
        }
      } catch (InterruptedException e) {
        return;
      }
    });
    allThreads[threadIndex] = archiverThread;
    archiverThread.start();
    
    // Attendre que tous les threads se terminent
    for (Thread thread : allThreads) {
      if (thread != null) {
        thread.join();
      }
    }
    
    System.out.println("Program terminated due to decoding error");
  }
}