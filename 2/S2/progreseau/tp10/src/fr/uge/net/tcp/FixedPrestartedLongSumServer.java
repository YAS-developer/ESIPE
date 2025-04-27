package fr.uge.net.tcp;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FixedPrestartedLongSumServer {

    private static final Logger logger = Logger.getLogger(FixedPrestartedLongSumServer.class.getName());
    private static final int BUFFER_SIZE = 1024;

    private final ServerSocketChannel serverSocketChannel;
    private final int nThreads;

    public FixedPrestartedLongSumServer(int port, int nThreads) throws IOException {
        this.nThreads = nThreads;
        this.serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        logger.info(getClass().getName() + " démarré sur le port " + port 
                    + " avec " + nThreads + " threads pré-démarrés");
    }

    public void launch() {
        for (int i = 0; i < nThreads; i++) {
            Thread.ofPlatform().start(this::acceptLoop);
        }
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void acceptLoop() {
        while (!Thread.interrupted()) {
            try {
                var client = serverSocketChannel.accept();
                serve(client);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Erreur I/O sur accept/serve :", e);
            }
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
    	try {
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
    	finally {
            silentlyClose(sc);
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
        var server = new FixedPrestartedLongSumServer(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        server.launch();
    }
}