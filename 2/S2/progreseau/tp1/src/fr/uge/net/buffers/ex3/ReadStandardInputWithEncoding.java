package fr.uge.net.buffers.ex3;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

public class ReadStandardInputWithEncoding {

	private static final int BUFFER_SIZE = 1024;

	private static void usage() {
		System.out.println("Usage: ReadStandardInputWithEncoding charset");
	}

	private static String stringFromStandardInput(Charset cs) throws IOException {
		var byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);	
		var in = Channels.newChannel(System.in);
		var str = new StringBuilder();
		while(in.read(byteBuffer) != -1) {
			if(!byteBuffer.hasRemaining()) {
				byteBuffer.clear();
				var cb = cs.decode(byteBuffer);
				str.append(cb.toString());
				byteBuffer.clear();			
			}
		}
		byteBuffer.flip();
		var cb = cs.decode(byteBuffer);
		str.append(cb.toString());
		
		return str.toString();
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			usage();
			return;
		}
		Charset cs = Charset.forName(args[0]);
		System.out.print(stringFromStandardInput(cs));
	}
}
