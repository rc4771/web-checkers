package com.webcheckers.util;

import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs the minimax algorithm for a game of checkers
 *
 * @author Mike White
 */
public class CheckersMinimaxAlgorithm {

	/**
	 * The possible move and its value
	 *
	 * @author Mike White
	 */
	static class MovePossibility {
		private Move move;
		private int value;

		/**
		 * Constructs a move possibility
		 * @param move The move
		 * @param value The value of the move
		 */
		public MovePossibility(Move move, int value) {
			this.move = move;
			this.value = value;
		}

		/**
		 * The move that was made
		 * @return the move
		 */
		public Move getMove() {
			return move;
		}

		/**
		 * The value of the move
		 * @return the value
		 */
		public int getValue() {
			return value;
		}
	}

	/**
	 * Calculates the best move for the player to make
	 * @param board The board being played on
	 * @param player The player whose turn it is
	 * @param movesToLookAhead The number of moves to look ahead
	 * @return The best move to make, and its value
	 */
	public static MovePossibility bestMove(Board board, Piece.PieceColor player, int movesToLookAhead) {
		List<Move> moveList = MoveValidator.calculateValidMoves(board, player);
		List<Integer> moveValues = new ArrayList<>(moveList.size());

		// calculate move values
		if (movesToLookAhead == 0) {
			for (int i = 0; i < moveList.size(); i++) {
				moveValues.add(calculateMoveValue(board, moveList.get(i), player));
			}
		} else {
			for (int i = 0; i < moveList.size(); i++) {
				Board newBoard = new Board(board);
				newBoard.movePiece(moveList.get(i));
				MovePossibility bestMove = bestMove(newBoard, player.opposite(), movesToLookAhead - 1);
				moveValues.add(-bestMove.getValue());
			}
		}

		// calculate max value
		int max = -24;
		for (int i = 0; i < moveList.size(); i++) {
			if (moveValues.get(i) > max) {
				max = moveValues.get(i);
			}
		}

		// get best moves
		List<Move> maxMoves = new ArrayList<>(moveList.size());
		for (int i = 0; i < moveList.size(); i++) {
			if (moveValues.get(i) == max) {
				maxMoves.add(moveList.get(i));
			}
		}

		// pick a move
		int random = (int) Math.floor(Math.random() * maxMoves.size());
		return new MovePossibility(moveList.get(random), max);
	}

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
