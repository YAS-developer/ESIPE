package fr.uge.net.tcp.nonblocking;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerChaton {
	private static final int BUFFER_SIZE = 1024;
	private static final Logger logger = Logger.getLogger(ServerChaton.class.getName());

	static private class Context {
		private final SelectionKey key;
		private final SocketChannel sc;
		private final ByteBuffer bufferIn = ByteBuffer.allocate(BUFFER_SIZE);
		private final ByteBuffer bufferOut = ByteBuffer.allocate(BUFFER_SIZE);
		private final ArrayDeque<Message> queue = new ArrayDeque<>();
		private final ServerChaton server;
		private boolean closed = false;

		private Context(SelectionKey key, ServerChaton server) {
			this.key = key;
			this.sc = (SocketChannel) key.channel();
			this.server = server;
		}

		private void processIn() {
			var reader = new MessageReader();
			for (;;) {
				switch (reader.process(bufferIn)) {
				case DONE:
					var msg = reader.get();
					reader.reset();
					server.broadcast(msg);
					break;
				case REFILL:
					return;
				case ERROR:
					silentlyClose();
					return;
				}
			}
		}

		private void processOut() {
			while (!queue.isEmpty()) {
				var msg = queue.peek();
				var bb = msg.toByteBuffer();
				if (bb.remaining() > bufferOut.remaining()) {
					break;
				}
				bufferOut.put(bb);
				queue.remove();
			}
		}

		private void doRead() throws IOException {
			if (sc.read(bufferIn) == -1) {
				logger.info("connexion closed");
				closed = true;
			}
			processOut();
			updateInterestOps();
		}

		private void doWrite() throws IOException {
			bufferOut.flip();
			sc.write(bufferOut);
			bufferOut.compact();
			processIn();
			updateInterestOps();
		}

		private void queueMessage(Message msg) {
			queue.add(msg);
			processOut();
			updateInterestOps();
		}

		private void updateInterestOps() {
			var newInterestOps = 0;

			if (bufferIn.hasRemaining() && !closed) {
				newInterestOps |= SelectionKey.OP_READ;
			}
			if (bufferOut.position() != 0) {
				newInterestOps |= SelectionKey.OP_WRITE;
			}
			if (newInterestOps == 0) {
				silentlyClose();
				return;
			}
			key.interestOps(newInterestOps);
		}
		
		private void silentlyClose() {
			try {
				sc.close();
			} catch (IOException e) {
				// ignore exception
			}
		}

		
	}

	private final ServerSocketChannel serverChannel;
	private final Selector selector;

	public ServerChaton(int port) throws IOException {
		serverChannel = ServerSocketChannel.open();
		serverChannel.bind(new InetSocketAddress(port));
		selector = Selector.open();
	}

	public void launch() throws IOException {
		serverChannel.configureBlocking(false);
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		while (!Thread.interrupted()) {
			Helpers.printKeys(selector); // for debug
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
		Helpers.printSelectedKey(key); // for debug
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
			silentlyClose(key);
		}
	}
	
	private void doAccept(SelectionKey key) throws IOException {
		var scc = (ServerSocketChannel) key.channel();
		var sc = scc.accept();
		if (sc == null) {
			return;
		}
		sc.configureBlocking(false);
		var clientKey = sc.register(selector, SelectionKey.OP_READ);
		clientKey.attach(new Context(clientKey,this));
	}
	
	private void broadcast(Message msg) {
		for (SelectionKey key : selector.keys()) {
			if (key.attachment() == null) {
				return;
			}
			var context = (Context) key.attachment();
			context.queueMessage(msg);
		}
	}
	
	private void silentlyClose(SelectionKey key) {
		Channel sc = (Channel) key.channel();
		try {
			sc.close();
		} catch (IOException e) {
			// ignore exception
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		if (args.length != 1) {
			usage();
			return;
		}
		new ServerChaton(Integer.parseInt(args[0])).launch();
	}

	private static void usage() {
		System.out.println("Usage : ServerChaton port");
	}
}
