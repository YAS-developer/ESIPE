package fr.uge.net.tcp.exam2025.ex1;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClientPokemonObserver {
    private static final Logger logger = Logger.getLogger(ClientPokemonObserver.class.getName());

    private final InetSocketAddress serverAddress;
    private final SocketChannel socketChannel;
    // TODO : you can add fields if necessary
    private final ByteBuffer buffer = ByteBuffer.allocate(1024);

    public ClientPokemonObserver(InetSocketAddress serverAddress) throws IOException {
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
    	Objects.requireNonNull(region);
    	
    	var regionBuf = StandardCharsets.UTF_8.encode(region);
    	if(regionBuf.remaining() > 1020) {
    		logger.info("Le message est trop grand");
    		return false;
    	}
    	var bufferIn = ByteBuffer.allocate(Integer.BYTES + regionBuf.remaining());
    	
    	bufferIn.putInt(regionBuf.remaining());
    	bufferIn.put(regionBuf);
    	bufferIn.flip();
    	
    	socketChannel.write(bufferIn);
    	 
    	return true;
    }

    /**
     * Runnable of the listener thread, that receive the information of the Pokemon
     * and print them
     * @throws IOException 
     */
    private void listen() throws IOException {
        var map = new HashMap<String, Long>();
    	try {
    		for(; ; ) {
                // TODO receive the Pokémon infos and print them         	
            
            	var pokemonName = readString(buffer);
            	if(pokemonName == null) {
            		return;
            	}
            	
            	var zoneName = readString(buffer);
            	if(zoneName == null) {
            		return;
            	}
            	
            	buffer.clear().limit(Short.BYTES);
            	if(!readFully(socketChannel, buffer)) {
            		logger.info("Connexion interompu par le client");
            		return;
            	}
            	buffer.flip();
            	var nbChara = buffer.getShort();
            	
            	for(int i= 0 ; i < nbChara ; i++) {
            		var chara = readString(buffer);
            		if(chara == null) {
                		return;
                	}
            		buffer.clear().limit(Long.BYTES);
            		if(!readFully(socketChannel, buffer)) {
            			logger.info("Connexion interompu par le client");
                		return;
            		}
            		buffer.flip();
            		var value = buffer.getLong();
            		map.putIfAbsent(chara, value);
            	}
            	
            	System.out.println("==============");
            	System.out.println("Pokemon : " + pokemonName);
            	System.out.println("Location : " + zoneName);
            	for( Map.Entry<String, Long> m : map.entrySet()) {
            		System.out.println(m.getKey() + " -> " + m.getValue());
            	}
            	System.out.println("==============");
        		map.clear();
            }
		} finally {
			socketChannel.close();
		}
        
    }
    
    static boolean readFully(SocketChannel sc, ByteBuffer buffer) throws IOException {
		while (buffer.hasRemaining()) {
			if (sc.read(buffer) == -1) {
				return false;
			}
		}
		return true;
	}
    
    private String readString(ByteBuffer buffer) throws IOException {
    	buffer.clear().limit(Integer.BYTES);
    	if(!readFully(socketChannel, buffer)) {
    		logger.info("Connexion interompu par le client");
    		return null;
    	}
    	buffer.flip();
    	
    	var stringSize = buffer.getInt();
    	if(stringSize > 1020) {
    		logger.info("La trame n'est pas correcte");
    		return null;
    	}
    	buffer.clear().limit(stringSize);

    	if(!readFully(socketChannel, buffer)) {
    		logger.info("Connexion interompu par le client");
    		return null;
    	}
    	buffer.flip();
    	
    	var string = StandardCharsets.UTF_8.decode(buffer).toString();
    	
    	return string;
    }
    

    public void launch() throws IOException {
        var listener = Thread.ofPlatform().start(() -> {
			try {
				listen();
			} catch (IOException e) {
				return;
			}
		});
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
        new ClientPokemonObserver(serverAddress).launch();
    }
}