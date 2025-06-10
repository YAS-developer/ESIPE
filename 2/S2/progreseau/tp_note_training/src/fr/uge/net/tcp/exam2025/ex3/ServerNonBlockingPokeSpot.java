package fr.uge.net.tcp.exam2025.ex3;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerNonBlockingPokeSpot {

	static private class Context {

		final private SelectionKey key;
		final private SocketChannel socketChannel;
		final private ServerNonBlockingPokeSpot server;
		final private ByteBuffer bufferIn = ByteBuffer.allocate(BUFFER_SIZE);
		final private ByteBuffer bufferPokemon = ByteBuffer.allocate(BUFFER_SIZE);
		final private ByteBuffer bufferOut = ByteBuffer.allocate(BUFFER_SIZE);
		private boolean closed = false;

		private Context(SelectionKey key, ServerNonBlockingPokeSpot server) {
			this.key = key;
			this.server = server;
			this.socketChannel = (SocketChannel) key.channel();
			server.clientsMap.putIfAbsent(socketChannel, new HashMap<String, Integer>());
			
		}

		/**
		 * Update the interestOps of the key looking only at values of the boolean
		 * closed and the ByteBuffer buff.
		 *
		 * The convention is that buff is in write-mode.
		 */
		private void updateInterestOps() {
			var newInterestOps = 0;
			if (bufferIn.hasRemaining() && !closed) {
				newInterestOps = newInterestOps | SelectionKey.OP_READ;
			}
			if (bufferOut.position() != 0) {
				newInterestOps = newInterestOps | SelectionKey.OP_WRITE;
			}
			if (newInterestOps == 0) {
				silentlyClose();
				return;
			}
			key.interestOps(newInterestOps);

		}

		/**
		 * Performs the read action on sc
		 *
		 * The convention is that buff is in write-mode before calling doRead and is in
		 * write-mode after calling doRead
		 *
		 * @throws IOException
		 */
		private void doRead() throws IOException {
			if (socketChannel.read(bufferIn) == -1) {
				closed = true;
			}
			processIn();
			updateInterestOps();
		}

		private void processIn() throws IOException {
			bufferIn.flip();
			while (bufferIn.remaining() >= Integer.BYTES + 256) {
				var frameType = bufferIn.getInt();
				bufferPokemon.clear();
				for (int i = 0; i < 256; i++) {
					var b = bufferIn.get();
					if (b != (byte) '!') {
						bufferPokemon.put(b);
					}
				}
				bufferPokemon.flip();

				var pokemonName = StandardCharsets.UTF_8.decode(bufferPokemon).toString();
				if (pokemonName != null) {
					if (frameType == 1) {
						observePokemon(pokemonName);
					} else {
						processPokemonInfo(pokemonName);
					}
				}
			}
			bufferIn.compact();

		}

		private void observePokemon( String pokemon) {
			server.pokemonMap.merge(pokemon,1, Integer::sum);
			server.clientsMap.get(socketChannel).merge(pokemon, 1, Integer::sum);;
		}

		private void processPokemonInfo(String pokemon) throws IOException {
			var obsSinceStart = server.pokemonMap.getOrDefault(pokemon,0);
			var mapClient = server.clientsMap.get(socketChannel);
			var obsForClient = mapClient == null ? 0 : mapClient.getOrDefault(pokemon, 0);
			var total = 0;
 	        for (var clientMap : server.clientsMap.values()) {
 	            total += clientMap.getOrDefault(pokemon, 0);
 	        }
			var obsAllConnected = total;

			bufferOut.clear();
			bufferOut.putInt(obsForClient);
			bufferOut.putInt(obsAllConnected);
			bufferOut.putInt(obsSinceStart);

		}

		/**
		 * Performs the write action on sc
		 *
		 * The convention is that buff is in write-mode before calling doWrite and is in
		 * write-mode after calling doWrite
		 *
		 * @throws IOException
		 */
		private void doWrite() throws IOException {
			bufferOut.flip();
			socketChannel.write(bufferOut);
			bufferOut.compact();
			updateInterestOps();
		}

		private void silentlyClose() {
			try {
				server.clientsMap.remove(socketChannel);
				socketChannel.close();
			} catch (IOException e) {
				// ignore exception
			}
		}
	}

	static private int BUFFER_SIZE = 1_024;
	static private Logger logger = Logger.getLogger(ServerNonBlockingPokeSpot.class.getName());

	private final ServerSocketChannel serverSocketChannel;
	private final Selector selector;

	private final Map<String, Integer> pokemonMap = new HashMap<>();
	private final Map<SocketChannel, Map<String, Integer>> clientsMap = new HashMap<>();

	public ServerNonBlockingPokeSpot(int port) throws IOException {
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(port));
		selector = Selector.open();
	}

	public void launch() throws IOException {
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		while (!Thread.interrupted()) {
			printKeys(); // for debug
			System.out.println("Starting select");
			try {
				selector.select(this::treatKey);
			} catch (UncheckedIOException tunneled) {
				throw tunneled.getCause();
			}
			System.out.println("Select finished");
		}
	}

	private void treatKey(SelectionKey key) {
		printSelectedKey(key); // for debug
		try {
			if (key.isValid() && key.isAcceptable()) {
				doAccept(key);
			}
		} catch (IOException ioe) {
			// lambda call in select requires to tunnel IOException
			throw new UncheckedIOException(ioe);
		}
		try {
			if (key.isValid() && key.isWritable()) {
				((Context) key.attachment()).doWrite();
			}
			if (key.isValid() && key.isReadable()) {
				((Context) key.attachment()).doRead();
			}
		} catch (IOException e) {
			logger.log(Level.INFO, "Connection closed with client due to IOException", e);
			((Context) key.attachment()).silentlyClose();
		}
	}

	private void doAccept(SelectionKey key) throws IOException {
		var client = serverSocketChannel.accept();
		if (client == null) {
			return;
		}
		
		client.configureBlocking(false);
		var clientKey = client.register(selector, SelectionKey.OP_READ);
		clientKey.attach(new Context(clientKey, this));
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		if (args.length != 1) {
			usage();
			return;
		}
		new ServerNonBlockingPokeSpot(Integer.parseInt(args[0])).launch();
	}

	private static void usage() {
		System.out.println("Usage : ServerNonBlockingPokeSpot port");
	}

	/***
	 * These methods are here to help understanding the behavior of the selector
	 ***/

	private String interestOpsToString(SelectionKey key) {
		if (!key.isValid()) {
			return "CANCELLED";
		}
		int interestOps = key.interestOps();
		ArrayList<String> list = new ArrayList<>();
		if ((interestOps & SelectionKey.OP_ACCEPT) != 0)
			list.add("OP_ACCEPT");
		if ((interestOps & SelectionKey.OP_READ) != 0)
			list.add("OP_READ");
		if ((interestOps & SelectionKey.OP_WRITE) != 0)
			list.add("OP_WRITE");
		return String.join("|", list);
	}

	public void printKeys() {
		Set<SelectionKey> selectionKeySet = selector.keys();
		if (selectionKeySet.isEmpty()) {
			System.out.println("The selector contains no key : this should not happen!");
			return;
		}
		System.out.println("The selector contains:");
		for (SelectionKey key : selectionKeySet) {
			SelectableChannel channel = key.channel();
			if (channel instanceof ServerSocketChannel) {
				System.out.println("\tKey for ServerSocketChannel : " + interestOpsToString(key));
			} else {
				SocketChannel sc = (SocketChannel) channel;
				System.out.println("\tKey for Client " + remoteAddressToString(sc) + " : " + interestOpsToString(key));
			}
		}
	}

	private String remoteAddressToString(SocketChannel sc) {
		try {
			return sc.getRemoteAddress().toString();
		} catch (IOException e) {
			return "???";
		}
	}

	public void printSelectedKey(SelectionKey key) {
		SelectableChannel channel = key.channel();
		if (channel instanceof ServerSocketChannel) {
			System.out.println("\tServerSocketChannel can perform : " + possibleActionsToString(key));
		} else {
			SocketChannel sc = (SocketChannel) channel;
			System.out.println(
					"\tClient " + remoteAddressToString(sc) + " can perform : " + possibleActionsToString(key));
		}
	}

	private String possibleActionsToString(SelectionKey key) {
		if (!key.isValid()) {
			return "CANCELLED";
		}
		ArrayList<String> list = new ArrayList<>();
		if (key.isAcceptable())
			list.add("ACCEPT");
		if (key.isReadable())
			list.add("READ");
		if (key.isWritable())
			list.add("WRITE");
		return String.join(" and ", list);
	}
}
