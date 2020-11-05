package com.webcheckers.model;

import com.webcheckers.model.pieces.RedSinglePiece;
import com.webcheckers.model.pieces.WhiteSinglePiece;
import com.webcheckers.model.spaces.BlackSpace;
import com.webcheckers.model.spaces.WhiteSpace;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("Model-tier")
class SpaceIteratorTest {

	private static ArrayList<Space> testList;

	static
	{
		//testList.add(new WhiteSpace(0));
		//testList.add(new BlackSpace(1, new RedSinglePiece()));
		//testList.add(new BlackSpace(2, new WhiteSinglePiece()));
		testList = new ArrayList<Space>();
		WhiteSpace firstWhiteSpace = mock(WhiteSpace.class);
		when(firstWhiteSpace.getCellIdx()).thenReturn(0);
		BlackSpace firstBlackSpace = mock(BlackSpace.class);
		when(firstBlackSpace.getCellIdx()).thenReturn(1);
		when(firstBlackSpace.getPiece()).thenReturn(mock(RedSinglePiece.class));
		BlackSpace secondBlackSpace = mock(BlackSpace.class);
		when(secondBlackSpace.getCellIdx()).thenReturn(2);
		when(secondBlackSpace.getPiece()).thenReturn(mock(WhiteSinglePiece.class));
		testList.add(firstWhiteSpace);
		testList.add(firstBlackSpace);
		testList.add(secondBlackSpace);
	}

	/**
	 * Test to make sure the first next call works correctly. First element of testList should be null.
	 */
	@Test
	void next_first() {
		final SpaceIterator CuT = new SpaceIterator(testList);
		final Space spaceActual = CuT.next();

		assertTrue(spaceActual instanceof WhiteSpace);

		final Space spaceExpected = testList.get(0);
		assertEquals(spaceExpected.getCellIdx(), spaceActual.getCellIdx());
	}

	/**
	 * Test to make sure hasNext works correctly
	 */
	@Test
	void hasNext() {
		final SpaceIterator CuT = new SpaceIterator(testList);

		for (int i = 0; i < testList.size(); i++) {
			assertTrue(CuT.hasNext());
			CuT.next();
		}

		assertFalse(CuT.hasNext());
	}

	/**
	 * Test to make sure all calls to next work correctly
	 */
	@Test
	void next_all() {
		final SpaceIterator CuT = new SpaceIterator(testList);
		CuT.next(); // skip first element

		for (int i = 1; i < testList.size(); i++) {
			Space spaceActual = CuT.next();
			Space spaceExpected = testList.get(i);
			if (spaceExpected instanceof BlackSpace) {
				BlackSpace blackSpaceActual = (BlackSpace) spaceActual;
				Piece pieceActual = blackSpaceActual.getPiece();
				BlackSpace blackSpaceExpected = (BlackSpace) spaceExpected;
				Piece pieceExpected = blackSpaceExpected.getPiece();

				assertEquals(spaceExpected.getCellIdx(), spaceActual.getCellIdx());
				assertEquals(pieceExpected.getType(), pieceActual.getType());
				assertEquals(pieceExpected.getColor(), pieceActual.getColor());
			} else {
				assertTrue(spaceActual instanceof WhiteSpace);
			}
		}

		assertThrows(NoSuchElementException.class, CuT::next);
	}
}