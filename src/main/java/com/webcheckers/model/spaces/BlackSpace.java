package com.webcheckers.model.spaces;

import com.webcheckers.model.Space;

/**
 * A black space on a board. Pieces cannot be placed on this
 *
 * @author Mike White
 */
public class BlackSpace extends Space {
	/**
	 * Creates a new space object
	 * @param cellIdx
	 *      The cell index on the board
	 */
	public BlackSpace(int cellIdx) {
		super(cellIdx);
	}

	/**
	 * Checks to see if this space is valid or not, returns a boolean
	 */
	public boolean isValid() {
		return false;
	}
}
