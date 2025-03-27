	package fr.uge.net.buffers.ex2;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ReadFileWithEncoding {

	private static void usage() {
		System.out.println("Usage: ReadFileWithEncoding charset filename");
	}

	private static String stringFromFile(Charset cs, Path path) throws IOException {
		var byteBuffer = ByteBuffer.allocate(1024);
		var str = new StringBuilder();
		try(var readChannel = FileChannel.open(path, StandardOpenOption.READ)){	
			while(readChannel.read(byteBuffer) > 0) {
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
		}
		return str.toString();

	}

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			usage();
			return;
		}
		var cs = Charset.forName(args[0]);
		var path = Path.of(args[1]);
		System.out.print(stringFromFile(cs, path));
	}
}

