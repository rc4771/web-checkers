package com.webcheckers.model;

/**
 * A class that represents a move.
 */
public class Move {
    /**
     * the starting position of the move
     */
    private Position start;
    /**
     * the end position of the move
     */
    private Position end;

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

}
