package fr.uge.net.udp.ex2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.lang.invoke.VarHandle;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import fr.uge.net.udp.ex1.ServerIdUpperCaseUDP;



final class ServerIntSumUDP {

	private static final Logger logger = Logger.getLogger(ServerIdUpperCaseUDP.class.getName());
	private final static int PACKET_SIZE=21;
	private final static int BUFFER_SIZE=1024;
	private final ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
	private final DatagramChannel dc;
	
	private final HashMap<Long, Client> clients;

	
	public ServerIntSumUDP(int port) throws IOException {
		dc = DatagramChannel.open();
		dc.bind(new InetSocketAddress(port));
		this.clients = new HashMap<Long, Client>();
		logger.info("ServerIntSumUDP started on port " + port);
		
	}
	
	public void serve() throws IOException {
		while(true) {
			buffer.clear();
			var sender = dc.receive(buffer);
			if(sender == null | buffer.remaining() < PACKET_SIZE) {
				continue;
			}
			
			
			byte bytee = buffer.get();
			
			switch (bytee) {
				case (byte)1 -> {
					var sessionId = buffer.getLong();
					var idPosOper = buffer.getInt();
					var totalOper = buffer.getInt();
					var opValue = buffer.getInt();
					
					var client = clients.putIfAbsent(sessionId, new Client(idPosOper, totalOper, opValue));

					if(client == null) {
						client = clients.get(sessionId);
					}
					
					if(!client.Allreceive()) {
						if(client.checkReceive(idPosOper)) {
							continue;
						}
						client.receive(idPosOper);
						client.addToOpValue(opValue);
						var sendBuffer2 = ByteBuffer.allocateDirect(BUFFER_SIZE);
						sendBuffer2.put((byte) 2);
						sendBuffer2.putLong(sessionId);
						sendBuffer2.putInt(client.getOpvalue());
						sendBuffer2.flip();
						logger.info("Server sending" + sendBuffer2.remaining()+ "bytes to " + sender.toString());
						
						dc.send(sendBuffer2, sender);
					}
					
					if(client.Allreceive()) {
						var sendBuffer3 = ByteBuffer.allocateDirect(BUFFER_SIZE);
						sendBuffer3.put((byte) 3);
						sendBuffer3.putLong(sessionId);
						sendBuffer3.putInt(client.getOpvalue());
						
						sendBuffer3.flip();
						logger.info("Server sending" + sendBuffer3.remaining()+ "bytes to " + sender.toString());
						
						dc.send(sendBuffer3, sender);
					}
					
				}
				default -> {}
			}
			
		}
	}
	
	
	 public static void usage() {
	        System.out.println("Usage : ServerIdSumUDP port");
	    }
	
	public static void main(String[] args) {
		if (args.length != 1) {
            usage();
            return;
        }

        var port = Integer.parseInt(args[0]);

        if (!(port >= 1024) & port <= 65535) {
            logger.severe("The port number must be between 1024 and 65535");
            return;
        }

        try {
            new ServerIntSumUDP(port).serve();
        } catch (BindException e) {
            logger.severe("Server could not bind on " + port + "\nAnother server is probably running on this port.");
            return;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Server stopped due to en I/O Exception.", e);
            return;
        }
	}
}
