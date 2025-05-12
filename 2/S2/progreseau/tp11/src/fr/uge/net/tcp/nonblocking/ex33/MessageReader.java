package fr.uge.net.tcp.nonblocking.ex33;


import java.nio.ByteBuffer;

import fr.uge.net.tcp.nonblocking.ex32.Reader;
import fr.uge.net.tcp.nonblocking.ex32.StringReader;

public class MessageReader implements Reader<Message>{

	
	private enum State { WAITING_FOR_LOGIN, WAITING_FOR_TEXT, REFILL, DONE, ERROR};
	
	
	private State state = State.WAITING_FOR_LOGIN;
	
	private final StringReader stringReader = new StringReader();
	private final int SIZE_BUFFER = 1024;
	private final ByteBuffer internalBuffer = ByteBuffer.allocateDirect(SIZE_BUFFER);
	private String login;
	private String texteString;
	
	
	@Override
	public ProcessStatus process(ByteBuffer bb) {
		if(state == State.ERROR) {
			return ProcessStatus.ERROR;
		}
		
		if(state == State.WAITING_FOR_LOGIN) {
			
			var statue = stringReader.process(bb);
			if(statue == ProcessStatus.REFILL) {
				return ProcessStatus.REFILL;
			}
			
			
			
			
			
			state = State.WAITING_FOR_TEXT;
		}
		
		
		if(state == State.WAITING_FOR_TEXT) {
			
		}
		
		
		return null;
	}

	@Override
	public Message get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
}
