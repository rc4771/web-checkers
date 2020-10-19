package com.webcheckers.util.exceptions.moves;

/**
 * The piece is not allowed to move in the given direction
 *
 * @author Mike White
 */
public class MoveDirectionMoveException extends MoveException {
	private static String MESSAGE = "That type of piece cannot move in that direction";

	/**
	 * Creates a new exception
	 */
	public MoveDirectionMoveException() {
		super(MESSAGE);
	}
}
