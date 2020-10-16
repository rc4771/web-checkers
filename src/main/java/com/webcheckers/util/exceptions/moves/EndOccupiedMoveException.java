package com.webcheckers.util.exceptions.moves;

/**
 * The position the piece is moving to is occupied
 *
 * @author Mike White
 */
public class EndOccupiedMoveException extends MoveException {
	private static String MESSAGE = "There is a piece at the space you're jumping to";

	/**
	 * Creates a new exception
	 */
	public EndOccupiedMoveException() {
		super(MESSAGE);
	}
}
