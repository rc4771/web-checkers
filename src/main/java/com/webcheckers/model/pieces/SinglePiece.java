package com.webcheckers.model.pieces;

import com.webcheckers.model.Piece;

/**
 * A single piece. Can only move forwards
 *
 * @author Mike White
 */
public abstract class SinglePiece extends Piece {

	/**
	 * Creates a new king piece
	 */
	protected SinglePiece() {
		super();
	}

	/**
	 * Shows that it's a single piece
	 * @return PieceType.SINGLE
	 */
	public PieceType getType() {
		return PieceType.SINGLE;
	}

	/**
	 * Creates a new King piece
	 * @return a king
	 */
	public abstract KingPiece promote();
}
