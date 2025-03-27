package fr.uge.net.ex3;

import java.io.IOException;
import java.lang.classfile.ClassFile.DeadCodeOption;
import java.net.InetSocketAddress;
import java.net.http.WebSocket.Listener;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.*;

public class ClientUpperCaseUDPFile {
    private final static Charset UTF8 = StandardCharsets.UTF_8;
    private final static int BUFFER_SIZE = 1024;
    private final static Logger logger = Logger.getLogger(ClientUpperCaseUDPFile.class.getName());
    private final static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

    private static void usage() {
        System.out.println("Usage : ClientUpperCaseUDPFile in-filename out-filename timeout host port ");
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length != 5) {
            usage();
            return;
        }

        var inFilename = args[0];
        var outFilename = args[1];
        var timeout = Integer.parseInt(args[2]);
        var server = new InetSocketAddress(args[3], Integer.parseInt(args[4]));

        // Read all lines of inFilename opened in UTF-8
        var lines = Files.readAllLines(Path.of(inFilename), UTF8);
        var upperCaseLines = new ArrayList<String>();
        
        try(var dc = DatagramChannel.open()){
          dc.bind(null);
          
          
          var listener = Thread.ofPlatform().start(() -> {
            var buffer = ByteBuffer.allocate(BUFFER_SIZE);
            try {
            while(!Thread.interrupted()) {
	            	buffer.clear();
	            	dc.receive(buffer);
	            	buffer.flip();     
	              queue.put(UTF8.decode(buffer).toString());
	              logger.info("Thread listener interrompu");
            
            }
            } catch ( AsynchronousCloseException | InterruptedException e) {
                logger.info("AsynchronousCloseException or InterruptedException ");
                return;
              } catch (IOException e) {
            	logger.severe("Listener coupé");
				return;
			}
            
          });
          
          for(var line : lines) {
            
            String result = null;
            
            var encodeLine = UTF8.encode(line);
            
            while(result == null) {
              dc.send(encodeLine, server);
              encodeLine.flip();
              result = queue.poll(timeout, TimeUnit.MILLISECONDS);
              
              if(result == null) {
                logger.info("Pas de réponse");
              }else {
                System.out.println("Received : " + result);
                upperCaseLines.add(result);
              }
            }    
          }
          
          dc.close();
          listener.interrupt();
        }
        
        

        // Write upperCaseLines to outFilename in UTF-8
        Files.write(Path.of(outFilename), upperCaseLines, UTF8, CREATE, WRITE, TRUNCATE_EXISTING);
    }
}