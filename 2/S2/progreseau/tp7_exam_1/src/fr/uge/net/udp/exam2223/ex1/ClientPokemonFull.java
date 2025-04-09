package fr.uge.net.udp.exam2223.ex1;



import java.io.Console;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Logger;



import static java.nio.file.StandardOpenOption.*;

public class ClientPokemonFull {

    private static final Charset UTF8 = StandardCharsets.UTF_8;
    private static final Logger logger = Logger.getLogger(ClientPokemon.class.getName());
    private static final int SEND_BUFFER_SIZE = 1024;
    private static final int RECEIVE_BUFFER_SIZE = 2048;
    private final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);

    private record Pokemon(String name, Map<String,Integer> characteristics){
        public Pokemon {
            Objects.requireNonNull(name);
            characteristics= Map.copyOf(characteristics);
        }

        @Override
        public String toString() {
            var stringBuilder = new StringBuilder();
            stringBuilder.append(name);
            for( var entry : characteristics.entrySet()){
                stringBuilder.append(';')
                        .append(entry.getKey())
                        .append(':')
                        .append(entry.getValue());
            }
            return stringBuilder.toString();
        }
    }

    private final String inFilename;
    private final String outFilename;
    private final InetSocketAddress server;
    private final DatagramChannel datagramChannel;

    public static void usage() {
        System.out.println("Usage : ClientPokemon in-filename out-filename host port ");
    }

    public ClientPokemonFull(String inFilename, String outFilename,
                         InetSocketAddress server) throws IOException {
        this.inFilename = Objects.requireNonNull(inFilename);
        this.outFilename = Objects.requireNonNull(outFilename);
        this.server = server;
        this.datagramChannel = DatagramChannel.open();
    }


    public void launch() throws IOException, InterruptedException {
        try {
            datagramChannel.bind(null);
            // Read all lines of inFilename opened in UTF-8
            var pokemonNames = Files.readAllLines(Path.of(inFilename), UTF8);
            // List of Pokemon to write to the output file
            var pokemons = new ArrayList<Pokemon>();

            // TODO
            
            
            var listener = Thread.ofPlatform().start(() -> {
                var buffer = ByteBuffer.allocate(RECEIVE_BUFFER_SIZE);
                try {
                while(!Thread.interrupted()) {
    	            	buffer.clear();
    	            	datagramChannel.receive(buffer);
    	            	buffer.flip();     
    	              queue.put(UTF8.decode(buffer).toString());
    	              logger.info("Thread listener interrompu");
                
                }
                } catch ( AsynchronousCloseException | InterruptedException e) {
                    logger.info("AsynchronousCloseException or InterruptedException ");
                    return;
                  } catch (IOException e) {
                	logger.severe("Listener coupé");
    				return;
    			}
                
            });
            
            var sendBuffer = ByteBuffer.allocateDirect(SEND_BUFFER_SIZE);
            var receiveBuffer = ByteBuffer.allocateDirect(RECEIVE_BUFFER_SIZE);
            for(var pokemonName : pokemonNames) {
            	
            	
            	
            	
            	
            	sendBuffer.clear();
            	var pokemonNameBuffer = UTF8.encode(pokemonName);
            	if(pokemonNameBuffer.capacity()+4 > sendBuffer.capacity()) {
            		continue;
            	}
            	sendBuffer.putInt(pokemonNameBuffer.capacity());
            	sendBuffer.put(pokemonNameBuffer);
            	sendBuffer.flip();
            	String result = null;
            	while(result == null) {
            		datagramChannel.send(sendBuffer, server);
            		result = queue.poll(300, null);
                	if(result == null) {
                		logger.info("receive noting");
                	}
            	}
            	
            	
            	
           
            	var tmpBuffer = ByteBuffer.allocateDirect(RECEIVE_BUFFER_SIZE);
            	var isName=true;
            	var characteristics = new HashMap<String, Integer>();
            	while(receiveBuffer.hasRemaining()) {
            		var bytee = receiveBuffer.get();
            		if(bytee != (byte)0){
            			tmpBuffer.put(bytee);
            		}
            		if(bytee == (byte)0 && isName) {
            			isName=false;
            			tmpBuffer.clear();
            			continue;
            		}
            		if(bytee == (byte)0 && !isName) {
            			tmpBuffer.flip();
            			characteristics.putIfAbsent(UTF8.decode(tmpBuffer).toString(), receiveBuffer.getInt());
            			tmpBuffer.clear();
            		}
            	}
            	
            	
            	
            	
            	
            	pokemons.add(new Pokemon(pokemonName, characteristics));
            	
            	
            }
            

            // Convert the pokemons to strings and write then in the output file
            var lines = pokemons.stream().map(Pokemon::toString).toList();
            Files.write(Paths.get(outFilename), lines , UTF8, CREATE, WRITE, TRUNCATE_EXISTING);
        } 
        catch (AsynchronousCloseException e) {
			logger.warning("Problem while sending to the server");
		}
        finally {
            datagramChannel.close();
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length != 4) {
            usage();
            return;
        }

        var inFilename = args[0];
        var outFilename = args[1];
        var server = new InetSocketAddress(args[2], Integer.parseInt(args[3]));

        // Create client with the parameters and launch it
        new ClientPokemon(inFilename, outFilename, server).launch();
    }
}