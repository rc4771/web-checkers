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

    public Piece.PieceColor getPieceColorAt(int row, int cell) {
        return getPieceAt(row, cell).getColor();
    }

    public Piece.PieceType getPieceTypeAt(int row, int cell) {
        return getPieceAt(row, cell).getType();
    }

    public Piece getPieceAt(int row, int cell) {
        if (row < 0 || row >= rows.size() || cell < 0 || cell >= rows.size()) { // Rows & cell size must be equal
            return null;
        }

        return rows.get(row).getSpaces().get(cell).getPiece();
    }

    public boolean hasPieceAt(int row, int cell) {
        return getPieceAt(row, cell) != null;
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
