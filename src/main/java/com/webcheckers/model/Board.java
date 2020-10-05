package com.webcheckers.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * An object representing a checkers board in a game
 */
public class Board implements Iterable<Row> {

    private ArrayList<Row> rows;

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
                        spaces.add(new Space(i, j, null));
                    } else {
                        spaces.add(new Space(i, j, new Piece(Piece.PieceColor.RED, Piece.PieceType.SINGLE)));
                    }
                } else if (i == 1) {
                    if (j % 2 == 1) {
                        spaces.add(new Space(i, j, null));
                    } else {
                        spaces.add(new Space(i, j, new Piece(Piece.PieceColor.RED, Piece.PieceType.SINGLE)));
                    }
                } else if (i == 7 || i == 5) {
                    if (j % 2 == 1) {
                        spaces.add(new Space(i, j, null));
                    } else {
                        spaces.add(new Space(i, j, new Piece(Piece.PieceColor.WHITE, Piece.PieceType.SINGLE)));
                    }
                } else if (i == 6) {
                    if (j % 2 == 0) {
                        spaces.add(new Space(i, j, null));
                    } else {
                        spaces.add(new Space(i, j, new Piece(Piece.PieceColor.WHITE, Piece.PieceType.SINGLE)));
                    }
                } else {
                    spaces.add(new Space(i, j, null));
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
     * Gets the piece at a space on the board
     * @param row
     *      The row to get the piece at, should be within board bounds
     * @param cell
     *      The cell to get the piece at, should be within board bounds
     * @return
     *      The piece at this space, WILL be null if there is no piece at this space
     */
    public Piece getPieceAt(int row, int cell) {
        if (row < 0 || row >= rows.size() || cell < 0 || cell >= rows.size()) { // Rows & cell size must be equal
            return null;
        }

        return rows.get(row).getSpaces().get(cell).getPiece();
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
     * @param startRow
     *      The starting row to move from, should be within board bounds
     * @param startCell
     *      The starting cell to move from, should be within board bounds
     * @param endRow
     *      The ending row to move to, should be within board bounds
     * @param endCell
     *      The endign cell to move to, should be within board bounds
     */
    public void movePiece(int startRow, int startCell, int endRow, int endCell) {
        Piece piece = getPieceAt(startRow, startCell);
        if (piece == null) {
            return;
        }

        if (hasPieceAt(endRow, endCell)) {
            return;
        }

        rows.get(endRow).getSpaces().get(endCell).setPiece(piece);
        rows.get(startRow).getSpaces().get(startCell).setPiece(null);

        // TODO Perform captures in the event of a jump
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
