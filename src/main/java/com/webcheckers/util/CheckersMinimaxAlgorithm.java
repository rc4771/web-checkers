package com.webcheckers.util;

import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;

/**
 * Performs the minimax algorithm for a game of checkers
 *
 * @author Mike White
 */
public class CheckersMinimaxAlgorithm {

	/**
	 * Calculates the value of the board for a given player
	 * The value is the number of the player's pieces, minus the number of opponent pieces
	 *
	 * @param board The board
	 * @param player The player
	 * @return The value of the board for the player
	 */
	public static int calculateBoardValue(Board board, Piece.PieceColor player) {
		int value = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.getPieceColorAt(i, j) == player) {
					value ++;
				} else if (board.getPieceColorAt(i, j) == player.opposite()) {
					value --;
				}
			}
		}

		return value;
	}

	/**
	 * Calculates the value of the board after a move is made
	 * @param board The board the move is made on
	 * @param move The move to make
	 * @param player The player to calculate the value for
	 * @return The new value of the board after the move
	 */
	public static int calculateMoveValue(Board board, Move move, Piece.PieceColor player) {
		Board newBoard = new Board(board);
		newBoard.movePiece(move);

		return calculateBoardValue(newBoard, player);
	}
}
