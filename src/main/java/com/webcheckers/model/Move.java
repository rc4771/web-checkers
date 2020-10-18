package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a move.
 */
public class Move {

    public static class PieceCapture {
        /** The piece that was captured */
        private Piece piece;

        /** The position it was captured at */
        private Position position;

        /**
         * A constructor for a piece capture
         * @param piece The piece to capture
         * @param position The position it was captured at
         */
        public PieceCapture(Piece piece, Position position) {
            this.piece = piece;
            this.position = position;
        }

        /**
         * Gets the piece that was captured
         * @return The piece
         */
        public Piece getPiece() {
            return piece;
        }

        /**
         * Gets the position of the capture
         * @return The position
         */
        public Position getPosition() {
            return position;
        }
    }

    /**
     * the starting position of the move
     */
    private Position start;
    /**
     * the end position of the move
     */
    private Position end;

    /** The pieces captured with this move */
    private List<PieceCapture> captures;

    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
        this.captures = new ArrayList<>();
    }

    public Move(Position start, Position end, List<PieceCapture> captures) {
        this.start = start;
        this.end = end;
        this.captures = captures;
    }

    public Move(Position start, Position end, PieceCapture capture) {
        this.start = start;
        this.end = end;
        this.captures = new ArrayList<>();
        this.captures.add(capture);
    }

    /**
     *
     * @return the starting position of the move
     */
    public Position getStart() {return this.start;}

    /**
     *
     * @return the end position of the move
     */
    public Position getEnd() {return this.end;}

    /**
     * Checks to see if a piece at a given position has been captured
     * @param p The position of the piece
     * @return True if it's been captured
     */
    public boolean pieceCaptured(Position p) {
        for (PieceCapture c: captures) {
            if (c.getPosition().equals(p)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return The list of captures
     */
    public List<PieceCapture> getCaptures() {
        return this.captures;
    }

    /**
     * Creates a new Move, that starts at this starting position,
     * and ends at a new position, with a new capture
     * @param end The ending position for the new move
     * @param capture A new capture
     * @return The new move
     */
    public Move addMove(Position end, PieceCapture capture) {
        List<PieceCapture> captures = new ArrayList<>(this.captures);
        captures.add(capture);
        return new Move(this.start, end, captures);
    }

    public Move addMove(Move move) {
        List<PieceCapture> captures = new ArrayList<>(this.captures);
        captures.addAll(move.captures);
        return new Move(this.start, move.end, captures);
    }

}
