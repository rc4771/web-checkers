package com.webcheckers.util.exceptions.moves;

/**
 * It is not the current player's turn to move
 *
 * @author Mike White
 */
public class NotTurnMoveException extends MoveException {
	private static String MESSAGE = "It is not your turn, please wait for the other player to finish their turn";

	public NotTurnMoveException() {
		super(MESSAGE);
	}
}
