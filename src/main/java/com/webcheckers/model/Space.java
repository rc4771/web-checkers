package com.webcheckers.model;

/**
 * A class to represent a space on a board
 *
 * @author Mike White
 */
public abstract class Space {
    private int cellIdx;

    /**
     * Creates a new space object
     * @param cellIdx
     *      The cell index on the board
     */
    public Space(int cellIdx) {
        this.cellIdx = cellIdx;
    }

    /**
     * Gets this space's cell index
     */
    public int getCellIdx() {
        return this.cellIdx;
    }

    /**
     * Checks to see if this space is valid or not, returns a boolean
     * A piece is valid if it's white and no no pieces on it already
     */
    public abstract boolean isValid();
}
