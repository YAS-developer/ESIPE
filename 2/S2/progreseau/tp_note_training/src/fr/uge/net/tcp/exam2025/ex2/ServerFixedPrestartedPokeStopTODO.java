package fr.uge.net.tcp.exam2025.ex2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerFixedPrestartedPokeStopTODO {


    private static final Logger logger = Logger.getLogger(fr.uge.net.tcp.exam2025.ex2.ServerFixedPrestartedPokeStopTODO.class.getName());
    private final ServerSocketChannel serverSocketChannel;
    private final int nbClients;


    public ServerFixedPrestartedPokeStopTODO(int port, int nbClients) throws IOException {
        this.serverSocketChannel = ServerSocketChannel.open();
        this.nbClients = nbClients;
        serverSocketChannel.bind(new InetSocketAddress(port));
        logger.info(this.getClass().getName()
                + " starts on port " + port);
    }

    public void launch() {
        // TODO
    }


    public static void main(String[] args) throws IOException {
       if (args.length != 2) {
            System.out.println("usage: java ServerFixedPrestartedPokeStop port nbClients");
            return;
        }
        var server = new ServerFixedPrestartedPokeStopTODO(Integer.parseInt(args[0]), Integer.parseInt(args[1]));


        server.launch();
    }
}