package com.webcheckers.util;

import com.webcheckers.model.*;
import com.webcheckers.util.exceptions.moves.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Checks if a move is valid
 *
 * @author Mike White
 */
public class MoveValidator {

	/**
	 * Creates a list of all of the moves that a player could make
	 * @param board The board being played on
	 * @param player The player whose turn it is
	 * @return The list of possible moves
	 */
	public static List<Move> calculateValidMoves(Board board, Piece.PieceColor player) {
		List<Move> jumps = calculateValidJumps(board, player);

		// if the list of jumps is empty, check single moves
		if (jumps.isEmpty()) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (board.getPieceColorAt(i, j) == player) {
						jumps.addAll(board.getPieceAt(i, j).getSingleMoves(board, new Position(i, j)));
					}
				}
			}
		}

		return jumps;
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
	 * Checks if a move is valid
	 * @param board The board being played on
	 * @param player The player whose turn it is
	 * @param start The starting position of the piece
	 * @param end The end position of the piece
	 * @return Whether or not the move is valid
	 */
	public static boolean isValidMove(Board board, Piece.PieceColor player, Position start, Position end) {
		List<Move> moves = calculateValidMoves(board, player);

		for (Move move: moves) {
			if (move.getStart().equals(start) && move.getEnd().equals(end)) {
				return true;
			}
		}

		return false;
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
	 *
	 * @throws MoveException The move is not valid
	 */
	public static void validateMove(Board board, Piece.PieceColor player, int startRow, int startCell, int endRow, int endCell) throws MoveException {

		List<Move> validJumps = calculateValidJumps(board, player);

		if (!board.hasPieceAt(startRow, startCell)) {   // #1
			throw new PieceNullMoveException();
		}

		if (board.hasPieceAt(endRow, endCell)) {        // #2
			throw new EndOccupiedMoveException();
		}

		Piece.PieceColor color = board.getPieceColorAt(startRow, startCell);

		if (color == Piece.PieceColor.RED && startRow > endRow                                 // #3 (also checks if
				&& board.getPieceTypeAt(startRow,startCell) == Piece.PieceType.SINGLE) {  //a single piece is used,
			// it is invalid if so
			throw new MoveDirectionMoveException();
		}
		else if (color == Piece.PieceColor.RED && player == Piece.PieceColor.WHITE){     //checking for turn
			throw new NotTurnMoveException();
		}
		else if (color == Piece.PieceColor.WHITE && startRow < endRow
				&& board.getPieceTypeAt(startRow,startCell) == Piece.PieceType.SINGLE) {
			throw new MoveDirectionMoveException();
		}
		else if (color == Piece.PieceColor.WHITE && player == Piece.PieceColor.RED){    //checking for turn
			throw new NotTurnMoveException();
		}
		else if (Math.sqrt(Math.pow(endRow - startRow, 2.0) + Math.pow(endCell - startCell, 2.0)) > 1.5) {    // #4
			if (!validJumps.isEmpty()) { // #5 & #6
				if (isValidJump(board, player, new Position(startRow, startCell), new Position(endRow, endCell)) == null) {
					throw new InvalidJumpMoveException();
				} else {
					return;
				}
			}
			throw new TooFarMoveException();
		} else if (!validJumps.isEmpty()) {
			throw new MustJumpMoveException();
		}
	}
}
