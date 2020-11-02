package com.webcheckers.model;

import java.util.LinkedList;
import java.util.List;

/**
 * A class to represent a checkers piece on the board.
 *
 * @author Mike White
 */
public abstract class Piece {
    /**
     * A enum representing the type of piece
     *
     * @author Mike White
     */
    public enum PieceType {
        SINGLE("SINGLE"),
        KING("KING");

        private String type;

        /**
         * The type of piece
         * @param type
         */
        private PieceType(String type) {
            this.type = type;
        }

        /**
         * Converts to a string
         * @return The value as a string
         */
        public String toString() {
            return this.type;
        }
    }

    /**
     * An enum representing the piece color
     *
     * @author Mike White
     */
    public enum PieceColor {
        RED("RED"), WHITE("WHITE");

        /** The color as a string */
        private String color;

        /**
         * Creates the color
         * @param color The color as a string
         */
        private PieceColor(String color) {
            this.color = color;
        }

        /**
         * Converts the value to a string
         * @return The string
         */
        public String toString() {
            return this.color;
        }

        /**
         * Returns the opposite color
         * @return the opposite color
         */
        public PieceColor opposite() {
            if (this == RED) {
                return WHITE;
            } else {
                return RED;
            }
        }
    }

    /**
     * Creates a new piece from a color and type
     */
    protected Piece() {}

    /**
     * Gets the piece type
     *
     * @return The piece type
     */
    public abstract PieceType getType();

    /**
     * Gets the piece color
     *
     * @return the piece color
     */
    public abstract PieceColor getColor();

    /**
     * Gets the possible moves that the piece can make
     * @param board The board being played on
     * @param position The position of the piece
     * @return The list of valid single moves
     */
    public abstract LinkedList<Move> getSingleMoves(Board board, Position position);

    /**
     * Gets the possible jumps that could be made by this piece
     * @param board The board being played on
     * @param position The position of the piece
     * @return The list of jumps that can be made
     */
    public abstract LinkedList<Move> getJumps(Board board, Position position);

    /**
     * Gets the possible jumps that could be made by this piece
     * @param board The board being played on
     * @param position The position of the piece
     * @param move The previous move
     * @return The list of jumps that can be made
     */
    public abstract LinkedList<Move> getJumps(Board board, Position position, Move move);

    /**
     * Checks that a single move is valid
     * It's valid if the ending space is valid
     * Adds the new move to the list of moves if it's possible
     * @param moves The list of moves to add to
     * @param board The board the move is being made on
     * @param start The starting position of the piece
     * @param end The end position of the piece
     */
    protected void checkSingleMove(List<Move> moves, Board board, Position start, Position end) {
        if (board.spaceIsValidAt(end.getRow(), end.getCell())) {
            moves.add(new Move(start, end));
        }
    }

    /**
     * Adds a valid jump move to a list, if possible
     * @param moves The list of moves to add to
     * @param board The board being played on
     * @param start The starting position
     * @param row_dir The vertical direction to move in (+1 or -1)
     * @param cell_dir The horizontal direction to move in (+1 or -1)
     */
    protected void checkJump(List<Move> moves, Board board, Position start, int row_dir, int cell_dir) {

        int row = start.getRow();
        int cell = start.getCell();

        Position end = new Position(row + (2 * row_dir), cell + (2 * cell_dir));
        Position capture = new Position(row + row_dir, cell + cell_dir);

        if (board.getPieceColorAt(capture.getRow(), capture.getCell()) == this.getColor().opposite() &&
                board.spaceIsValidAt(end.getRow(), end.getCell())) {
            Move.PieceCapture pieceCapture = new Move.PieceCapture(board.getPieceAt(capture.getRow(), capture.getCell()), capture);
            Move newMove = new Move(start, end, pieceCapture);
            LinkedList<Move> newJumps = getJumps(board, end, newMove);
            if (newJumps.isEmpty()) {
                moves.add(newMove);
            } else {
                moves.addAll(newJumps);
            }
        }
    }

    /**
     * Adds a jump to the list of jumps, if possible
     * @param moves The list of moves to add to
     * @param board The board being played on
     * @param start The starting position of the piece
     * @param row_dir The vertical direction to move in (+1 or -1)
     * @param cell_dir The horizontal direction to move in (+1 or -1)
     * @param move The previous move
     */
    protected void checkJump(List<Move> moves, Board board, Position start, int row_dir, int cell_dir, Move move) {

        int row = start.getRow();
        int cell = start.getCell();

        Position end = new Position(row + (2 * row_dir), cell + (2 * cell_dir));
        Position capture = new Position(row + row_dir, cell + cell_dir);

        if (board.getPieceColorAt(capture.getRow(), capture.getCell()) == this.getColor().opposite() &&
                board.spaceIsValidAt(end.getRow(), end.getCell()) &&
                !move.pieceCaptured(capture)) {
            Move.PieceCapture pieceCapture = new Move.PieceCapture(board.getPieceAt(capture.getRow(), capture.getCell()), capture);
            Move newMove = move.addMove(end, pieceCapture);
            LinkedList<Move> newJumps = getJumps(board, end, newMove);
            if (newJumps.isEmpty()) {
                moves.add(newMove);
            } else {
                moves.addAll(newJumps);
            }
        }
    }
}
