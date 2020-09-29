package com.webcheckers.model;

public class Space {
    private int cellIdx;
    private Piece piece;

    public Space(int cellIdx, Piece piece) {
        this.cellIdx = cellIdx;
        this.piece = piece;
    }

    public int getCellIdx() {
        return this.cellIdx;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public boolean isValid() {
        return true;
    }
}
