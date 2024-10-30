package fr.uge.conc.ex1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Codex {
  private static final int CODED_QUEUE_CAPACITY = 100;
  private static final int DECODED_QUEUE_CAPACITY = 100;
  
  public static void main(String[] args) throws InterruptedException {
    // Files d'attente pour les messages codés et décodés
    BlockingQueue<String> codedMessages = new ArrayBlockingQueue<>(CODED_QUEUE_CAPACITY);
    BlockingQueue<String> decodedMessages = new ArrayBlockingQueue<>(DECODED_QUEUE_CAPACITY);
    
    // 3 threads pour recevoir les messages
    for (int i = 0; i < 3; i++) {
      final int id = i;
      Thread receiverThread = new Thread(() -> {
        try {
          while (!Thread.interrupted()) {
            String codedMsg = CodeAPI.receive();
            System.out.println("Receiver " + id + " received: " + codedMsg);
            codedMessages.put(codedMsg);
          }
        } catch (InterruptedException e) {
          return;
        }
      });
      receiverThread.start();
    }
    
    // 2 threads pour décoder les messages
    for (int i = 0; i < 2; i++) {
      final int id = i;
      Thread decoderThread = new Thread(() -> {
        try {
          while (!Thread.interrupted()) {
            String codedMsg = codedMessages.take();
            System.out.println("Decoder " + id + " processing: " + codedMsg);
            try {
              String decodedMsg = CodeAPI.decode(codedMsg);
              System.out.println("Decoder " + id + " decoded: " + decodedMsg);
              decodedMessages.put(decodedMsg);
            } catch (IllegalArgumentException e) {
              System.out.println("Decoder " + id + " failed to decode message: " + codedMsg);
              // On ignore les messages qui ne peuvent pas être décodés
            }
          }
        } catch (InterruptedException e) {
          return;
        }
      });
      decoderThread.start();
    }
    
    // 1 thread pour archiver les messages
    Thread archiverThread = new Thread(() -> {
      try {
        while (!Thread.interrupted()) {
          String decodedMsg = decodedMessages.take();
          System.out.println("Archiver processing: " + decodedMsg);
          CodeAPI.archive(decodedMsg);
        }
      } catch (InterruptedException e) {
        return;
      }
    });
    archiverThread.start();
    
    // Le programme continue indéfiniment
    Thread.sleep(Long.MAX_VALUE);
  }
}