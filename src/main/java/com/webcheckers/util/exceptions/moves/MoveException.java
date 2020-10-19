package com.webcheckers.util.exceptions.moves;

import com.webcheckers.util.Message;

/**
 * An error in moving a piece
 *
 * @author Mike White
 */
public abstract class MoveException extends Exception {

	/** The reason why the error occurred */
	private String message;

	/**
	 * Creates a new move exception
	 * @param message The reason for the move error
	 */
	protected MoveException(String message) {
		this.message = message;
	}

	/**
	 * The message for the exception
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Creates an error message
	 * @return The message
	 */
	public Message toMessage() {
		return Message.error(message);
	}
}
