package com.webcheckers.util;

import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test the minimax algoritgm
 *
 * @author Mike White
 */
class CheckersMinimaxAlgorithmTest {

	@Test
	public void test_calculateBoardValue() {
		Board board = new Board();

		assertEquals(0, CheckersMinimaxAlgorithm.calculateBoardValue(board, Piece.PieceColor.RED));
		assertEquals(0, CheckersMinimaxAlgorithm.calculateBoardValue(board, Piece.PieceColor.WHITE));

		board.removePiece(0, 1);

		assertEquals(-1, CheckersMinimaxAlgorithm.calculateBoardValue(board, Piece.PieceColor.RED));
		assertEquals(1, CheckersMinimaxAlgorithm.calculateBoardValue(board, Piece.PieceColor.WHITE));

		board.removePiece(7, 0);
		board.removePiece(7, 2);

		assertEquals(1, CheckersMinimaxAlgorithm.calculateBoardValue(board, Piece.PieceColor.RED));
		assertEquals(-1, CheckersMinimaxAlgorithm.calculateBoardValue(board, Piece.PieceColor.WHITE));
	}

	@Test
	public void test_calculateMoveValue() {
		Board board = new Board();
		Move move0 = new Move(new Position(7, 0), new Position(3, 2));
		board.movePiece(move0);
		Move move1 = new Move(new Position(2, 1), new Position(4, 3), new Move.PieceCapture(board.getPieceAt(3, 2), new Position(3, 2)));

		assertEquals(1, CheckersMinimaxAlgorithm.calculateMoveValue(board, move1, Piece.PieceColor.RED));
		assertEquals(-1, CheckersMinimaxAlgorithm.calculateMoveValue(board, move1, Piece.PieceColor.WHITE));
	}
}