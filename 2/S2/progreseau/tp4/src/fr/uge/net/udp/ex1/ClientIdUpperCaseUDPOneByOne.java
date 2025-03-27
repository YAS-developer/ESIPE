package fr.uge.net.udp.ex1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static java.nio.file.StandardOpenOption.*;

public class ClientIdUpperCaseUDPOneByOne {

	private static Logger logger = Logger.getLogger(ClientIdUpperCaseUDPOneByOne.class.getName());
	private static final Charset UTF8 = StandardCharsets.UTF_8;
	private static final int BUFFER_SIZE = 1024;

	private record Response(long id, String message) {
	};

	private final String inFilename;
	private final String outFilename;
	private final long timeout;
	private final InetSocketAddress server;
	private final DatagramChannel dc;
	private final SynchronousQueue<Response> queue = new SynchronousQueue<>();

	private long i=0;
	
	public static void usage() {
		System.out.println("Usage : ClientIdUpperCaseUDPOneByOne in-filename out-filename timeout host port ");
	}

	public ClientIdUpperCaseUDPOneByOne(String inFilename, String outFilename, long timeout, InetSocketAddress server)
			throws IOException {
		this.inFilename = Objects.requireNonNull(inFilename);
		this.outFilename = Objects.requireNonNull(outFilename);
		this.timeout = timeout;
		this.server = server;
		this.dc = DatagramChannel.open();
		dc.bind(null);
	}

	private void listenerThreadRun() {
		 
	  
        var buffer = ByteBuffer.allocate(BUFFER_SIZE);
        try {
        while(true) {
        	 	
            	buffer.clear();
            	dc.receive(buffer);
            	buffer.flip();
            	if(buffer.remaining() >= Long.BYTES) {
            		var id = buffer.getLong();
            		var message = UTF8.decode(buffer).toString();
                	buffer.clear();
       
                	var rep = new Response(id, message); 
                	queue.put(rep);
            	}
        }
        } catch ( AsynchronousCloseException | InterruptedException e) {
            logger.info("AsynchronousCloseException or InterruptedException ");
            return;
          } catch (IOException e) {
        	logger.severe("Listener coupé");
			return;
		}
	            
	          
	        		 
	}

	public void launch() throws IOException, InterruptedException {
		try {

			var listenerThread = Thread.ofPlatform().start(this::listenerThreadRun);
			
			// Read all lines of inFilename opened in UTF-8
			var lines = Files.readAllLines(Path.of(inFilename), UTF8);
			
			var upperCaseLines = new ArrayList<String>();
			var buffer = ByteBuffer.allocate(BUFFER_SIZE); 
			 for(var line : lines) {
				 	
				 	buffer.clear();
	            	buffer.putLong(i);
	            	buffer.put(UTF8.encode(line));
	            	
		            while(true) {
		              buffer.flip();
		              dc.send(buffer, server);
		            
		              var result = queue.poll(timeout, TimeUnit.MILLISECONDS);
		              
		              if(result == null) {
		                logger.info("Pas de réponse");
		              }else if (result.id == i){
		            	
		                System.out.println("Received : " + result);
		                upperCaseLines.add(result.message);
		                i++;
		                break;
		              }
		              
		            }    
		          }
			

			listenerThread.interrupt();
			Files.write(Paths.get(outFilename), upperCaseLines, UTF8, CREATE, WRITE, TRUNCATE_EXISTING);
		} finally {
			dc.close();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		if (args.length != 5) {
			usage();
			return;
		}

		var inFilename = args[0];
		var outFilename = args[1];
		var timeout = Long.parseLong(args[2]);
		var server = new InetSocketAddress(args[3], Integer.parseInt(args[4]));

		// Create client with the parameters and launch it
		new ClientIdUpperCaseUDPOneByOne(inFilename, outFilename, timeout, server).launch();
	}
}
