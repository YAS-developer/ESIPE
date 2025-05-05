package fr.upem.net.tcp.nonblocking;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Objects;
import java.util.logging.Logger;

public class ServerSum {

  private static final int BUFFER_SIZE = 2 * Integer.BYTES;
  private static final Logger logger = Logger.getLogger(ServerSum.class.getName());

  private final ServerSocketChannel serverSocketChannel;
  private final Selector selector;

  public ServerSum(int port) throws IOException {
    serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress(port));
    selector = Selector.open();
  }

  public void launch() throws IOException {
    serverSocketChannel.configureBlocking(false);
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    while (!Thread.interrupted()) {
      Helpers.printKeys(selector); // for debug
      System.out.println("Starting select");
      try {
        selector.select(this::treatKey);
      }catch(UncheckedIOException tunneled) {
        throw tunneled.getCause();  
      }
      
      System.out.println("Select finished");
    }
  }

  private void treatKey(SelectionKey key) {
    Helpers.printSelectedKey(key); // for debug
    if (key.isValid() && key.isAcceptable()) {
      try {
        doAccept(key);
      } catch (IOException ioe) {
        logger.severe("Accept IOException");
        throw new UncheckedIOException(ioe);
      }
    }
    if (key.isValid() && key.isWritable()) {
      try {
        doWrite(key);
      } catch (IOException e) {
        logger.info("write exception");
        silentlyClose(key);
      }
    }
    if (key.isValid() && key.isReadable()) {
      try {
        doRead(key);
      } catch (IOException e) {
        logger.info("read exception");
        silentlyClose(key);
      }
    }
  }

  private void doAccept(SelectionKey key) throws IOException {
    Objects.requireNonNull(key);
    var ssc = (ServerSocketChannel) key.channel();
    var sc = ssc.accept();
    if(sc == null) {
      return;
    }

    var buffer = ByteBuffer.allocate(BUFFER_SIZE);
    sc.configureBlocking(false);
    sc.register(selector, SelectionKey.OP_READ, buffer);
  }

  private void doRead(SelectionKey key) throws IOException {
    Objects.requireNonNull(key);
    var sc = (SocketChannel) key.channel();
    var buffer = (ByteBuffer) key.attachment();

    if(sc.read(buffer) == -1) {
      silentlyClose(key);
      return;
    }

    if(buffer.hasRemaining()) {
      return;
    }

    buffer.flip();

    var sum = buffer.getInt() + buffer.getInt();

    buffer.clear();
    buffer.putInt(sum);
    buffer.flip();

    key.interestOps(SelectionKey.OP_WRITE);
  }

  private void doWrite(SelectionKey key) throws IOException {
    Objects.requireNonNull(key);
    var sc = (SocketChannel) key.channel();
    var buffer = (ByteBuffer) key.attachment();
    
    sc.write(buffer);

    if(buffer.hasRemaining()) {
      return;
    }
    buffer.clear();
    key.interestOps(SelectionKey.OP_READ);
  }

  private void silentlyClose(SelectionKey key) {
    var sc = (Channel) key.channel();
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
    new ServerSum(Integer.parseInt(args[0])).launch();
  }

  private static void usage() {
    System.out.println("Usage : ServerSumOneShot port");
  }
}