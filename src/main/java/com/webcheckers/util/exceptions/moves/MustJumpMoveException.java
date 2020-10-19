package com.webcheckers.util.exceptions.moves;

/**
 * The player moved forward one space, when they should've jumped
 *
 * @author Mike White
 */
public class MustJumpMoveException extends MoveException {
	private static String MESSAGE = "You made a single move, even though you can make a jump. Please jump";

	/**
	 * Creates a new exception
	 */
	public MustJumpMoveException() {
		super(MESSAGE);
	}
}
