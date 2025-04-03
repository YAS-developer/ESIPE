package fr.uge.net.udp.ex3;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Random;
import java.util.logging.Logger;

public class ServerEchoRepeat {
    private static final Logger logger = Logger.getLogger(ServerEchoRepeat.class.getName());

    private final DatagramChannel dc;
    private final int BUFFER_SIZE = 1024;
    private final ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
    private final Selector selector;
    private int port;

    public ServerEchoRepeat(int port) throws IOException {
        this.port = port;
        selector = Selector.open();
        dc = DatagramChannel.open();
        dc.bind(new InetSocketAddress(port));
        
        // TODO set dc in non-blocking mode and register it to the selector
        dc.configureBlocking(false);
        dc.register(selector, SelectionKey.OP_READ);
    }

    public void serve() throws IOException {
        logger.info("ServerEchoRepeat started on port " + port);
        while (!Thread.interrupted()) {
            selector.select(this::treatKey);
        }
    }

    private void treatKey(SelectionKey key) {
        try {
            if (key.isValid() && key.isWritable()) {
                doWrite(key);
            }
            if (key.isValid() && key.isReadable()) {
                doRead(key);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    private void doRead(SelectionKey key) throws IOException {
        // TODO
    	
    	var dc1 = (DatagramChannel)key.channel();
    	
    	var sender = dc1.receive(buffer);
    	buffer.clear();
    	if(sender == null) {
    		logger.warning("Selector ");
    		return;
    	}
    	buffer.flip();
    	var rep = buffer.getInt(); 
    	int content = 
    	
    }

    private void doWrite(SelectionKey key) throws IOException {
        // TODO
    }

    public static void usage() {
        System.out.println("Usage : ServerEchoRepeat port");
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            usage();
            return;
        }
        new ServerEchoRepeat(Integer.parseInt(args[0])).serve();
    }
}
