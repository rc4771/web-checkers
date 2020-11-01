package com.webcheckers.model.spaces;

import com.webcheckers.model.Piece;
import com.webcheckers.model.Space;

/**
 * A white space on a board that may contain a piece
 *
 * @author Mike White
 */
public class BlackSpace extends Space {
	/** The piece on the space */
	private Piece piece;

	/**
	 * Creates a new space object
	 * @param cellIdx
	 *      The cell index on the board
	 * @param piece
	 *      The piece this space has
	 */
	public BlackSpace(int cellIdx, Piece piece) {
		super(cellIdx);
		this.piece = piece;
	}

	/**
	 * Creates a new space object with no pieces on it
	 * @param cellIdx
	 *      The cell index on the board
	 */
	public BlackSpace(int cellIdx) {
		super(cellIdx);
		this.piece = null;
	}

	/**
	 * Gets this space's Piece object
	 */
	public Piece getPiece() {
		return this.piece;
	}

	/**
	 * Sets this space's Piece object
	 * @param newPiece
	 *      The piece to set this space to, this CAN be null if there is no longer a piece here
	 */
	public void setPiece(Piece newPiece) {
		this.piece = newPiece;
	}

	/**
	 * Checks to see if this space is valid or not, returns a boolean
	 */
	public boolean isValid() {
		return piece == null;
	}
}
