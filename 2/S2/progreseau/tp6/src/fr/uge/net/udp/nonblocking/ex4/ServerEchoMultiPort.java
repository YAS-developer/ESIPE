package fr.uge.net.udp.nonblocking.ex4;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.classfile.instruction.NewMultiArrayInstruction;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.logging.Logger;

class ServerEchoMultiPort {
	 

	class Context {
		
		private InetSocketAddress address;
		private final ByteBuffer buffer;
		
		public Context(){
			buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
		}
	}

	private final int BUFFER_SIZE = 1024;
	private final static Logger logger = Logger.getLogger(ServerEchoMultiPort.class.getName());
	private final Selector selector;

	 
	 
	 public ServerEchoMultiPort(int min, int max) throws IOException {
		 if(min > max || max < 0 || min < 0) {
			 throw new IllegalArgumentException(); 
		 }
		 selector = Selector.open();
		 
		 for(int port= min; port <= max; port++) {
			 var dc = DatagramChannel.open();
			 dc.bind(new InetSocketAddress(port));
			 dc.configureBlocking(false);
			 dc.register(selector, SelectionKey.OP_READ, new Context());
		 }
	 }
	 
	 public void serve() throws IOException {
        logger.info("ServerEchoRepeat started");
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
		
		 
		 var dChannel = (DatagramChannel) key.channel();
		 var context= (Context)key.attachment();
		 if(context == null || dChannel == null) {
			 return;
		 }
		 var buffer = context.buffer;
		 var sender =  dChannel.receive(buffer);
		 if(sender == null || buffer.remaining() >= BUFFER_SIZE) {
			 return;
		 }
		 context.address = (InetSocketAddress) sender;
		 buffer.flip();
		 key.interestOps(SelectionKey.OP_WRITE);

	}

	private void doWrite(SelectionKey key) throws IOException {
		 var dChannel = (DatagramChannel) key.channel();
		 var context= (Context)key.attachment();
		 if(context == null || dChannel == null) {
			 return;
		 }
		 var buffer = context.buffer;
		 dChannel.send(buffer, context.address);
		 buffer.clear();
		 key.interestOps(SelectionKey.OP_READ);
	}

	public static void usage() {
		 System.out.println(" ServerEchoMultiPort min max");
	 }
	 
	 
	 public static void main(String[] args) {
	
		 if(args.length != 2) {
			 usage();
			 return;
		 }
		 
		 try {
			 new ServerEchoMultiPort(Integer.parseInt(args[0]), Integer.parseInt(args[1])).serve();
		 }
		 catch (IOException e) {
			 logger.severe("Server interruped brutaly");
			 return;
		}
		 
		 
	}
}
