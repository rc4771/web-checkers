package com.webcheckers.model;

/**
 * A class to represent a space on a board
 *
 * @author Mike White
 */
public abstract class Space {

    /** The column the space is in */
    private int cellIdx;

    /**
     * Creates a new space object
     * @param cellIdx
     *      The cell index on the board
     */
    protected Space(int cellIdx) {
        this.cellIdx = cellIdx;
    }

    /**
     * Creates a copy of a space
     * @param space The space to copy
     */
    protected Space(Space space) {this.cellIdx = space.cellIdx;}

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

    /**
     * Creates a copy of the space
     */
    public abstract Space copy();
}
