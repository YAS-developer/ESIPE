package fr.uge.net.udp.exam2223.ex2;


import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.logging.Logger;

public class ServerChat {

    private static final Logger logger = Logger.getLogger(ServerChat.class.getName());
    private final DatagramChannel datagramChannel;
    private static final Charset UTF8 = StandardCharsets.UTF_8;
    private final int BUFFER_SIZE = 2048;
    private final ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
    private final int port;
    private final HashMap<String, SocketAddress> clients;

    public ServerChat(int port) throws IOException {
        this.datagramChannel = DatagramChannel.open();
        this.port = port;
        this.clients = new HashMap<>();
    }

    public void serve() throws IOException {
        datagramChannel.bind(new InetSocketAddress(port));
        System.out.println("ServerChat started on port " + port);
        var tmpBuffer = ByteBuffer.allocateDirect(1024);
        while(true) {
        	try {
        		
        		buffer.clear();
            	var sender = datagramChannel.receive(buffer);
            	if(sender == null) {
            		logger.info("receive nothing.");
            		continue;
            	}
            	buffer.flip();
            	int size_sender = buffer.getInt();
            	tmpBuffer.clear();
            	for(int i=0; i<size_sender; i++) {
            		tmpBuffer.put(buffer.get());
            	}
            	tmpBuffer.flip();
            	var name_sender = UTF8.decode(tmpBuffer).toString();
            	
            	clients.computeIfAbsent(name_sender, _->sender);
            	tmpBuffer.clear();
            	int size_receiver = buffer.getInt();
            	for(int i=0; i<size_receiver; i++) {
            		tmpBuffer.put(buffer.get());
            	}
            	tmpBuffer.flip();
            	var name_receive = UTF8.decode(tmpBuffer).toString();
            	if(!clients.containsKey(name_receive)) {
            		logger.info("packet ignored.");
            		continue;
            	}
            	
            	var receiver = clients.get(name_receive);
            	int message_length = buffer.getInt();
            	
            	tmpBuffer.clear();
            	for(int i=0; i<message_length; i++) {
            		tmpBuffer.put(buffer.get());
            	}
            	tmpBuffer.flip();
            	
            	buffer.clear();
            	buffer.putInt(size_sender);
            	buffer.put(UTF8.encode(name_sender));
            	buffer.putInt(message_length);
            	buffer.put(tmpBuffer);
            	buffer.flip();
            	
            	datagramChannel.send(buffer, receiver);
            	
			}
        	catch (AsynchronousCloseException e) {
        		logger.info("AsynchronousCloseException");
        		return;
			}
        	catch (IOException e) {
        		logger.severe("server stop by IOException "+e);
        		return;
			}
        }
      
    }

    public static void usage() {
        System.out.println("Usage : ServerChat port");
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            usage();
            return;
        }
        int port = Integer.valueOf(args[0]);
        if (!(port >= 1024) & port <= 65535) {
            System.out.println("The port number must be between 1024 and 65535");
            return;
        }

        var server=new ServerChat(port);
        try {
            server.serve();
        } catch (BindException e) {
            System.err.println("Server could not bind on " + port + "\nAnother server is probably running on this port.");
            return;
        }
    }
}
