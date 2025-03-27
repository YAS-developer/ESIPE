package fr.uge.net.ex2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ClientUpperCaseUDPTimeout {
  public static final int BUFFER_SIZE = 1024;
  private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ClientUpperCaseUDPTimeout.class.getName());
  private static void usage() {
    System.out.println("Usage : NetcatUDP host port charset");
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    if (args.length != 3) {
      usage();
      return;
    }

    var server = new InetSocketAddress(args[0], Integer.parseInt(args[1]));
    var cs = Charset.forName(args[2]);
    var buffer = ByteBuffer.allocate(BUFFER_SIZE);

    var queue = new ArrayBlockingQueue<String>(10);

    try (var scanner = new Scanner(System.in); var dc = DatagramChannel.open()) {
      dc.bind(null);
      Thread.ofPlatform().start(() -> {
        while(!Thread.interrupted()) {
          try {
            buffer.clear();
            dc.receive(buffer);
            buffer.flip();
            try {
              queue.put(cs.decode(buffer).toString());
            } catch (InterruptedException e) {
              logger.info("Interruption");
              return;
            }
            buffer.clear();
          } catch (IOException e) {
            logger.severe("Listener coupé");
            return;
          }
        }  
      });

      
      while (scanner.hasNextLine()) {
        var line = scanner.nextLine();
        dc.send(cs.encode(line), server);
        buffer.clear();

        var sender = queue.poll(1, TimeUnit.SECONDS);
        if(sender == null) {
          logger.info("Le serveur n'a pas répondu");
        }else {
          System.out.println(sender); 
        }

      }
    }
  }
}
