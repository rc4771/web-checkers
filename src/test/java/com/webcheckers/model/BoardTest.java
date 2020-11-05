package com.webcheckers.model;

import com.webcheckers.model.pieces.RedKingPiece;
import com.webcheckers.model.pieces.RedSinglePiece;
import com.webcheckers.model.pieces.WhiteSinglePiece;
import com.webcheckers.model.spaces.BlackSpace;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class BoardTest {
	/**
	 * This test the color function of pieces.
	 * Testing board.getPieceColorAt().
	 */
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

	/**
	 * This test the piece type function of pieces.
	 */
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

	/**
	 * This test the pieces' existance on the board.
	 */
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

	/**
	 * This test the empty spaces existance on the board should not hold any pieces.
	 */
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

	/**
	 * This test if the game can get the right piece color from the board using getPiece().
	 */
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

	/**
	 * This test if the game can get the right piece type from the board using getPiece().
	 */
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

	/**
	 * This test if the piece movements are within the American checker rule set.
	 */
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
		board1.movePiece(new Move(new Position(0, 1), new Position(4, 1)));
		assertEquals(board1.getPieceColorAt(4, 1), Piece.PieceColor.RED);
		assertEquals(board1.getPieceTypeAt(4, 1), Piece.PieceType.SINGLE);

		// Test move to whitespace
		board1.movePiece(new Move(new Position(2,0), new Position(3, 1)));
		assertNull(board1.getPieceAt(3,1));
		board1.movePiece(new Move(new Position(4,1), new Position(5, 1)));
		assertNull(board1.getPieceAt(5, 1));
		board1.movePiece(new Move(new Position(2,0), new Position(3,0)));
		assertNull(board1.getPieceAt(2, 0));
	}

	/**
	 * Test if the game can be mirrored properly so that two players can play the same game.
	 */
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

	/**
	 * Test if the iterator performs successfully to generate a proper game board with pieces on it.
	 */
	@Test
	void iterator() {
		Board board = new Board();
		RowIterator iterator = board.iterator(); // get the iterator

		// Check that the rows are equal
		for (int i = 0; i < 8; i++) {
			ArrayList<Space> actual = iterator.next().getSpaces();
			for (int j = 0; j < 8; j++) {
				if (actual.get(j) instanceof BlackSpace) {
					BlackSpace space = (BlackSpace) actual.get(j);
					if (space.getPiece() == null) {
						assertNull(board.getPieceAt(i, j));
					} else {
						assertEquals(board.getPieceColorAt(i, j), space.getPiece().getColor());
						assertEquals(board.getPieceTypeAt(i, j), space.getPiece().getType());
					}
				}
			}
		}

		assertFalse(iterator.hasNext()); // There should be no more rows
		assertThrows(NoSuchElementException.class, iterator::next);
	}

	/**
	 * Test to check inBounds method
	 */
	@Test
	void inBoundsTest(){
		Board CuT = new Board();

		assertTrue(CuT.inBounds(0, 5));

		assertFalse(CuT.inBounds(9,10));
	}

	/**
	 * Test for setPieceAt method
	 */
	@Test
	void setPieceAtTest(){
		Board CuT = new Board();

		// Test for out of bounds
		CuT.setPieceAt(9, 8, new RedSinglePiece());
		assertFalse(CuT.hasPieceAt(9, 8));

		// Test for WhiteSpace
		CuT.setPieceAt(4,0, new RedSinglePiece());
		assertFalse(CuT.hasPieceAt(4,0));

		// Test for piece being set
		CuT.setPieceAt(4,1, new RedSinglePiece());
		assertTrue(CuT.hasPieceAt(4,1));
	}

	/**
	 * Test for removePiece method
	 */
	@Test
	void removePieceTest(){
		Board CuT = new Board();

		// Test for out of bounds
		CuT.removePiece(9, 10);
		assertNull(CuT.getPieceAt(9,10));

		// Test for WhiteSpace
		CuT.removePiece(4,0);
		assertNull(CuT.getPieceAt(4,0));

		// Test for piece removed
		CuT.removePiece(1,0);
		assertFalse(CuT.hasPieceAt(1,0));
	}

	/**
	 * Test for Simple and King Piece instances
	 */
	@Test
	void kingTest(){
		Board CuT = new Board();
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++) {
				if (CuT.hasPieceAt(i, j)) {
					CuT.removePiece(i, j);
					CuT.setPieceAt(i, j,null);
				}
			}
		}

		CuT.setPieceAt(6, 1, new RedSinglePiece());
		CuT.setPieceAt(1,2, new WhiteSinglePiece());

		assertTrue(CuT.hasPieceAt(6,1));
		assertTrue(CuT.hasPieceAt(1,2));

		assertEquals(Piece.PieceColor.RED, CuT.getPieceColorAt(6,1));
		assertEquals(Piece.PieceColor.WHITE, CuT.getPieceColorAt(1, 2));

		CuT.movePiece(new Move(new Position(6,1), new Position(7,2)));
		CuT.movePiece(new Move(new Position(1,2), new Position(0,1)));

		assertTrue(CuT.hasPieceAt(7,2));
		assertTrue(CuT.hasPieceAt(0,1));

		assertEquals(Piece.PieceType.KING, CuT.getPieceTypeAt(7,2));
		assertEquals(Piece.PieceType.KING, CuT.getPieceTypeAt(0,1));
	}

	/**
	 * Test if the game board can be duplicated correctly.
	 */
	@Test
	public void test_copy() {
		Board board1 = new Board();
		Board board2 = new Board(board1);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				assertEquals(board1.getPieceColorAt(i, j), board2.getPieceColorAt(i, j));
				assertEquals(board1.getPieceTypeAt(i, j), board2.getPieceTypeAt(i, j));
			}
		}

		board2.removePiece(0, 1);
		board2.removePiece(6, 5);
		Board board3 = new Board(board2);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				assertEquals(board2.getPieceColorAt(i, j), board3.getPieceColorAt(i, j));
				assertEquals(board2.getPieceTypeAt(i, j), board3.getPieceTypeAt(i, j));
			}
		}
	}

	/**
	 * Test if the space on a board is valid in relation to the pieces' initial position based
	 * on the American checker rule set.
	 */
	@Test
	public void isValidTest() {
		Board cut = new Board();
		assertTrue(cut.spaceIsValidAt(3, 4)); // empty black space is valid
		assertFalse(cut.spaceIsValidAt(3, 3)); // white space is invalid
		assertFalse(cut.spaceIsValidAt(0, 1)); // taken space is invalid
	}
}