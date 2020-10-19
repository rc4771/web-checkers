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

	@Override
	public PieceColor getColor() {
		return PieceColor.WHITE;
	}
}
