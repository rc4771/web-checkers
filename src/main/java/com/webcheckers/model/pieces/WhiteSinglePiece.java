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
	 * Gets the possible jumps that could be made by this piece
	 * @param board The board being played on
	 * @param position The position of the piece
	 * @return The list of jumps that can be made
	 */
	public LinkedList<Move> getJumps(Board board, Position position) {
		LinkedList<Move> jumps = new LinkedList<>();
		int row = position.getRow();
		int cell = position.getCell();

		if (board.getPieceColorAt(row - 1, cell + 1) == PieceColor.RED &&
				!board.hasPieceAt(row - 2, cell + 2) &&
				board.inBounds(row - 2, cell + 2)) {
			Move newMove = new Move(position,
					new Position(row - 2, cell + 2),
					new Move.PieceCapture(
							board.getPieceAt(row - 1, cell + 1),
							new Position(row - 1, cell + 1)
					)
			);
			LinkedList<Move> newJumps = getJumps(board, new Position(row - 2, cell + 2), newMove);
			if (newJumps.isEmpty()) {
				jumps.add(newMove);
			} else {
				jumps.addAll(newJumps);
			}
		}
		if (board.getPieceColorAt(row - 1, cell - 1) == PieceColor.RED &&
				!board.hasPieceAt(row - 2, cell - 2) &&
				board.inBounds(row - 2, cell - 2)) {
			Move newMove = new Move(position,
					new Position(row - 2, cell - 2),
					new Move.PieceCapture(
							board.getPieceAt(row - 1, cell - 1),
							new Position(row - 1, cell - 1)
					)
			);
			LinkedList<Move> newJumps = getJumps(board, new Position(row - 2, cell - 2), newMove);
			if (newJumps.isEmpty()) {
				jumps.add(newMove);
			} else {
				jumps.addAll(newJumps);
			}
		}

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
		int row = position.getRow();
		int cell = position.getCell();

		if (board.getPieceColorAt(row - 1, cell + 1) == PieceColor.RED &&
				!board.hasPieceAt(row - 2, cell + 2) &&
				!move.pieceCaptured(new Position(row - 1, cell + 1)) &&
				board.inBounds(row - 2, cell + 2)) {
			Move newMove = move.addMove(
					new Position(row - 2, cell + 2),
					new Move.PieceCapture(
							board.getPieceAt(row - 1, cell + 1),
							new Position(row - 1, cell + 1)
					)
			);
			LinkedList<Move> newJumps = getJumps(board, new Position(row - 2, cell + 2), newMove);
			if (newJumps.isEmpty()) {
				jumps.add(newMove);
			} else {
				jumps.addAll(newJumps);
			}
		}
		if (board.getPieceColorAt(row - 1, cell - 1) == PieceColor.RED &&
				!board.hasPieceAt(row - 2, cell - 2) &&
				!move.pieceCaptured(new Position(row - 1, cell - 1)) &&
				board.inBounds(row - 2, cell - 2)) {
			Move newMove = move.addMove(
					new Position(row - 2, cell - 2),
					new Move.PieceCapture(
							board.getPieceAt(row - 1, cell - 1),
							new Position(row - 1, cell - 1)
					)
			);
			LinkedList<Move> newJumps = getJumps(board, new Position(row - 2, cell - 2), newMove);
			if (newJumps.isEmpty()) {
				jumps.add(newMove);
			} else {
				jumps.addAll(newJumps);
			}
		}

		return jumps;
	}

}
