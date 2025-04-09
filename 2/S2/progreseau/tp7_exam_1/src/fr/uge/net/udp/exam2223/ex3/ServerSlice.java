package fr.uge.net.udp.exam2223.ex3;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ServerSlice {
    private static final Logger logger = Logger.getLogger(ServerSlice.class.getName());
    private final DatagramChannel datagramChannel;
    private final Selector selector;
    private final int port;
    private SocketAddress sender;
    private final ArrayList<Long> longs;

    public ServerSlice(int port) throws IOException {
        this.port = port;
        this.selector = Selector.open();
        this.datagramChannel = DatagramChannel.open();
        this.longs = new ArrayList<Long>();
    }

    public void serve() throws IOException {
    	datagramChannel.bind(new InetSocketAddress(port));
        datagramChannel.configureBlocking(false);
        datagramChannel.register(selector, SelectionKey.OP_READ);
        logger.info("ServerSlice started on port " + port);
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
        var dc = (DatagramChannel)key.channel();
        var receiveBuffer = ByteBuffer.allocateDirect(128*8);
        
        sender = dc.receive(receiveBuffer);
        receiveBuffer.flip();
        if(sender == null || receiveBuffer.remaining() <8) {
        	logger.info("no packets received or insufisance packet length");
        	return;
        }
        
        var nb_long = receiveBuffer.remaining()/8;
        
        for(int i=0; i<nb_long; i++) {
        	longs.add(receiveBuffer.getLong());
        }
        	
        key.interestOps(SelectionKey.OP_WRITE);
    }

    private void doWrite(SelectionKey key) throws IOException {
    	if(longs.isEmpty()) {
    		key.interestOps(SelectionKey.OP_READ);
    		return;
    	}
    	var dc = (DatagramChannel)key.channel();
    	var sendBuffer = ByteBuffer.allocateDirect(8);
    	sendBuffer.putLong(longs.removeFirst());
    	sendBuffer.flip();    
    	
    	dc.send(sendBuffer, sender);
    }

    public static void usage() {
        System.out.println("Usage : ServerSlice port");
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            usage();
            return;
        }
        new ServerSlice(Integer.parseInt(args[0])).serve();
    }
}
