package com.webcheckers.util.exceptions.moves;

/**
 * An invalid jump was made
 *
 * @author Mike White
 */
public class InvalidJumpMoveException extends MoveException {
	private static String MESSAGE = "That is not a valid jump move";

	/**
	 * Creates a new exception
	 */
	public InvalidJumpMoveException() {
		super(MESSAGE);
	}
}
