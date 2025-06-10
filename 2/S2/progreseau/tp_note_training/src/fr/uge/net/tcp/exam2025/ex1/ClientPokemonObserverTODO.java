package fr.uge.net.tcp.exam2025.ex1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClientPokemonObserverTODO {
    private static final Logger logger = Logger.getLogger(ClientPokemonObserverTODO.class.getName());

    private final InetSocketAddress serverAddress;
    private final SocketChannel socketChannel;
    // TODO : you can add fields if necessary

    public ClientPokemonObserverTODO(InetSocketAddress serverAddress) throws IOException {
        this.serverAddress = Objects.requireNonNull(serverAddress);
        this.socketChannel = SocketChannel.open(serverAddress);
    }

    /**
     * Write the request corresponding to region as specified in the protocol
     * PokemonObserver
     *
      * @param region
     * @return true if the request could be sent
     * @throws IOException
     */
    private boolean sendRequest(String region) throws IOException {
        return false;
    }

    /**
     * Runnable of the listener thread, that receive the information of the Pokemon
     * and print them
     */
    private void listen() {
            for(; ; ) {
                // TODO receive the Pokémon infos and print them
            }
    }

    public void launch() throws IOException {
        var listener = Thread.ofPlatform().start(this::listen);
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("What region are you interested in ?");
            while (scanner.hasNextLine()) {
                var region = scanner.nextLine();
                if (!sendRequest(region)){
                    System.out.println("You request could not be send using the protocol PokemonObserver");
                }
                System.out.println("What region are you interested in ?");
            }
        } finally {
            listener.interrupt();
            socketChannel.close();
        }

    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("usage: java ClientPokemonObserver host port");
            return;
        }
        var serverAddress = new InetSocketAddress(args[0], Integer.valueOf(args[1]));
        new ClientPokemonObserverTODO(serverAddress).launch();
    }
}