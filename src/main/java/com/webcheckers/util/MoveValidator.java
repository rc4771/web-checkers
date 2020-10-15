package com.webcheckers.util;

import com.webcheckers.model.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Checks if a move is valid
 *
 * @author Mike White
 */
public class MoveValidator {

	/**
	 * The result of a move attempt
	 */
	public enum MoveResult {
		OK,                     // The move is valid and can be made
		PIECE_NULL_ERR,         // There is no piece to move at the start space, so invalid
		END_OCCUPIED_ERR,       // There is a piece at the end space, so invalid
		MOVE_DIRECTION_ERR,     // The direction of the move is backwards, so invalid
		TOO_FAR_ERR,            // The piece moved too far without jumping, so invalid
		NOT_TURN_ERR,           // It is not the player's turn to move
		INVALID_JUMP,           // An invalid jump has been made
		MUST_MAKE_JUMP,         // Player made a single move when a jump is required
	}

	/**
	 * Calculates the possible jumps the current player could make
	 *
	 * @param board The board being played on
	 * @param player The player whose turn it is
	 * @return the list of valid jump moves
	 */
	public static List<Move> calculateValidJumps(Board board, Piece.PieceColor player) {
		List<Move> jumps = new LinkedList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.getPieceColorAt(i, j) == player) {
					jumps.addAll(board.getPieceAt(i, j).getJumps(board, new Position(i, j)));
				}
			}
		}

		return jumps;
	}

	/**
	 * Checks to see if a jump is valid
	 * If no jumps can be made, then the move is valid
	 *
	 * @param board The board being played on
	 * @param start The starting position of the jump
	 * @param end The end position of the jump
	 * @return The move that was made, null if invalid
	 */
	public static Move isValidJump(Board board, Piece.PieceColor player, Position start, Position end) {
		List<Move> jumps = calculateValidJumps(board, player);

		for (Move jump: jumps) {
			if (jump.getStart().equals(start) && jump.getEnd().equals(end)) {
				return jump;
			}
		}

		return null;
	}

	/**
	 * Validates the move of a piece. This validates:
	 *      1) There is a piece at this row & cell
	 *      2) There isn't a piece at the ending row & cell
	 *      3) The direction of the jump according to the piece's color
	 *      4) The move only goes 1 square if there are no jumps made
	 *      5) If this move isn't a jump, and the player with the color of this piece CAN make a jump, then that
	 *              jump must be made, so this is an invalid move
	 *      6) If this move is a jump, and there are more jumps to be made after this jump, then those jumps must
	 *              be made as well, so this is an invalid move
	 *
	 * @param board The board being played on
	 * @param player The player whose turn it is
	 * @param startRow
	 *      The starting row to move from, should be within board bounds
	 * @param startCell
	 *      The starting cell to move from, should be within board bounds
	 * @param endRow
	 *      The ending row to move to, should be within board bounds
	 * @param endCell
	 *      The ending cell to move to, should be within board bounds
	 * @return
	 *      An enum MoveResult representing how the validation of the move checked out, see MoveResult for details
	 */
	public static MoveResult validateMove(Board board, Piece.PieceColor player, int startRow, int startCell, int endRow, int endCell) {

		List<Move> validJumps = calculateValidJumps(board, player);

		if (!board.hasPieceAt(startRow, startCell)) {   // #1
			return MoveResult.PIECE_NULL_ERR;
		}

		if (board.hasPieceAt(endRow, endCell)) {        // #2
			return MoveResult.END_OCCUPIED_ERR;
		}

		Piece.PieceColor color = board.getPieceColorAt(startRow, startCell);

		if (color == Piece.PieceColor.RED && startRow > endRow                                 // #3 (also checks if
				&& board.getPieceTypeAt(startRow,startCell) == Piece.PieceType.SINGLE) {  //a single piece is used,
			// it is invalid if so)
			return MoveResult.MOVE_DIRECTION_ERR;
		}
		else if (color == Piece.PieceColor.RED && player == Piece.PieceColor.WHITE){     //checking for turn
			return MoveResult.NOT_TURN_ERR;
		}
		else if (color == Piece.PieceColor.WHITE && startRow < endRow
				&& board.getPieceTypeAt(startRow,startCell) == Piece.PieceType.SINGLE) {
			return MoveResult.MOVE_DIRECTION_ERR;
		}
		else if (color == Piece.PieceColor.WHITE && player == Piece.PieceColor.RED){    //checking for turn
			return MoveResult.NOT_TURN_ERR;
		}
		else if (Math.sqrt(Math.pow(endRow - startRow, 2.0) + Math.pow(endCell - startCell, 2.0)) > 1.5) {    // #4
			if (!validJumps.isEmpty()) { // #5 & #6
				if (isValidJump(board, player, new Position(startRow, startCell), new Position(endRow, endCell)) == null) {
					return MoveResult.INVALID_JUMP;
				} else {
					return MoveResult.OK;
				}
			}
			return MoveResult.TOO_FAR_ERR;
		} else if (!validJumps.isEmpty()) {
			return MoveResult.MUST_MAKE_JUMP;
		}

		return MoveResult.OK;
	}
}
