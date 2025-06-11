package fr.uge.net.tcp.exam2025.ex2;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerFixedPrestartedPokeSpot {
	
	private static class InformationsThreadSafe{
		private final Map<String,Integer> pokemonMap = new HashMap<>();
	    private final Map<SocketChannel,Map<String, Integer>> clientsMap = new HashMap<>();
	    private final Object lock = new Object();
	    
	    private void mergePokemonInfo(String pokemon) {
	    	synchronized (lock) {
	    		pokemonMap.merge(pokemon, 1, Integer::sum);
			}
	    }
	    
	    private Integer getPokemonInfo(String pokemon) {
	    	synchronized (lock) {
	    	return pokemonMap.getOrDefault(pokemon,0);
	    	}
	    }
	    
	    private void addNewClient(SocketChannel sc) {
	    	synchronized (lock) {
	    		clientsMap.putIfAbsent(sc, new HashMap<String, Integer>());
			}
	    }
	    
	    private void mergePokemonInfoForClient(SocketChannel sc, String pokemon) {
	    	synchronized (lock) {
	    		clientsMap.get(sc).merge(pokemon, 1, Integer::sum);;
			}
	    	
	    }
	    
	    private Integer getPokemonInfoForClient(SocketChannel sc, String pokemon) {
	    	synchronized (lock) {
	    		 var mapClient = clientsMap.get(sc);
	    	        if (mapClient == null) {
	    	            return 0;
	    	        }
	    	        return mapClient.getOrDefault(pokemon, 0);
	    	}
	    }
	    
	    private Integer getPokemonInfoForAllConnectedClients(String pokemon) {
	    	synchronized (lock) {
	    		  var total = 0;
	    	        for (var clientMap : clientsMap.values()) {
	    	            total += clientMap.getOrDefault(pokemon, 0);
	    	        }
	    	        return total;
			}	    	
	    } 
	    
	    private void removeClient(SocketChannel sc) {
	    	clientsMap.remove(sc);
	    }
	}

    private static final Logger logger = Logger.getLogger(fr.uge.net.tcp.exam2025.ex2.ServerFixedPrestartedPokeSpot.class.getName());
    private final ServerSocketChannel serverSocketChannel;
    private final int nbClients;
    private final InformationsThreadSafe infos;
    private final ByteBuffer bufferIn = ByteBuffer.allocate(260);
    private final ByteBuffer bufferOut = ByteBuffer.allocate(Integer.BYTES * 3);
    
    

    public ServerFixedPrestartedPokeSpot(int port, int nbClients) throws IOException {
        this.serverSocketChannel = ServerSocketChannel.open();
        this.nbClients = nbClients;
        this.infos = new InformationsThreadSafe();
        serverSocketChannel.bind(new InetSocketAddress(port));
        logger.info(this.getClass().getName()
                + " starts on port " + port);
    }

    public void launch() {
        // TODO
    	for (int i = 0; i < nbClients; i++) {
			Thread.ofPlatform().start(this::acceptLoop);
		}
    	
    }
    
    private void acceptLoop() {
		while (!Thread.interrupted()) {
			try {
				var client = serverSocketChannel.accept();
				infos.addNewClient(client);
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
		var frameType = 0;
		try {
			while (true) {
				bufferIn.clear().limit(Integer.BYTES);
				
				if (!readFully(sc, bufferIn)) {
					logger.warning("Connection with server lost");
					return;
				}
				bufferIn.flip();
				frameType = bufferIn.getInt();
				
				if (frameType != 1 && frameType != 2) {
					logger.info("Trame type wrong");
					continue;
				}
				
				var pokemonName = readPokemon(sc);
				if(pokemonName != null) {
					if(frameType == 1) {
						observePokemon(sc,pokemonName);
					}else {
						sendPokemonInfo(sc,pokemonName);
					}
				}
			}
		} finally {
			infos.removeClient(sc);
			silentlyClose(sc);
		}

	}
	
	private void observePokemon(SocketChannel sc, String pokemon) {
		infos.mergePokemonInfo(pokemon);
		infos.mergePokemonInfoForClient(sc, pokemon);
	}
	
	private void sendPokemonInfo(SocketChannel sc, String pokemon) throws IOException {
		var obsSinceStart = infos.getPokemonInfo(pokemon);
		var obsForClient = infos.getPokemonInfoForClient(sc, pokemon);
		var obsAllConnected = infos.getPokemonInfoForAllConnectedClients(pokemon);
		
		bufferOut.clear();
		bufferOut.putInt(obsForClient);
		bufferOut.putInt(obsAllConnected);
		bufferOut.putInt(obsSinceStart);
		bufferOut.flip();
		
		sc.write(bufferOut);
		
	}
	
	private String readPokemon(SocketChannel sc) throws IOException {
		bufferIn.clear().limit(256);
		if (!readFully(sc, bufferIn)) {
			logger.warning("Connection with server lost");
			return null;
		}
		bufferIn.flip();
		var bufferPokemon = ByteBuffer.allocate(256);
		 for (int i = 0; i < 256; i++) {
		        var b = bufferIn.get();
		        if (b == (byte) '!') {
		            break;
		        }
		        bufferPokemon.put(b);
		    }
		bufferPokemon.flip();
		return StandardCharsets.UTF_8.decode(bufferPokemon).toString();
		
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


    public static void main(String[] args) throws IOException {
       if (args.length != 2) {
            System.out.println("usage: java ServerFixedPrestartedPokeSpot port nbClients");
            return;
        }
        var server = new ServerFixedPrestartedPokeSpot(Integer.parseInt(args[0]), Integer.parseInt(args[1]));


        server.launch();
    }
}