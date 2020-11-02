package com.webcheckers.model.pieces;

import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Position;

import java.util.LinkedList;

/**
 * A white single piece
 *
 * @author Mike White
 */
public class WhiteSinglePiece extends SinglePiece {

	/**
	 * Creates a new white single piece
	 */
	public WhiteSinglePiece() {
		super();
	}

	/**
	 * The color of the piece
	 * @return WHITE
	 */
	public PieceColor getColor() {
		return PieceColor.WHITE;
	}

	/**
	 * Creates a new king piece
	 * @return A white king
	 */
	public KingPiece promote() {
		return new WhiteKingPiece();
	}

	/**
	 * Gets the possible moves that the white piece can make
	 * @param board The board being played on
	 * @param position The position of the piece
	 * @return The list of valid single moves
	 */
	public LinkedList<Move> getSingleMoves(Board board, Position position) {
		LinkedList<Move> moves = new LinkedList<>();
		int row = position.getRow();
		int cell = position.getCell();

		checkSingleMove(moves, board, position, new Position(row - 1, cell + 1));
		checkSingleMove(moves, board, position, new Position(row - 1, cell - 1));

		return moves;
	}

	/**
	 * Gets the possible jumps that could be made by this piece
	 * @param board The board being played on
	 * @param position The position of the piece
	 * @return The list of jumps that can be made
	 */
	public LinkedList<Move> getJumps(Board board, Position position) {
		LinkedList<Move> jumps = new LinkedList<>();

		checkJump(jumps, board, position, -1, +1);
		checkJump(jumps, board, position, -1, -1);

		return jumps;
	}

	/**
	 * Gets the possible jumps that could be made by this piece
	 * @param board The board being played on
	 * @param position The position of the piece
	 * @param move The previous move
	 * @return The list of jumps that can be made
	 */
	public LinkedList<Move> getJumps(Board board, Position position, Move move) {
		LinkedList<Move> jumps = new LinkedList<>();

		checkJump(jumps, board, position, -1, +1, move);
		checkJump(jumps, board, position, -1, -1, move);

		return jumps;
	}

}
