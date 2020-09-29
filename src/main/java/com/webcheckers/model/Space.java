package com.webcheckers.model;

public class Space {
    private int cellIdx;
    private int rowIdx;
    private Piece piece;

    public Space(int rowIdx, int cellIdx, Piece piece) {
        this.rowIdx = rowIdx;
        this.cellIdx = cellIdx;
        this.piece = piece;
    }

    public int getCellIdx() {
        return this.cellIdx;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public boolean isBlack() {
        return (rowIdx % 2 == 0) != (cellIdx % 2 == 0);
    }

    public boolean isValid() {
        return piece == null && isBlack();
    }
}
