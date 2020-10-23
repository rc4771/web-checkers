package com.webcheckers.util;

import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckersMinimaxAlgorithmTest {
	@Test
	public void test_calculateValue() {
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
}