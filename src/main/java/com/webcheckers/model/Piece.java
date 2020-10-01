package com.webcheckers.model;

/**
 * A class to represent a checkers piece on the board.
 */
public class Piece {
    /**
     * A enum representing the type of piece
     */
    public enum PieceType {
        SINGLE("SINGLE");

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

    private String type;
    private String color;

    /**
     * Creates a new piece from a color and type
     * @param color
     * @param type
     */
    public Piece(PieceColor color, PieceType type) {
        this.color = color.toString();
        this.type = type.toString();
    }

    /**
     * Gets the piece type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Gets the piece color
     */
    public String getColor() {
        return this.color;
    }
}
