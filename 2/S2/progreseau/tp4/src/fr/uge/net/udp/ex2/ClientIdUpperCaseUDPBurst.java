package fr.uge.net.udp.ex2;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class ClientIdUpperCaseUDPBurst {

  private static Logger logger = Logger.getLogger(ClientIdUpperCaseUDPBurst.class.getName());
  private static final Charset UTF8 = StandardCharsets.UTF_8;
  private static final int BUFFER_SIZE = 1024;
  private final List<String> lines;
  private final int nbLines;
  private final String[] upperCaseLines; //
  private final int timeout;
  private final String outFilename; 
  private final InetSocketAddress serverAddress;
  private final DatagramChannel dc;
  private final AnswersLog answersLog;         // Thread-safe structure keeping track of missing responses

  public static void usage() {
    System.out.println("Usage : ClientIdUpperCaseUDPBurst in-filename out-filename timeout host port ");
  }

  public ClientIdUpperCaseUDPBurst(List<String> lines,int timeout,InetSocketAddress serverAddress,String outFilename) throws IOException {
    this.lines = lines;
    this.nbLines = lines.size();
    this.timeout = timeout;
    this.outFilename = outFilename;
    this.serverAddress = serverAddress;
    this.dc = DatagramChannel.open();
    dc.bind(null);
    this.upperCaseLines = new String[nbLines];
    this.answersLog = new AnswersLog(nbLines);
  }

  private void senderThreadRun() {

    try {

      var buffer = ByteBuffer.allocate(BUFFER_SIZE);


      while(!Thread.interrupted()) {
        
        var missingLines = answersLog.getMissingResponse();
        
        for(var missingLine : missingLines) {
            buffer.clear();
            buffer.putLong(missingLine);
            buffer.put(UTF8.encode(lines.get(missingLine)));
            buffer.flip();
            dc.send(buffer, serverAddress);
        }
        Thread.sleep(timeout);
      }
    }catch (InterruptedException e) {
      logger.info("Sender thread interrupted, stopping...");
    }catch (ClosedChannelException e) {
      logger.info("Listener thread stopping ...");
    }catch (IOException e) {
      logger.severe("Listener thread stopping due to unexpected IOException " + e);
    }
  }

  public void launch() throws IOException {
    Thread senderThread = Thread.ofPlatform().start(this::senderThreadRun);

    var buffer = ByteBuffer.allocate(BUFFER_SIZE);
    System.out.println("toto");
    while(!answersLog.isFull()) {
     

      buffer.clear();
      dc.receive(buffer);
      buffer.flip();

      if (buffer.remaining() < Long.BYTES) {
        logger.warning("Received malformed packet, ignoring...");
        continue;
      }
      
      long idLong = buffer.getLong();
      
      if (idLong < 0 || idLong >= nbLines) {
        logger.warning("Received packet with invalid ID: " + idLong);
        continue;
      }
      
      var id = Math.toIntExact(idLong);
      if(answersLog.contains(id)) {
        continue;
      }
      var msg = UTF8.decode(buffer).toString();
      System.out.print(msg);
      upperCaseLines[id] = msg;
      answersLog.addAnswer(id);
    }

    senderThread.interrupt();

    Files.write(Paths.get(outFilename),Arrays.asList(upperCaseLines), UTF8,
        StandardOpenOption.CREATE,
        StandardOpenOption.WRITE,
        StandardOpenOption.TRUNCATE_EXISTING);

  }

  public static void main(String[] args) throws IOException, InterruptedException {
    if (args.length !=5) {
      usage();
      return;
    }

    String inFilename = args[0];
    String outFilename = args[1];
    int timeout = Integer.valueOf(args[2]);
    String host=args[3];
    int port = Integer.valueOf(args[4]);
    InetSocketAddress serverAddress = new InetSocketAddress(host,port);

    //Read all lines of inFilename opened in UTF-8
    List<String> lines= Files.readAllLines(Paths.get(inFilename),UTF8);
    //Create client with the parameters and launch it
    ClientIdUpperCaseUDPBurst client = new ClientIdUpperCaseUDPBurst(lines,timeout,serverAddress,outFilename);
    client.launch();
  }

  private static class AnswersLog {

    private final int capacity;
//    private final Set<Long> lines;
    private final boolean lines[];
    private static final Object lock = new Object();
    private int size;

    public AnswersLog(int capacity) {
      this.capacity = capacity;
      this.lines = new boolean[capacity];
    }

    public void addAnswer(int id) {
      synchronized (lock) {
        if(!lines[id]) {
          lines[id] = true;
          size++;
        }
      }
    }
    
    public boolean contains(int id) {
      return lines[id];
    }

    public List<Integer> getMissingResponse() {
      var list = new ArrayList<Integer>();
      
      for(var i = 0; i < capacity; i++) {
        if(!lines[i]) {
          list.add(i);
        }
      }
      return list;
    }

    public boolean isFull() {
      synchronized (lock) {
        return size >= capacity;
      } 
    }
  }
}


