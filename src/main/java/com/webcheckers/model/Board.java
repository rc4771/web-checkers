package com.webcheckers.model;

import com.webcheckers.model.pieces.RedSinglePiece;
import com.webcheckers.model.pieces.SinglePiece;
import com.webcheckers.model.pieces.WhiteSinglePiece;
import com.webcheckers.model.spaces.BlackSpace;
import com.webcheckers.model.spaces.WhiteSpace;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * An object representing a checkers board in a game
 *
 * @author Mike White
 */
public class Board implements Iterable<Row> {

    /** The rows in the board */
    private ArrayList<Row> rows;

    /**
     * Creates a custom board
     * @param rows The rows
     */
    private Board(ArrayList<Row> rows) {
        this.rows = rows;
    }

    /**
     * Creates a new checkers board, setup in the starting configuration
     */
    public Board() {
        this.rows = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            ArrayList<Space> spaces = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                //*
                if (i == 0 || i == 2) {
                    if (j % 2 == 0) {
                        spaces.add(new BlackSpace(j));
                    } else {
                        spaces.add(new WhiteSpace(j, new RedSinglePiece()));
                    }
                } else if (i == 1) {
                    if (j % 2 == 1) {
                        spaces.add(new BlackSpace(j));
                    } else {
                        spaces.add(new WhiteSpace(j, new RedSinglePiece()));
                    }
                } else if (i == 7 || i == 5) {
                    if (j % 2 == 1) {
                        spaces.add(new BlackSpace(j));
                    } else {
                        spaces.add(new WhiteSpace(j, new WhiteSinglePiece()));
                    }
                } else if (i == 6) {
                    if (j % 2 == 0) {
                        spaces.add(new BlackSpace(j));
                    } else {
                        spaces.add(new WhiteSpace(j, new WhiteSinglePiece()));
                    }
                } else {
                    if (j % 2 == 0) {
                        spaces.add(new BlackSpace(j));
                    } else {
                        spaces.add(new WhiteSpace(j));
                    }
                }//*/
            }
            rows.add(new Row(i, spaces));
        }
    }

    /**
     * Gets the color of the piece at a space
     * @param row
     *      The row to get the piece at, should be within board bounds
     * @param cell
     *      The cell to get the piece at, should be within board bounds
     * @return
     *      The enum representing the piece's color, WILL be null if there is no piece at this space
     */
    public Piece.PieceColor getPieceColorAt(int row, int cell) {
        Piece piece = getPieceAt(row, cell);
        return piece == null ? null : piece.getColor();
    }

    /**
     * Gets the type of the piece at a space
     * @param row
     *      The row to get the piece at, should be within board bounds
     * @param cell
     *      The cell to get the piece at, should be within board bounds
     * @return
     *      The enum representing the piece's type, WILL be null if there is no piece at this space
     */
    public Piece.PieceType getPieceTypeAt(int row, int cell) {
        Piece piece = getPieceAt(row, cell);
        return piece == null ? null : piece.getType();
    }

    /**
     * Checks to see if a given position exists on the board
     * @param row The row
     * @param cell The column
     * @return True if the position is valid on the board
     */
    public boolean inBounds(int row, int cell) {
        return row < 8 && cell < 8 && row >= 0 && cell >= 0;
    }

    /**
     * Gets the piece at a space on the board
     * @param row
     *      The row to get the piece at, should be within board bounds
     * @param cell
     *      The cell to get the piece at, should be within board bounds
     * @return
     *      The piece at this space, WILL be null if there is no piece at this space
     */
    public Piece getPieceAt(int row, int cell) {
        if (!inBounds(row, cell)) { // Rows & cell size must be in the board
            return null; // TODO throw error
        }

        Space space = rows.get(row).getSpaces().get(cell);

        if (space instanceof BlackSpace) {
            return null; // TODO throw error
        } else {
            WhiteSpace whiteSpace = (WhiteSpace) space;
            return whiteSpace.getPiece();
        }
    }

    /**
     * Sets the piece at a given location
     * @param row The row number
     * @param cell The column number
     * @param piece The piece to set it to
     */
    public void setPieceAt(int row, int cell, Piece piece) {
        if (!inBounds(row, cell)) { // Rows & cell size must be in the board
            return; // TODO throw error
        }

        Space space = rows.get(row).getSpaces().get(cell);

        if (space instanceof BlackSpace) {
            return; // TODO throw error
        } else {
            WhiteSpace whiteSpace = (WhiteSpace) space;
            whiteSpace.setPiece(piece);
        }
    }

    /**
     * Removes a piece from a given position in the board
     * @param row The row to remove from
     * @param cell The column to remove from
     */
    public void removePiece(int row, int cell) {
        if (!inBounds(row, cell)) { // Rows & cell size must be in the board
            return; // TODO throw error
        }

        Space space = rows.get(row).getSpaces().get(cell);

        if (space instanceof BlackSpace) {
            return; // TODO throw error
        } else {
            WhiteSpace whiteSpace = (WhiteSpace) space;
            whiteSpace.setPiece(null);
        }
    }

    /**
     * Checks to see if there is a piece at a space
     * @param row
     *      The row to get the piece at, should be within board bounds
     * @param cell
     *      The cell to get the piece at, should be within board bounds
     * @return
     *      A boolean, true if there is a piece and false if there isn't
     */
    public boolean hasPieceAt(int row, int cell) {
        return getPieceAt(row, cell) != null;
    }

    /**
     * Moves a piece from one space to another and performs any jumps/captures in the process. This will null check
     * the start and end spaces, if start is null or end isn't null, the move won't happen
     * @param move The move to make
     */
    public void movePiece(Move move) {

        int startRow = move.getStart().getRow();
        int startCell = move.getStart().getCell();
        int endRow = move.getEnd().getRow();
        int endCell = move.getEnd().getCell();

        Piece piece = getPieceAt(startRow, startCell);
        if (piece == null) {
            return;
        }

        if (hasPieceAt(endRow, endCell)) {
            return;
        }

        Space endSpace = rows.get(endRow).getSpaces().get(endCell);
        Space startSpace = rows.get(startRow).getSpaces().get(startCell);

        if (endSpace instanceof BlackSpace || startSpace instanceof BlackSpace) {
            return; // TODO throw error
        } else {
            WhiteSpace endSpaceWhite = (WhiteSpace) endSpace;
            WhiteSpace startSpaceWhite = (WhiteSpace) startSpace;
            endSpaceWhite.setPiece(piece);
            startSpaceWhite.setPiece(null);
        }

        if (((endRow == 7 && piece.getColor() == Piece.PieceColor.RED)              //check if piece is on opposite
                || (endRow == 0 && piece.getColor() == Piece.PieceColor.WHITE)) &&  //end of the board
                piece.getType() == Piece.PieceType.SINGLE) { // make sure it's single
            SinglePiece single = (SinglePiece) piece;
            setPieceAt(endRow, endCell, single.promote());     //convert the piece to a King
        }

        // remove captured pieces from the board
        for (Move.PieceCapture capture: move.getCaptures()) {
            removePiece(capture.getPosition().getRow(), capture.getPosition().getCell());
        }
    }

    /**
     * Transposes a board for a particular player view, this returns a copy and does not modify the original.
     * @param color
     *      What color to rotate the view for
     * @return
     *      A copy of this board, but rotated to account for a particular player's view
     */
    public Board transposeForColor(Piece.PieceColor color) {
        if (color == Piece.PieceColor.RED) {
            ArrayList<Row> rows = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                ArrayList<Space> spaces = new ArrayList<>();
                for (int j = 0; j < 8; j++) {
                    spaces.add(this.rows.get(7 - i).getSpaces().get(7 - j));
                }
                rows.add(new Row(7-i, spaces));
            }
            return new Board(rows);
        } else {
            return this;
        }
    }

    /**
     * Create a constructor for RowIterator that can be passed with values.
     * iterator() will put all the rows into RowIterator.
     * RowIterator will have 4 methods from the Iterator doc on Java.
     * Try arraylist. Store the value into the arraylist.
     */
    public RowIterator iterator() {
        return new RowIterator(rows);
    }
}
