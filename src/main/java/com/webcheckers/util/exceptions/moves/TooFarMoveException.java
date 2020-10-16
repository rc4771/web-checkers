package com.webcheckers.util.exceptions.moves;

/**
 * The piece moved too far
 *
 * @author Mike White
 */
public class TooFarMoveException extends MoveException {
	private static String MESSAGE = "Your cannot move more than 1 space without jumping";

	public TooFarMoveException() {
		super(MESSAGE);
	}
}
