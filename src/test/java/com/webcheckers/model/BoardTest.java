package com.webcheckers.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

	@Test
	void getPieceColorAt() {
		Board board = new Board(); // uses a starting board

		// test that the first three rows are either red or null
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 8; j++) {
				assertTrue(board.getPieceColorAt(i, j) == null || board.getPieceColorAt(i, j) == Piece.PieceColor.RED);
			}
		}

		// test that the last three rows are white or null
		for (int i = 5; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				assertTrue(board.getPieceColorAt(i, j) == null || board.getPieceColorAt(i, j) == Piece.PieceColor.WHITE);
			}
		}
	}

	@Test
	/// Test that at the beginning, all pieces are either single or null
	void getPieceTypeAt_start() {
		Board board = new Board();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				assertTrue(board.getPieceTypeAt(i, j) == null || board.getPieceTypeAt(i, j) == Piece.PieceType.SINGLE);
			}
		}
	}

	@Test
	void hasPieceAt() {
		Board board = new Board();

		// Check the even red rows
		for (int i = 0; i < 3; i += 2) {

			// Test that the even rows has empty spaces every other line
			for (int j = 0; j < 8; j += 2) {
				assertFalse(board.hasPieceAt(i, j));
			}

			// Check that the other pieces in the row aren't null
			for (int j = 1; j < 8; j += 2) {
				assertTrue(board.hasPieceAt(i, j));
			}
		}

		// Check the odd red row
		for (int j = 1; j < 8; j += 2) {
			assertFalse(board.hasPieceAt(1, j));
		} for (int j = 0; j < 8; j += 2) {
			assertTrue(board.hasPieceAt(1, j));
		}

		// Check the even white row
		for (int j = 0; j < 8; j += 2) {
			assertFalse(board.hasPieceAt(6, j));
		} for (int j = 1; j < 8; j += 2) {
			assertTrue(board.hasPieceAt(6, j));
		}

		// Check the odd white rows
		for (int i = 5; i < 8; i += 2) {

			for (int j = 1; j < 8; j += 2) {
				assertFalse(board.hasPieceAt(i, j));
			}

			for (int j = 0; j < 8; j += 2) {
				assertTrue(board.hasPieceAt(i, j));
			}
		}

		// Check the middle of the board
		for (int i = 3; i < 5; i++) {
			for (int j = 0; j < 8; j++) {
				assertFalse(board.hasPieceAt(i, j));
			}
		}
	}

	@Test
	/// Check that the correct spaces are null
	void getPieceAt_nulls() {
		Board board = new Board();

		// If there's a piece at the location, then the Piece should not be null
		// If there isn't, then it should be null
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.hasPieceAt(i, j)) {
					assertNotNull(board.getPieceAt(i, j));
				} else {
					assertNull(board.getPieceAt(i, j));
				}
			}
		}
	}

	@Test
	void getPieceAt_colors() {
		Board board = new Board();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece.PieceColor expected = board.getPieceColorAt(i, j);
				if (expected != null) {
					Piece.PieceColor actual = board.getPieceAt(i, j).getColor();
					assertSame(expected, actual);
				} else {
					assertNull(board.getPieceAt(i, j));
				}
			}
		}
	}

	@Test
	void getPieceAt_types() {
		Board board = new Board();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece.PieceType expected = board.getPieceTypeAt(i, j);
				if (expected != null) {
					Piece.PieceType actual = board.getPieceAt(i, j).getType();
					assertSame(expected, actual);
				} else {
					assertNull(board.getPieceAt(i, j));
				}
			}
		}
	}

	@Test
	void movePiece() {
		Board board1 = new Board();
		Board board2 = new Board();

		// Test move null piece
		board1.movePiece(new Move(new Position(0, 0), new Position(4, 5)));
		assertFalse(board1.hasPieceAt(4, 5));

		// Test move to taken spot
		board1.movePiece(new Move(new Position(0, 1), new Position(5, 0)));
		assertEquals(board1.getPieceColorAt(5, 0), board2.getPieceColorAt(5, 0));

		// Test regular move
		board1.movePiece(new Move(new Position(0, 1), new Position(3, 1)));
		assertEquals(board1.getPieceColorAt(3, 1), Piece.PieceColor.RED);
		assertEquals(board1.getPieceTypeAt(3, 1), Piece.PieceType.SINGLE);
	}

	@Test
	void transposeForColor() {
		Board original = new Board();
		Board transposed = original.transposeForColor(Piece.PieceColor.RED);
		Board stillOriginal = original.transposeForColor(Piece.PieceColor.WHITE);

		// Check that original and still original are the same
		assertEquals(original, stillOriginal);

		// Check that the transposition happened correctly
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				assertEquals(original.getPieceColorAt(7 - i, 7 - j), transposed.getPieceColorAt(i, j));
				assertEquals(original.getPieceTypeAt(7 - i, 7 - j), transposed.getPieceTypeAt(i, j));
			}
		}
	}

	@Test
	void iterator() {
		Board board = new Board();
		RowIterator iterator = board.iterator(); // get the iterator

		// Check that the rows are equal
		for (int i = 0; i < 8; i++) {
			ArrayList<Space> actual = iterator.next().getSpaces();
			for (int j = 0; j < 8; j++) {
				if (actual.get(j).getPiece() == null) {
					assertNull(board.getPieceAt(i, j));
				} else {
					assertEquals(board.getPieceColorAt(i, j), actual.get(j).getPiece().getColor());
					assertEquals(board.getPieceTypeAt(i, j), actual.get(j).getPiece().getType());
				}
			}
		}

		assertFalse(iterator.hasNext()); // There should be no more rows
	}
}