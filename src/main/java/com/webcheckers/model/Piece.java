package com.webcheckers.model;

public class Piece {

    public enum PieceType {
        SINGLE("single");

        private String type;

        private PieceType(String type) {
            this.type = type;
        }

        public String toString() {
            return this.type;
        }
    }

    public enum PieceColor {
        RED("red"), WHITE("white");

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

    public Piece(PieceColor color, PieceType type) {
        this.color = color.toString();
        this.type = type.toString();
    }

    public String getType() {
        return this.type;
    }

    public String getColor() {
        return this.color;
    }
}
