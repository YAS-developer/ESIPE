package fr.uge.net.tcp.nonblocking.ex32;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;




public class StringReader implements Reader<String>{
	
	 private enum State {
	        DONE, WAITING, ERROR
	    };
	
	private State state = State.WAITING;
	private String message;
	private final ByteBuffer internalBuffer = ByteBuffer.allocateDirect(1024);
	
	
	
	@Override
	public ProcessStatus process(ByteBuffer bb) {
		if (state == State.DONE || state == State.ERROR) {
            throw new IllegalStateException();
        }
		bb.flip();
	
		if(bb.remaining() > 1024) {
			return ProcessStatus.ERROR;
		}
		
		var size = bb.getInt();
		
		for(int i=0; i<size; i++) {
			internalBuffer.put(bb.get());
		}
		
		
		
		
		
		if(internalBuffer.hasRemaining()) {
			return ProcessStatus.REFILL;
		}
		
		internalBuffer.flip();
		
		message = StandardCharsets.UTF_8.decode(internalBuffer).toString();
		state = State.DONE;
		
		return ProcessStatus.DONE;
	}

	@Override
	public String get() {
		if (state != State.DONE) {
            throw new IllegalStateException();
        }
		return message;
	}

	@Override
	public void reset() {
		state = State.WAITING;
        internalBuffer.clear();
	}

}
