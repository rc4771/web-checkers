package com.webcheckers.model.pieces;

import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KingPieceTest {

	/**
	 * Tests that a king can make a single move in each direction
	 */
	@Test
	public void testAll_singleMoves() {
		Board board = new Board();

		board.movePiece(new Move(new Position(0, 1), new Position(3, 4)));
		board.removePiece(2, 3);
		board.removePiece(2, 5);
		SinglePiece piece = (SinglePiece) board.getPieceAt(3, 4);
		KingPiece king = piece.promote();
		board.setPieceAt(3, 4, king);

		List<Move> moves = king.getSingleMoves(board, new Position(3, 4));
		assertEquals(4, moves.size());

		assertTrue(moves.get(0).getCaptures().isEmpty());
		assertTrue(moves.get(1).getCaptures().isEmpty());
		assertTrue(moves.get(2).getCaptures().isEmpty());
		assertTrue(moves.get(3).getCaptures().isEmpty());
	}

	/**
	 * Test that a king in the left corner cannot move
	 */
	@Test
	public void testLeftCorner_singleMoves() {
		Board board = new Board();

		SinglePiece piece = (SinglePiece) board.getPieceAt(0, 1);
		KingPiece king = piece.promote();
		board.setPieceAt(0, 1, king);

		List<Move> moves = king.getSingleMoves(board, new Position(0, 1));
		assertTrue(moves.isEmpty());
	}

	/**
	 * Test that a king in the right corner cannot make any moves
	 */
	@Test
	public void testRightCorner_singleMoves() {
		Board board = new Board();

		SinglePiece piece = (SinglePiece) board.getPieceAt(0, 7);
		KingPiece king = piece.promote();
		board.setPieceAt(0, 7, king);

		List<Move> moves = king.getSingleMoves(board, new Position(0, 7));
		assertTrue(moves.isEmpty());
	}

	/**
	 * Test that it can move forward left
	 */
	@Test
	public void testRedLeft_singleMoves() {
		Board board = new Board();

		SinglePiece piece = (SinglePiece) board.getPieceAt(2, 7);
		KingPiece king = piece.promote();
		board.setPieceAt(2, 7, king);

		List<Move> moves = king.getSingleMoves(board, new Position(2, 7));
		assertEquals(1, moves.size());
		Move move = moves.get(0);

		assertEquals(new Position(2, 7), move.getStart());
		assertEquals(new Position(3, 6), move.getEnd());
	}

	/**
	 * Test that it can move forward right
	 */
	@Test
	public void testRedRight_singleMoves() {
		Board board = new Board();

		SinglePiece piece = (SinglePiece) board.getPieceAt(2, 1);
		KingPiece king = piece.promote();
		board.setPieceAt(3, 0, king);

		List<Move> moves = king.getSingleMoves(board, new Position(3, 0));
		assertEquals(1, moves.size());
		Move move = moves.get(0);

		assertEquals(new Position(3, 0), move.getStart());
		assertEquals(new Position(4, 1), move.getEnd());
	}

	/**
	 * Test that it can move backward left
	 */
	@Test
	public void testWhiteLeft_singleMoves() {
		Board board = new Board();

		SinglePiece piece = (SinglePiece) board.getPieceAt(5, 0);
		KingPiece king = piece.promote();
		board.setPieceAt(5, 0, king);

		List<Move> moves = king.getSingleMoves(board, new Position(5, 0));
		assertEquals(1, moves.size());
		Move move = moves.get(0);

		assertEquals(new Position(5, 0), move.getStart());
		assertEquals(new Position(4, 1), move.getEnd());
	}

	/**
	 * Test that it can move backward right
	 */
	@Test
	public void testWhiteRight_singleMoves() {
		Board board = new Board();

		SinglePiece piece = (SinglePiece) board.getPieceAt(6, 1);
		KingPiece king = piece.promote();
		board.setPieceAt(4, 7, king);

		List<Move> moves = king.getSingleMoves(board, new Position(4, 7));
		assertEquals(1, moves.size());
		Move move = moves.get(0);

		assertEquals(new Position(4, 7), move.getStart());
		assertEquals(new Position(3, 6), move.getEnd());
	}

	/**
	 * Test that it can jump to the right
	 */
	@Test
	public void testRight_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(7, 6), new Position(3, 4)));
		Position piecePosition = new Position(2, 3);
		SinglePiece piece = (SinglePiece) board.getPieceAt(2, 3);
		KingPiece king = piece.promote();
		board.setPieceAt(2, 3, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertEquals(1, jumps.size());

		Move jump = jumps.get(0);

		assertEquals(new Position(2, 3), jump.getStart());
		assertEquals(new Position(4, 5), jump.getEnd());
		assertEquals(1, jump.getCaptures().size());

		Move.PieceCapture capture = jump.getCaptures().get(0);

		assertTrue(capture.getPiece() instanceof WhiteSinglePiece);
		assertEquals(new Position(3, 4), capture.getPosition());
	}

	/**
	 * Test that it can jump forward left
	 */
	@Test
	public void testLeft_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(7, 6), new Position(3, 4)));
		Position piecePosition = new Position(2, 5);
		SinglePiece piece = (SinglePiece) board.getPieceAt(2, 5);
		KingPiece king = piece.promote();
		board.setPieceAt(2, 5, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertEquals(1, jumps.size());

		Move jump = jumps.get(0);

		assertEquals(new Position(2, 5), jump.getStart());
		assertEquals(new Position(4, 3), jump.getEnd());
		assertEquals(1, jump.getCaptures().size());

		Move.PieceCapture capture = jump.getCaptures().get(0);

		assertTrue(capture.getPiece() instanceof WhiteSinglePiece);
		assertEquals(new Position(3, 4), capture.getPosition());
	}

	/**
	 * Test that it can jump backward left
	 */
	@Test
	public void testLeftWhitePiece_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(0, 1), new Position(3, 4)));
		Position piecePosition = new Position(2, 5);
		SinglePiece piece = (SinglePiece) board.getPieceAt(2, 5);
		KingPiece king = piece.promote();
		board.setPieceAt(2, 5, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	/**
	 * Test that it can jump backward right
	 */
	@Test
	public void testRightWhitePiece_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(0, 1), new Position(3, 4)));
		Position piecePosition = new Position(2, 3);
		SinglePiece piece = (SinglePiece) board.getPieceAt(2, 3);
		KingPiece king = piece.promote();
		board.setPieceAt(2, 3, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	/**
	 * Test that a left jump can be blocked
	 */
	@Test
	public void testLeftBlocked_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(0, 1), new Position(4, 3)));
		board.movePiece(new Move(new Position(0, 1), new Position(3, 4)));
		Position piecePosition = new Position(2, 5);
		SinglePiece piece = (SinglePiece) board.getPieceAt(2, 5);
		KingPiece king = piece.promote();
		board.setPieceAt(2, 5, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	/**
	 * Test that a right jump can be blocked
	 */
	@Test
	public void testRightBlocked_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(7, 6), new Position(3, 4)));
		board.movePiece(new Move(new Position(0, 1), new Position(4, 5)));
		Position piecePosition = new Position(2, 3);
		SinglePiece piece = (SinglePiece) board.getPieceAt(2, 3);
		KingPiece king = piece.promote();
		board.setPieceAt(2, 3, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	/**
	 * Test that the piece can't jump out of bounds to the left
	 */
	@Test
	public void testLeftBounds_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(7, 6), new Position(3, 0)));
		Position piecePosition = new Position(2, 1);
		SinglePiece piece = (SinglePiece) board.getPieceAt(2, 1);
		KingPiece king = piece.promote();
		board.setPieceAt(2, 1, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	/**
	 * Test that the piece can't move out of bounds to the right
	 */
	@Test
	public void testRightBounds_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(7, 0), new Position(4, 7)));
		board.movePiece(new Move(new Position(0, 1), new Position(3, 6)));
		Position piecePosition = new Position(3, 6);
		SinglePiece piece = (SinglePiece) board.getPieceAt(3, 6);
		KingPiece king = piece.promote();
		board.setPieceAt(3, 6, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	/**
	 * Test left double jumps
	 */
	@Test
	public void testDoubleJumpLeft_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(7, 6), new Position(3, 4)));
		board.removePiece(6, 1);
		Position piecePosition = new Position(2, 5);
		SinglePiece piece = (SinglePiece) board.getPieceAt(2, 5);
		KingPiece king = piece.promote();
		board.setPieceAt(2, 5, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertEquals(1, jumps.size());

		Move jump = jumps.get(0);

		assertEquals(new Position(2, 5), jump.getStart());
		assertEquals(new Position(6, 1), jump.getEnd());
		assertEquals(2, jump.getCaptures().size());

		List<Move.PieceCapture> captures = jump.getCaptures();
		Move.PieceCapture capture1 = captures.get(0);

		assertEquals(new Position(3, 4), capture1.getPosition());
		assertTrue(capture1.getPiece() instanceof WhiteSinglePiece);

		Move.PieceCapture capture2 = captures.get(1);

		assertEquals(new Position(5, 2), capture2.getPosition());
		assertTrue(capture2.getPiece() instanceof WhiteSinglePiece);
	}

	/**
	 * Test right double jumps
	 */
	@Test
	public void testDoubleJumpRight_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(7, 0), new Position(3, 4)));
		board.removePiece(6, 7);
		Position piecePosition = new Position(2, 3);
		SinglePiece piece = (SinglePiece) board.getPieceAt(2, 3);
		KingPiece king = piece.promote();
		board.setPieceAt(2, 3, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertEquals(1, jumps.size());

		Move jump = jumps.get(0);

		assertEquals(new Position(2, 3), jump.getStart());
		assertEquals(new Position(6, 7), jump.getEnd());
		assertEquals(2, jump.getCaptures().size());

		List<Move.PieceCapture> captures = jump.getCaptures();
		Move.PieceCapture capture1 = captures.get(0);

		assertEquals(new Position(3, 4), capture1.getPosition());
		assertTrue(capture1.getPiece() instanceof WhiteSinglePiece);

		Move.PieceCapture capture2 = captures.get(1);

		assertEquals(new Position(5, 6), capture2.getPosition());
		assertTrue(capture2.getPiece() instanceof WhiteSinglePiece);
	}

	/**
	 * Test left triple jumps
	 */
	@Test
	public void testTripleJumpLeft_calculateJump() {
		Board board = new Board();

		board.removePiece(1, 6);
		board.movePiece(new Move(new Position(7, 0), new Position(1, 6)));
		board.removePiece(2, 5);
		board.movePiece(new Move(new Position(7, 2), new Position(3, 4)));
		board.removePiece(6, 1);
		board.removePiece(7, 0);
		board.removePiece(7, 2);
		Position piecePosition = new Position(0, 7);
		SinglePiece piece = (SinglePiece) board.getPieceAt(0, 7);
		KingPiece king = piece.promote();
		board.setPieceAt(0, 7, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertEquals(1, jumps.size());

		Move jump = jumps.get(0);

		assertEquals(new Position(0, 7), jump.getStart());
		assertEquals(new Position(6, 1), jump.getEnd());
		assertEquals(3, jump.getCaptures().size());

		List<Move.PieceCapture> captures = jump.getCaptures();
		Move.PieceCapture capture1 = captures.get(0);

		assertEquals(new Position(1, 6), capture1.getPosition());
		assertTrue(capture1.getPiece() instanceof WhiteSinglePiece);

		Move.PieceCapture capture2 = captures.get(1);

		assertEquals(new Position(3, 4), capture2.getPosition());
		assertTrue(capture2.getPiece() instanceof WhiteSinglePiece);

		Move.PieceCapture capture3 = captures.get(2);

		assertEquals(new Position(5, 2), capture3.getPosition());
		assertTrue(capture3.getPiece() instanceof WhiteSinglePiece);
	}

	/**
	 * Test right triple jumps
	 */
	@Test
	public void testTripleJumpRight_calculateJump() {
		Board board = new Board();

		board.removePiece(1, 2);
		board.movePiece(new Move(new Position(7, 0), new Position(1, 2)));
		board.removePiece(2, 3);
		board.movePiece(new Move(new Position(7, 2), new Position(3, 4)));
		board.removePiece(6, 7);
		board.removePiece(7, 6);
		Position piecePosition = new Position(0, 1);
		SinglePiece piece = (SinglePiece) board.getPieceAt(0, 1);
		KingPiece king = piece.promote();
		board.setPieceAt(0, 1, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertEquals(1, jumps.size());

		Move jump = jumps.get(0);

		assertEquals(new Position(0, 1), jump.getStart());
		assertEquals(new Position(6, 7), jump.getEnd());
		assertEquals(3, jump.getCaptures().size());

		List<Move.PieceCapture> captures = jump.getCaptures();
		Move.PieceCapture capture1 = captures.get(0);

		assertEquals(new Position(1, 2), capture1.getPosition());
		assertTrue(capture1.getPiece() instanceof WhiteSinglePiece);

		Move.PieceCapture capture2 = captures.get(1);

		assertEquals(new Position(3, 4), capture2.getPosition());
		assertTrue(capture2.getPiece() instanceof WhiteSinglePiece);

		Move.PieceCapture capture3 = captures.get(2);

		assertEquals(new Position(5, 6), capture3.getPosition());
		assertTrue(capture3.getPiece() instanceof WhiteSinglePiece);
	}

	/**
	 * Test backward jumps to the right
	 */
	@Test
	public void testBackRight_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(0, 1), new Position(4, 3)));
		Position piecePosition = new Position(5, 2);
		SinglePiece piece = (SinglePiece) board.getPieceAt(5, 2);
		KingPiece king = piece.promote();
		board.setPieceAt(5, 2, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertEquals(1, jumps.size());

		Move jump = jumps.get(0);

		assertEquals(new Position(5, 2), jump.getStart());
		assertEquals(new Position(3, 4), jump.getEnd());
		assertEquals(1, jump.getCaptures().size());

		Move.PieceCapture capture = jump.getCaptures().get(0);

		assertTrue(capture.getPiece() instanceof RedSinglePiece);
		assertEquals(new Position(4, 3), capture.getPosition());
	}

	/**
	 * Test backward jumps to the left
	 */
	@Test
	public void testBackLeft_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(0, 1), new Position(4, 3)));
		Position piecePosition = new Position(5, 4);
		SinglePiece piece = (SinglePiece) board.getPieceAt(5, 4);
		KingPiece king = piece.promote();
		board.setPieceAt(5, 4, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertEquals(1, jumps.size());

		Move jump = jumps.get(0);

		assertEquals(new Position(5, 4), jump.getStart());
		assertEquals(new Position(3, 2), jump.getEnd());
		assertEquals(1, jump.getCaptures().size());

		Move.PieceCapture capture = jump.getCaptures().get(0);

		assertTrue(capture.getPiece() instanceof RedSinglePiece);
		assertEquals(new Position(4, 3), capture.getPosition());
	}

	/**
	 * Test backward jump with wrong piece
	 */
	@Test
	public void testBackLeftWhitePiece_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(7, 0), new Position(4, 3)));
		Position piecePosition = new Position(5, 4);
		SinglePiece piece = (SinglePiece) board.getPieceAt(5, 4);
		KingPiece king = piece.promote();
		board.setPieceAt(5, 4, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	/**
	 * Test backward jumps with wrong piece
	 */
	@Test
	public void testBackRightWhitePiece_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(7, 0), new Position(4, 3)));
		Position piecePosition = new Position(5, 2);
		SinglePiece piece = (SinglePiece) board.getPieceAt(5, 2);
		KingPiece king = piece.promote();
		board.setPieceAt(5, 2, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	/**
	 * Test that a backward jump can be blocked to the left
	 */
	@Test
	public void testBackLeftBlocked_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(7, 0), new Position(3, 2)));
		board.movePiece(new Move(new Position(0, 1), new Position(4, 3)));
		Position piecePosition = new Position(5, 4);
		SinglePiece piece = (SinglePiece) board.getPieceAt(5, 4);
		KingPiece king = piece.promote();
		board.setPieceAt(5, 4, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	/**
	 * Test that a backward jump can be blocked to the right
	 */
	@Test
	public void testBackRightBlocked_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(0, 1), new Position(4, 3)));
		board.movePiece(new Move(new Position(7, 0), new Position(3, 4)));
		Position piecePosition = new Position(5, 2);
		SinglePiece piece = (SinglePiece) board.getPieceAt(5, 2);
		KingPiece king = piece.promote();
		board.setPieceAt(5, 2, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	/**
	 * Test that a backward jump can't go out of bounds to the left
	 */
	@Test
	public void testBackLeftBounds_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(0, 1), new Position(4, 7)));
		Position piecePosition = new Position(5, 6);
		SinglePiece piece = (SinglePiece) board.getPieceAt(5, 6);
		KingPiece king = piece.promote();
		board.setPieceAt(5, 6, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	/**
	 * Test that a backward jump can't go out of bounds to the right
	 */
	@Test
	public void testBackRightBounds_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(0, 1), new Position(3, 0)));
		board.movePiece(new Move(new Position(7, 0), new Position(4, 1)));
		Position piecePosition = new Position(4, 1);
		SinglePiece piece = (SinglePiece) board.getPieceAt(4, 1);
		KingPiece king = piece.promote();
		board.setPieceAt(4, 1, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

		assertTrue(jumps.isEmpty());
	}

	/**
	 * Test backward double jumps
	 */
	@Test
	public void testBackDoubleJumpLeft_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(0, 1), new Position(4, 3)));
		board.removePiece(1, 0);
		Position piecePosition = new Position(5, 4);
		SinglePiece piece = (SinglePiece) board.getPieceAt(5, 4);
		KingPiece king = piece.promote();
		board.setPieceAt(5, 4, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

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

	/**
	 * Test backward double jumps to the right
	 */
	@Test
	public void testBackDoubleJumpRight_calculateJump() {
		Board board = new Board();

		board.movePiece(new Move(new Position(0, 1), new Position(4, 3)));
		board.removePiece(1, 6);
		Position piecePosition = new Position(5, 2);
		SinglePiece piece = (SinglePiece) board.getPieceAt(5, 2);
		KingPiece king = piece.promote();
		board.setPieceAt(5, 2, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

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

	/**
	 * Test backward triple jumps to the left
	 */
	@Test
	public void testBackTripleJumpLeft_calculateJump() {
		Board board = new Board();

		board.removePiece(6, 1);
		board.movePiece(new Move(new Position(0, 1), new Position(6, 1)));
		board.removePiece(5, 2);
		board.movePiece(new Move(new Position(0, 3), new Position(4, 3)));
		board.removePiece(1, 6);
		board.removePiece(0, 7);
		board.removePiece(0, 5);
		Position piecePosition = new Position(7, 0);
		SinglePiece piece = (SinglePiece) board.getPieceAt(7, 0);
		KingPiece king = piece.promote();
		board.setPieceAt(7, 0, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

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

	/**
	 * Test a backward triple jump to the right
	 */
	@Test
	public void testBackTripleJumpRight_calculateJump() {
		Board board = new Board();

		board.removePiece(6, 5);
		board.movePiece(new Move(new Position(0, 1), new Position(6, 5)));
		board.removePiece(5, 4);
		board.movePiece(new Move(new Position(0, 3), new Position(4, 3)));
		board.removePiece(1, 0);
		board.removePiece(0, 1);
		Position piecePosition = new Position(7, 6);
		SinglePiece piece = (SinglePiece) board.getPieceAt(7, 6);
		KingPiece king = piece.promote();
		board.setPieceAt(7, 6, king);

		List<Move> jumps = king.getJumps(board, piecePosition);

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