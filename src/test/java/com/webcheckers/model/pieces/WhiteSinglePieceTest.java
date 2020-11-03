package com.webcheckers.model.pieces;

import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Position;
import com.webcheckers.model.spaces.WhiteSpace;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("Model-tier")
class WhiteSinglePieceTest {

	/**
	 * Test if the piece has the white color.
	 */
	@Test
	public void colorTestWhite() {
		Piece piece = new WhiteSinglePiece();
		assertTrue(piece.getColor() == Piece.PieceColor.WHITE);
	}

	@Test
	public void test_promote() {
		WhiteSinglePiece CuT = new WhiteSinglePiece();
		assertTrue(CuT.promote() instanceof WhiteKingPiece);
	}

	@Test
	public void testRight_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(0, 1), new Position(4, 3)));
		Position piecePosition = new Position(5, 2);
		Piece piece = board.getPieceAt(5, 2);

		List<Move> jumps = piece.getJumps(board, piecePosition);

		assertEquals(1, jumps.size());

		Move jump = jumps.get(0);

		assertEquals(new Position(5, 2), jump.getStart());
		assertEquals(new Position(3, 4), jump.getEnd());
		assertEquals(1, jump.getCaptures().size());

		Move.PieceCapture capture = jump.getCaptures().get(0);

		assertTrue(capture.getPiece() instanceof RedSinglePiece);
		assertEquals(new Position(4, 3), capture.getPosition());
	}

	@Test
	public void testLeft_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(0, 1), new Position(4, 3)));
		Position piecePosition = new Position(5, 4);
		Piece piece = board.getPieceAt(5, 4);

		List<Move> jumps = piece.getJumps(board, piecePosition);

		assertEquals(1, jumps.size());

		Move jump = jumps.get(0);

		assertEquals(new Position(5, 4), jump.getStart());
		assertEquals(new Position(3, 2), jump.getEnd());
		assertEquals(1, jump.getCaptures().size());

		Move.PieceCapture capture = jump.getCaptures().get(0);

		assertTrue(capture.getPiece() instanceof RedSinglePiece);
		assertEquals(new Position(4, 3), capture.getPosition());
	}

	@Test
	public void testBackward_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(5, 4), new Position(4, 3)));
		board.movePiece(new Move(new Position(0, 1), new Position(5, 4)));
		board.removePiece(6, 5);
		Position piecePosition = new Position(5, 4);
		Piece piece = board.getPieceAt(5, 4);

		List<Move> jumps = piece.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	@Test
	public void testLeftWhitePiece_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(7, 0), new Position(4, 3)));
		Position piecePosition = new Position(5, 4);
		Piece piece = board.getPieceAt(5, 4);

		List<Move> jumps = piece.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	@Test
	public void testRightWhitePiece_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(7, 0), new Position(4, 3)));
		Position piecePosition = new Position(5, 2);
		Piece piece = board.getPieceAt(5, 2);

		List<Move> jumps = piece.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	@Test
	public void testLeftBlocked_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(7, 0), new Position(3, 2)));
		board.movePiece(new Move(new Position(0, 1), new Position(4, 3)));
		Position piecePosition = new Position(5, 4);
		Piece piece = board.getPieceAt(5, 4);

		List<Move> jumps = piece.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	@Test
	public void testRightBlocked_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(0, 1), new Position(4, 3)));
		board.movePiece(new Move(new Position(7, 0), new Position(3, 4)));
		Position piecePosition = new Position(5, 2);
		Piece piece = board.getPieceAt(5, 2);

		List<Move> jumps = piece.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	@Test
	public void testLeftBounds_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(0, 1), new Position(4, 7)));
		Position piecePosition = new Position(5, 6);
		Piece piece = board.getPieceAt(5, 6);

		List<Move> jumps = piece.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	@Test
	public void testRightBounds_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(0, 1), new Position(3, 0)));
		board.movePiece(new Move(new Position(7, 0), new Position(4, 1)));
		Position piecePosition = new Position(4, 1);
		Piece piece = board.getPieceAt(4, 1);

		List<Move> jumps = piece.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	@Test
	public void testDoubleJumpLeft_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(0, 1), new Position(4, 3)));
		board.removePiece(1, 0);
		Position piecePosition = new Position(5, 4);
		Piece piece = board.getPieceAt(5, 4);

		List<Move> jumps = piece.getJumps(board, piecePosition);

		assertEquals(1, jumps.size());

		Move jump = jumps.get(0);

		assertEquals(new Position(5, 4), jump.getStart());
		assertEquals(new Position(1, 0), jump.getEnd());
		assertEquals(2, jump.getCaptures().size());

		List<Move.PieceCapture> captures = jump.getCaptures();
		Move.PieceCapture capture1 = captures.get(0);

		assertEquals(new Position(4, 3), capture1.getPosition());
		assertTrue(capture1.getPiece() instanceof RedSinglePiece);

		Move.PieceCapture capture2 = captures.get(1);

		assertEquals(new Position(2, 1), capture2.getPosition());
		assertTrue(capture2.getPiece() instanceof RedSinglePiece);
	}

	@Test
	public void testDoubleJumpRight_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(0, 1), new Position(4, 3)));
		board.removePiece(1, 6);
		Position piecePosition = new Position(5, 2);
		Piece piece = board.getPieceAt(5, 2);

		List<Move> jumps = piece.getJumps(board, piecePosition);

		assertEquals(1, jumps.size());

		Move jump = jumps.get(0);

		assertEquals(new Position(5, 2), jump.getStart());
		assertEquals(new Position(1, 6), jump.getEnd());
		assertEquals(2, jump.getCaptures().size());

		List<Move.PieceCapture> captures = jump.getCaptures();
		Move.PieceCapture capture1 = captures.get(0);

		assertEquals(new Position(4, 3), capture1.getPosition());
		assertTrue(capture1.getPiece() instanceof RedSinglePiece);

		Move.PieceCapture capture2 = captures.get(1);

		assertEquals(new Position(2, 5), capture2.getPosition());
		assertTrue(capture2.getPiece() instanceof RedSinglePiece);
	}

	@Test
	public void testTripleJumpLeft_calculateJump() {
		Board board = new Board();

		board.removePiece(6, 1);
		board.movePiece(new Move(new Position(0, 1), new Position(6, 1)));
		board.removePiece(5, 2);
		board.movePiece(new Move(new Position(0, 3), new Position(4, 3)));
		board.removePiece(1, 6);
		board.removePiece(0, 7);
		board.removePiece(0, 5);
		Position piecePosition = new Position(7, 0);
		Piece piece = board.getPieceAt(7, 0);

		List<Move> jumps = piece.getJumps(board, piecePosition);

		assertEquals(1, jumps.size());

		Move jump = jumps.get(0);

		assertEquals(new Position(7, 0), jump.getStart());
		assertEquals(new Position(1, 6), jump.getEnd());
		assertEquals(3, jump.getCaptures().size());

		List<Move.PieceCapture> captures = jump.getCaptures();
		Move.PieceCapture capture1 = captures.get(0);

		assertEquals(new Position(6, 1), capture1.getPosition());
		assertTrue(capture1.getPiece() instanceof RedSinglePiece);

		Move.PieceCapture capture2 = captures.get(1);

		assertEquals(new Position(4, 3), capture2.getPosition());
		assertTrue(capture2.getPiece() instanceof RedSinglePiece);

		Move.PieceCapture capture3 = captures.get(2);

		assertEquals(new Position(2, 5), capture3.getPosition());
		assertTrue(capture3.getPiece() instanceof RedSinglePiece);
	}

	@Test
	public void testTripleJumpRight_calculateJump() {
		Board board = new Board();

		board.removePiece(6, 5);
		board.movePiece(new Move(new Position(0, 1), new Position(6, 5)));
		board.removePiece(5, 4);
		board.movePiece(new Move(new Position(0, 3), new Position(4, 3)));
		board.removePiece(1, 0);
		board.removePiece(0, 1);
		Position piecePosition = new Position(7, 6);
		Piece piece = board.getPieceAt(7, 6);

		List<Move> jumps = piece.getJumps(board, piecePosition);

		assertEquals(1, jumps.size());

		Move jump = jumps.get(0);

		assertEquals(new Position(7, 6), jump.getStart());
		assertEquals(new Position(1, 0), jump.getEnd());
		assertEquals(3, jump.getCaptures().size());

		List<Move.PieceCapture> captures = jump.getCaptures();
		Move.PieceCapture capture1 = captures.get(0);

		assertEquals(new Position(6, 5), capture1.getPosition());
		assertTrue(capture1.getPiece() instanceof RedSinglePiece);

		Move.PieceCapture capture2 = captures.get(1);

		assertEquals(new Position(4, 3), capture2.getPosition());
		assertTrue(capture2.getPiece() instanceof RedSinglePiece);

		Move.PieceCapture capture3 = captures.get(2);

		assertEquals(new Position(2, 1), capture3.getPosition());
		assertTrue(capture3.getPiece() instanceof RedSinglePiece);
	}
}