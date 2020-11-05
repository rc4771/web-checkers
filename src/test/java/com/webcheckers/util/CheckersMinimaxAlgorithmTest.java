package com.webcheckers.util;

import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Piece.PieceColor;
import com.webcheckers.model.Position;
import org.junit.jupiter.api.Test;

import static com.webcheckers.model.Piece.PieceColor.RED;
import static com.webcheckers.model.Piece.PieceColor.WHITE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * test the minimax algorithm
 *
 * @author Mike White
 */
class CheckersMinimaxAlgorithmTest {

	@Test
	//
	/**
	 * not much depends on the minimax function being good, and it's impossible to test, robust tests are not necessary
	 */
	public void testSpeed_bestMove() {
		Board board = new Board();
		board.movePiece(CheckersMinimaxAlgorithm.bestMove(board, RED, 0).getMove());
		board.movePiece(CheckersMinimaxAlgorithm.bestMove(board, WHITE, 1).getMove());
		board.movePiece(CheckersMinimaxAlgorithm.bestMove(board, RED, 2).getMove());
		board.movePiece(CheckersMinimaxAlgorithm.bestMove(board, WHITE, 3).getMove());
		board.movePiece(CheckersMinimaxAlgorithm.bestMove(board, RED, 4).getMove());
		board.movePiece(CheckersMinimaxAlgorithm.bestMove(board, WHITE, 5).getMove());
		board.movePiece(CheckersMinimaxAlgorithm.bestMove(board, RED, 6).getMove());
		board.movePiece(CheckersMinimaxAlgorithm.bestMove(board, WHITE, 7).getMove());
	}

	/**
	 * Tests to make sure bestMove gives valid moves
	 */
	@Test
	public void testValid_bestMove() {
		Board board = new Board();
		for (int i = 0; i < 4; i++) {
			Move move = CheckersMinimaxAlgorithm.bestMove(board, RED, i).getMove();
			assertTrue(MoveValidator.isValidMove(board, RED, move.getStart(), move.getEnd()));
			board.movePiece(move);
			move = CheckersMinimaxAlgorithm.bestMove(board, WHITE, i).getMove();
			assertTrue(MoveValidator.isValidMove(board, WHITE, move.getStart(), move.getEnd()));
			board.movePiece(move);
		}
	}

	@Test
	public void test_calculateBoardValue() {
		Board board = new Board();

		assertEquals(0, CheckersMinimaxAlgorithm.calculateBoardValue(board, RED));
		assertEquals(0, CheckersMinimaxAlgorithm.calculateBoardValue(board, WHITE));

		board.removePiece(0, 1);

		assertEquals(-1, CheckersMinimaxAlgorithm.calculateBoardValue(board, RED));
		assertEquals(1, CheckersMinimaxAlgorithm.calculateBoardValue(board, WHITE));

		board.removePiece(7, 0);
		board.removePiece(7, 2);

		assertEquals(1, CheckersMinimaxAlgorithm.calculateBoardValue(board, RED));
		assertEquals(-1, CheckersMinimaxAlgorithm.calculateBoardValue(board, WHITE));
	}

	@Test
	public void test_calculateMoveValue() {
		Board board = new Board();
		Move move0 = new Move(new Position(7, 0), new Position(3, 2));
		board.movePiece(move0);
		Move move1 = new Move(new Position(2, 1), new Position(4, 3), new Move.PieceCapture(board.getPieceAt(3, 2), new Position(3, 2)));

		assertEquals(1, CheckersMinimaxAlgorithm.calculateMoveValue(board, move1, RED));
		assertEquals(-1, CheckersMinimaxAlgorithm.calculateMoveValue(board, move1, WHITE));
	}
}