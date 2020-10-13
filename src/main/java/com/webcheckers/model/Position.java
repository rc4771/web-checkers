package com.webcheckers.model;

/**
 * A class that represents a position on the board.
 *
 * @author Rafeed Choudhury
 */
public class Position {
    /**
     * The position's row index
     */
    private int row;
    /**
     * The position's cell(column) index
     */
    private int cell;

    /**
     * Constructor to create a Position
     * @param row the row index
     * @param cell the cell index
     */
    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    /**
     *
     * @return the row index of this Position
     */
    public int getRow() {return this.row;}

    /**
     *
     * @return the cell index of this Position
     */
    public int getCell() {return this.cell;}

    /**
     * Checks if this position is equal to another position
     * @param o The object to compare to
     * @return True if they are equal
     */
    public boolean equals(Object o) {
        if (o instanceof Position) {
            Position other = (Position) o;
            return this.row == other.row && this.cell == other.cell;
        } else {
            return false;
        }
    }
}
