package fr.uge.net.udp.ex2;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Scanner;

public class NetcatUDP {
    public static final int BUFFER_SIZE = 1024;

    private static void usage() {
        System.out.println("Usage : NetcatUDP host port charset");
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            usage();
            return;
        }

        var server = new InetSocketAddress(args[0], Integer.parseInt(args[1]));
        var cs = Charset.forName(args[2]);
        var buffer = ByteBuffer.allocate(BUFFER_SIZE);
        try( var dc = DatagramChannel.open() ){
        	dc.bind(null);
        	 try (var scanner = new Scanner(System.in);) {
                 while (scanner.hasNextLine()) {
                     var line = scanner.nextLine();
                     buffer.put(line.getBytes());
                     buffer.flip();
                     dc.send(buffer, server);
                     buffer.clear();
                     dc.receive(buffer);
                     buffer.flip();
                     var cb = cs.decode(buffer);
                     System.out.println(cb.toString());
                     buffer.clear();
                 }
                 
             }
        }
    }
}
