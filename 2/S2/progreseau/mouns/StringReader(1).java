package fr.uge.net.tcp.nonblocking;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;


public class StringReader implements Reader<String> {

	private enum State {
		DONE, WAITING_FOR_SIZE, WAITING_FOR_CONTENT, ERROR
	};

	private State state = State.WAITING_FOR_SIZE;
	private final IntReader intReader = new IntReader();
	private final ByteBuffer internalBuffer = ByteBuffer.allocate(1024); // write-mode
	private String value;
	private int size;

	@Override
	public ProcessStatus process(ByteBuffer bb) {
		if (state == State.DONE || state == State.ERROR) {
			throw new IllegalStateException();
		}

		if (state == State.WAITING_FOR_SIZE) {
			var status = intReader.process(bb);
			if (status == ProcessStatus.REFILL) {
				return ProcessStatus.REFILL;
			}
			size = intReader.get();
			if (size < 0 || size > 1024) {
				state = State.ERROR;
				return ProcessStatus.ERROR;
			}
			internalBuffer.clear();
			internalBuffer.limit(size);
			state = State.WAITING_FOR_CONTENT;
		}

		if (state == State.WAITING_FOR_CONTENT) {
			bb.flip();
			try {
				if (bb.remaining() <= internalBuffer.remaining()) {
					internalBuffer.put(bb);
				} else {
					var oldLimit = bb.limit();
					bb.limit(bb.position() + internalBuffer.remaining());
					internalBuffer.put(bb);
					bb.limit(oldLimit);
				}
				
			} finally {
				bb.compact();
			}
			if (internalBuffer.hasRemaining()) {
				return ProcessStatus.REFILL;
			}
			
			internalBuffer.flip();
			value= StandardCharsets.UTF_8.decode(internalBuffer).toString();
			state = State.DONE;
			return ProcessStatus.DONE;
		}
		return ProcessStatus.ERROR;
	}

	@Override
	public String get() {
		if (state != State.DONE) {
			throw new IllegalStateException();
		}
		return value;
	}

	@Override
	public void reset() {
		intReader.reset();
		state = State.WAITING_FOR_SIZE;
		internalBuffer.clear();
	}

}
