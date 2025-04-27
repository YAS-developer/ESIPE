package fr.uge.net.tcp;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoundedOnDemandConcurrentLongSumServer {

    private static final Logger logger = Logger.getLogger(BoundedOnDemandConcurrentLongSumServer.class.getName());
    private static final int BUFFER_SIZE = 1024;
    private final ServerSocketChannel serverSocketChannel;
    private final int maxClient;

    public BoundedOnDemandConcurrentLongSumServer(int port, int maxClient) throws IOException {
        this.maxClient = maxClient;
    	serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        logger.info(this.getClass().getName() + " starts on port " + port);
    }

    /**
     * Iterative server main loop
     *
     * @throws IOException
     * @throws InterruptedException 
     */

    public void launch() throws IOException, InterruptedException {
        logger.info("Server started");
        var semaphore = new Semaphore(maxClient);
        while (!Thread.interrupted()) {
        	semaphore.acquire();
            SocketChannel client = serverSocketChannel.accept();
            Thread.ofPlatform().start(()->{
            	try {
					serve(client);
					
				}catch (IOException ioe) {
	                logger.log(Level.SEVERE, "Connection terminated with client by IOException", ioe.getCause());
	            } finally {
	                silentlyClose(client);
	                semaphore.release();
	            }
            });
        }
    }

    /**
     * Treat the connection sc applying the protocol. All IOException are thrown
     *
     * @param sc
     * @throws IOException
     */
    private void serve(SocketChannel sc) throws IOException {    	
    	ByteBuffer buffer;
    	var bufferSize = ByteBuffer.allocate(Integer.BYTES);
    	var size = 0;
    	var sum = 0L;
    	while(true) {
    		bufferSize.clear();
    		sum = 0L;
    		if(!readFully(sc, bufferSize)) {
    			logger.warning("Connection with server lost");
        		return;
    		}
    		bufferSize.flip();
    		size = bufferSize.getInt();
    		if(size <= 0) {
    			logger.info("Nb opérandes <= 0");
    			continue;
    		}
    		buffer = ByteBuffer.allocate(Long.BYTES * size);
    		if(!readFully(sc, buffer)) {
    			logger.warning("Connection with server lost");
        		return;
    		}
    		buffer.flip();
    		for(var i = 0 ; i < size ; i++) {
    			if(buffer.hasRemaining()) {
    				sum+= buffer.getLong();
    			}
    		}
    		buffer.clear();
    		buffer.putLong(sum);
    		buffer.flip();
    		sc.write(buffer);
    	}
    	

    }

    /**
     * Close a SocketChannel while ignoring IOExecption
     *
     * @param sc
     */

    private void silentlyClose(Closeable sc) {
        if (sc != null) {
            try {
                sc.close();
            } catch (IOException e) {
                // Do nothing
            }
        }
    }

    static boolean readFully(SocketChannel sc, ByteBuffer buffer) throws IOException {
        while (buffer.hasRemaining()) {
            if (sc.read(buffer) == -1) {
                logger.info("Input stream closed");
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws NumberFormatException, IOException, InterruptedException {
        var server = new BoundedOnDemandConcurrentLongSumServer(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        server.launch();
    }
}