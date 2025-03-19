package fr.uge.net.buffers.ex1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.*;

public class StoreWithByteOrder {

	public static void usage() {
		System.out.println("StoreWithByteOrder [LE|BE] filename");
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			usage();
			return;
		}
		var path = Path.of(args[1]);

		// TODO
		var byteBuffer = ByteBuffer.allocate(1024);
		

		switch (args[0].toUpperCase()) {
		case "LE":
			byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
			break;
		case "BE":
//			byteBuffer.order(ByteOrder.BIG_ENDIAN);
			break;
		default:
			System.out.println("Unrecognized option : " + args[0]);
			usage();
			return;
		}

		try (var outChannel = FileChannel.open(path, WRITE, CREATE, TRUNCATE_EXISTING);
				var scanner = new Scanner(System.in)) {
			while (scanner.hasNextLong()) {
				var l = scanner.nextLong();
				if(byteBuffer.remaining() < Long.BYTES){
					byteBuffer.flip();
					outChannel.write(byteBuffer);
					byteBuffer.clear();
				}
				byteBuffer.putLong(l);
			}
			byteBuffer.flip();
			outChannel.write(byteBuffer);
		}
		
	}
}