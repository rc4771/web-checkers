package com.webcheckers.model.pieces;

import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Position;

import java.util.LinkedList;
import java.util.List;

/**
 * A king piece that can move in four directions
 *
 * @author Mike White
 */
public abstract class KingPiece extends Piece {

	/**
	 * Creates a new king piece
	 */
	protected KingPiece() {
		super();
	}

	/**
	 * Shows that it's a king piece
	 * @return PieceType.KING
	 */
	public PieceType getType() {
		return PieceType.KING;
	}

	/**
	 * Gets the possible moves that the king can make
	 * @param board The board being played on
	 * @param position The position of the piece
	 * @return The list of valid single moves
	 */
	public LinkedList<Move> getSingleMoves(Board board, Position position) {
		LinkedList<Move> moves = new LinkedList<>();
		int row = position.getRow();
		int cell = position.getCell();

		checkSingleMove(moves, board, position, new Position(row + 1, cell + 1));
		checkSingleMove(moves, board, position, new Position(row + 1, cell - 1));
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

		checkJump(jumps, board, position, +1, +1);
		checkJump(jumps, board, position, +1, -1);
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

		checkJump(jumps, board, position, +1, +1, move);
		checkJump(jumps, board, position, +1, -1, move);
		checkJump(jumps, board, position, -1, +1, move);
		checkJump(jumps, board, position, -1, -1, move);

		return jumps;
	}
}
