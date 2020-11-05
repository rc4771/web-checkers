package com.webcheckers.model.pieces;

/**
 * A red king piece
 *
 * @author Mike White
 */
public class RedKingPiece extends KingPiece {

	public RedKingPiece() {
		super();
	}

	/**
	 * accessor for the color of the piece
	 * @return the color RED
	 */
	@Override
	public PieceColor getColor() {
		return PieceColor.RED;
	}
}
