package fr.uge.concurrence.exo2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

public class CountAndInterrupt {
  public static void main(String[] args) throws IOException {
    var candidate = new Thread[4];
    IntStream.range(0, 4).forEach(threadId -> {
      candidate[threadId] = Thread.ofPlatform().start(() -> {
        System.out.println("Thread " + threadId + " : " + Thread.currentThread().getName());
        var counter = 0;
        for (;;) {
          try {
            Thread.sleep(1_000);
            System.out.println(Thread.currentThread().getName() + " : " + counter);
            counter++;
          } catch (InterruptedException e) {
            return;
          }
        }
      });
    });

    System.out.println("enter a thread id:");
    try (var input = new InputStreamReader(System.in);
         var reader = new BufferedReader(input)) {
      String line;
      while ((line = reader.readLine()) != null) {
        var threadId = Integer.parseInt(line);
        if (threadId < 4) {
          var thread = candidate[threadId];
          thread.interrupt();
        }
      }
    }
  }
}
