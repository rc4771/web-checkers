package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
class SpaceIteratorTest {

	private static ArrayList<Space> testList = new ArrayList<>();

	static {
		testList.add(new Space(0, 0, null));
		testList.add(new Space(0, 1, new Piece(Piece.PieceColor.RED, Piece.PieceType.SINGLE)));
		testList.add(new Space(0, 2, new Piece(Piece.PieceColor.WHITE, Piece.PieceType.SINGLE)));
	}

	@Test
	/// Test to make sure the first next call works correctly
	/// First element of testList should be null
	void next_first() {
		final SpaceIterator CuT = new SpaceIterator(testList);
		final Space spaceActual = CuT.next();
		final Piece pieceActual = spaceActual.getPiece();
		final Space spaceExpected = testList.get(0);

		assertEquals(spaceExpected.getCellIdx(), spaceActual.getCellIdx());
		assertNull(pieceActual);
	}

	@Test
	/// Test to make sure hasNext works correctly
	void hasNext() {
		final SpaceIterator CuT = new SpaceIterator(testList);

		for (int i = 0; i < testList.size(); i++) {
			assertTrue(CuT.hasNext());
			CuT.next();
		}

		assertFalse(CuT.hasNext());
	}

	@Test
	/// Test to make sure all calls to next work correctly
	void next_all() {
		final SpaceIterator CuT = new SpaceIterator(testList);
		CuT.next(); // skip first element

		for (int i = 1; i < testList.size(); i++) {
			Space spaceActual = CuT.next();
			Piece pieceActual = spaceActual.getPiece();
			Space spaceExpected = testList.get(i);
			Piece pieceExpected = spaceExpected.getPiece();

			assertEquals(spaceExpected.getCellIdx(), spaceActual.getCellIdx());
			assertEquals(pieceExpected.getType(), pieceActual.getType());
			assertEquals(pieceExpected.getColor(), pieceActual.getColor());
		}

		assertThrows(NoSuchElementException.class, () -> CuT.next());
	}
}