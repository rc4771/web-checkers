package com.webcheckers.util.exceptions.moves;

/**
 * The piece that was supposed to move doesn't exist
 *
 * @author Mike White
 */
public class PieceNullMoveException extends MoveException {

	private static String MESSAGE = "Please reset your move before trying to move again by using the Backup button";

	/**
	 * Creates a new error
	 */
	public PieceNullMoveException() {
		super(MESSAGE);
	}
}
