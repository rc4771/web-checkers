package com.webcheckers.model.pieces;

/**
 * A white king piece
 *
 * @author Mike White
 */
public class WhiteKingPiece extends KingPiece {

	public WhiteKingPiece() {
		super();
	}

	/**
	 * accessor for the color of the piece
	 * @return the color WHITE
	 */
	@Override
	public PieceColor getColor() {
		return PieceColor.WHITE;
	}
}
