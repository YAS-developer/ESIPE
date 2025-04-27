package fr.uge.net.tcp.http;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.StringJoiner;

public class HTTPReader {

	private final Charset ASCII_CHARSET = Charset.forName("ASCII");
	private final SocketChannel sc;
	private final ByteBuffer buffer;

	public HTTPReader(SocketChannel sc, ByteBuffer buffer) {
		this.sc = sc;
		this.buffer = buffer;
	}

	/**
	 * @return The ASCII string terminated by CRLF without the CRLF
	 *         <p>
	 *         The method assume that buffer is in write mode and leaves it in write
	 *         mode The method process the data from the buffer and if necessary
	 *         will read more data from the socket.
	 * @throws IOException HTTPException if the connection is closed before a line
	 *                     could be read
	 */
	public String readLineCRLF() throws IOException {
		buffer.flip();

		try {
			var sb = new StringBuilder();
			var lastCR = false;
			while (true) {
				while (buffer.hasRemaining()) {
					var decodedByte = (char) buffer.get();
					sb.append(decodedByte);
					if (lastCR && decodedByte == '\n') {
						sb.setLength(sb.length() - 2);
						return sb.toString();
					}

					lastCR = (decodedByte == '\r');
				}
				buffer.clear();
				if (sc.read(buffer) == -1) {
					throw new HTTPException("Connection closed");
				}
				buffer.flip();
			}
		} finally {
			buffer.compact();
		}

	}

	/**
	 * @return The HTTPHeader object corresponding to the header read
	 * @throws IOException HTTPException if the connection is closed before a header
	 *                     could be read or if the header is ill-formed
	 */
	public HTTPHeader readHeader() throws IOException {
		var fields = new HashMap<String, String>();
		var state = readLineCRLF();
		String line;
		while (!(line = readLineCRLF()).isEmpty()) {
			var tab = line.split(":\s", 2);
			fields.merge(tab[0], tab[1], (t, u) -> t + ";" + u);
		}

		return HTTPHeader.create(state, fields);
	}

	/**
	 * @param size
	 * @return a ByteBuffer in write mode containing size bytes read on the socket
	 *         <p>
	 *         The method assume that buffer is in write mode and leaves it in write
	 *         mode The method process the data from the buffer and if necessary
	 *         will read more data from the socket.
	 * @throws IOException HTTPException is the connection is closed before all
	 *                     bytes could be read
	 */
	public ByteBuffer readBytes(int size) throws IOException {
		buffer.flip();
		var newBuffer = ByteBuffer.allocate(size);
		try {
			while (newBuffer.hasRemaining()) {
				if (!buffer.hasRemaining()) {
					buffer.clear();
					if (sc.read(buffer) == -1) {
						throw new HTTPException("Connection closed");
					}
					buffer.flip();
				}
				newBuffer.put(buffer.get());

			}
			return newBuffer;
		} finally {
			buffer.compact();
		}
	}

	/**
	 * @return a ByteBuffer in write-mode containing a content read in chunks mode
	 * @throws IOException HTTPException if the connection is closed before the end
	 *                     of the chunks if chunks are ill-formed
	 */

	public ByteBuffer readChunks() throws IOException {
		buffer.flip();
		var newBuffer = ByteBuffer.allocate(1024);
		var size = 0;
		try {
			String sizeChunk;
			while(!(sizeChunk = readLineCRLF()).equals("0")) {
				size = Integer.parseInt(sizeChunk,16);
				if(newBuffer.remaining() < size) {
					newBuffer = resizeBuffer(newBuffer,size);
				}
				for(var i = 0; i< size; i++) {
					if (!buffer.hasRemaining()) {
						buffer.clear();
						if (sc.read(buffer) == -1) {
							throw new HTTPException("Connection closed");
						}
						buffer.flip();
					}
					newBuffer.put(buffer.get());
				}
			}
		} finally {
			buffer.compact();
		}
		return newBuffer;
	}

	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int remainingSize) {
		var newBuffer = ByteBuffer.allocate(buffer.capacity() + remainingSize);
		buffer.flip();
		newBuffer.put(buffer);
		
		return newBuffer;
		
	}

	public static void main(String[] args) throws IOException {
		var charsetASCII = Charset.forName("ASCII");
		var request = "GET / HTTP/1.1\r\n" + "Host: www.w3.org\r\n" + "\r\n";
		var sc = SocketChannel.open();
		sc.connect(new InetSocketAddress("www.w3.org", 80));
		sc.write(charsetASCII.encode(request));
		var buffer = ByteBuffer.allocate(50);
		var reader = new HTTPReader(sc, buffer);
		System.out.println(reader.readLineCRLF());
		System.out.println(reader.readLineCRLF());
		System.out.println(reader.readLineCRLF());
		sc.close();

		buffer = ByteBuffer.allocate(50);
		sc = SocketChannel.open();
		sc.connect(new InetSocketAddress("www.w3.org", 80));
		reader = new HTTPReader(sc, buffer);
		sc.write(charsetASCII.encode(request));
		System.out.println(reader.readHeader());
		sc.close();

		buffer = ByteBuffer.allocate(50);
		sc = SocketChannel.open();
		sc.connect(new InetSocketAddress("igm.univ-mlv.fr", 80));
		request = "GET /coursprogreseau/ HTTP/1.1\r\n" + "Host: igm.univ-mlv.fr\r\n" + "\r\n";
		reader = new HTTPReader(sc, buffer);
		sc.write(charsetASCII.encode(request));
		var header = reader.readHeader();
		System.out.println(header);
		var content = reader.readBytes(header.getContentLength());
		content.flip();
		System.out.println(header.getCharset().orElse(Charset.forName("UTF8")).decode(content));
		sc.close();

		buffer = ByteBuffer.allocate(50);
		request = "GET / HTTP/1.1\r\n" + "Host: www.u-pem.fr\r\n" + "\r\n";
		sc = SocketChannel.open();
		sc.connect(new InetSocketAddress("www.u-pem.fr", 80));
		reader = new HTTPReader(sc, buffer);
		sc.write(charsetASCII.encode(request));
		header = reader.readHeader();
		System.out.println(header);
		content = reader.readChunks();
		content.flip();
		System.out.println(header.getCharset().orElse(Charset.forName("UTF8")).decode(content));
		sc.close();
	}
}
