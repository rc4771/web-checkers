package com.webcheckers.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board implements Iterable<Row> {

    private ArrayList<Row> rows;

    private Board(ArrayList<Row> rows) {
        this.rows = rows;
    }

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

    public Board transposeForColor(String color) {
        if (color.equals("RED")) {
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

    //Create a constructor for RowIterator that can be passed with values.
    //iterator() will put all the rows into RowIterator.
    //RowIterator will have 4 methods from the Iterator doc on Java.
    //Try arraylist. Store the value into the arraylist.
    public RowIterator iterator() {
        return new RowIterator(rows);
    }
}
