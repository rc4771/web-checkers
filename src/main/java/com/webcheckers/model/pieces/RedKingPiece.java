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

	@Override
	public PieceColor getColor() {
		return PieceColor.RED;
	}
}
