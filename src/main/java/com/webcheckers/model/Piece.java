package com.webcheckers.model;

/**
 * A class to represent a checkers piece on the board.
 */
public class Piece {
    /**
     * A enum representing the type of piece
     */
    public enum PieceType {
        SINGLE("SINGLE"),
        KING("KING");

        private String type;

        private PieceType(String type) {
            this.type = type;
        }

        public String toString() {
            return this.type;
        }
    }

    /**
     * An enum representing the piece color
     */
    public enum PieceColor {
        RED("RED"), WHITE("WHITE");

        private String color;

        private PieceColor(String color) {
            this.color = color;
        }

        public String toString() {
            return this.color;
        }
    }

    private PieceType type;
    private PieceColor color;

    /**
     * Creates a new piece from a color and type
     * @param color
     * @param type
     */
    public Piece(PieceColor color, PieceType type) {
        this.color = color;
        this.type = type;
    }

    /**
     * Gets the piece type
     */
    public PieceType getType() {
        return this.type;
    }

    /**
     * Gets the piece color
     */
    public PieceColor getColor() {
        return this.color;
    }

    /**
     * sets the type of the Piece
     * @param type the type to set the piece to
     */
    public void setType(PieceType type){this.type = type;}
}
