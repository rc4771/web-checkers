package com.webcheckers.model;

/**
 * A class to represent a space on a baord
 */
public class Space {
    private int cellIdx;
    private int rowIdx;
    private Piece piece;

    /**
     * Creates a new space object
     * @param rowIdx
     *      The row index on the board
     * @param cellIdx
     *      The cell index on the board
     * @param piece
     *      The piece this space has
     */
    public Space(int rowIdx, int cellIdx, Piece piece) {
        this.rowIdx = rowIdx;
        this.cellIdx = cellIdx;
        this.piece = piece;
    }

    /**
     * Gets this space's cell index
     */
    public int getCellIdx() {
        return this.cellIdx;
    }

    /**
     * Gets this space's Piece object
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Sets this space's Piece object
     * @param newPiece
     *      The piece to set this space to, this CAN be null if there is no longer a piece here
     */
    public void setPiece(Piece newPiece) {
        this.piece = newPiece;
    }

    /**
     * Returns a boolean of if this space is black or not
     */
    public boolean isBlack() {
        return (rowIdx % 2 == 0) != (cellIdx % 2 == 0);
    }

    /**
     * Checks to see if this space is valid or not, returns a boolean
     */
    public boolean isValid() {
        return piece == null && isBlack();
    }
}
