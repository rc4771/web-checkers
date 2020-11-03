package com.webcheckers.model.spaces;

import com.webcheckers.model.Space;
import com.webcheckers.model.pieces.WhiteKingPiece;

/**
 * A black space on a board. Pieces cannot be placed on this
 *
 * @author Mike White
 */
public class WhiteSpace extends Space {

	/**
	 * Creates a new space object
	 * @param cellIdx
	 *      The cell index on the board
	 */
	public WhiteSpace(int cellIdx) {
		super(cellIdx);
	}

	/**
	 * Creates a new white space
	 * @param space The space to copy
	 */
	private WhiteSpace(Space space) {
		super(space);
	}

	/**
	 * Checks to see if this space is valid or not, returns a boolean
	 */
	public boolean isValid() {
		return false;
	}

	/**
	 * Creates a new copy of the piece
	 * @return a copy
	 */
	public Space copy() {
		return new WhiteSpace(this);
	}
}
