package fr.uge.net.tcp.nonblocking;

import java.nio.ByteBuffer;

public class MessageReader implements Reader<Message> {

	private enum State {
		DONE, WAITING_FOR_LOGIN, WAITING_FOR_TEXT, ERROR
	};

	private State state = State.WAITING_FOR_LOGIN;
	private final StringReader stringReader = new StringReader();
	private Message message;
	private String login;
	private String text;

	@Override
	public ProcessStatus process(ByteBuffer bb) {
		if (state == State.DONE || state == State.ERROR) {
			throw new IllegalStateException();
		}

		if (state == State.WAITING_FOR_LOGIN) {
			var loginStatus = stringReader.process(bb);
			if (loginStatus == ProcessStatus.REFILL) {
				return ProcessStatus.REFILL;
			}
			if (loginStatus == ProcessStatus.ERROR) {
				state = State.ERROR;
				return ProcessStatus.ERROR;
			}
			login = stringReader.get();
			state = State.WAITING_FOR_TEXT;
			stringReader.reset();
		}

		if (state == State.WAITING_FOR_TEXT) {
			var textStatus = stringReader.process(bb);
			if (textStatus == ProcessStatus.REFILL) {
				return ProcessStatus.REFILL;
			}
			if (textStatus == ProcessStatus.ERROR) {
				state = State.ERROR;
				return ProcessStatus.ERROR;
			}
			text = stringReader.get();
			message = new Message(login, text);
			state = State.DONE;
			return ProcessStatus.DONE;
		}

		return ProcessStatus.ERROR;
	}

	@Override
	public Message get() {
		if (state != State.DONE) {
			throw new IllegalStateException();
		}
		return message;
	}

	@Override
	public void reset() {
		stringReader.reset();
		login = null;
		text = null;
		message = null;
		state = State.WAITING_FOR_LOGIN;
	}

}
