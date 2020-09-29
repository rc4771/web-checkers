package com.webcheckers.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board implements Iterable<Row> {

    private ArrayList<Row> rows;

    public Board() {
        this.rows = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            ArrayList<Space> spaces = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                //*
                if (i == 0 || i == 2) {
                    if (j % 2 == 0) {
                        spaces.add(new Space(j, null));
                    } else {
                        spaces.add(new Space(j, new Piece(Piece.PieceColor.RED, Piece.PieceType.SINGLE)));
                    }
                } else if (i == 1) {
                    if (j % 2 == 1) {
                        spaces.add(new Space(j, null));
                    } else {
                        spaces.add(new Space(j, new Piece(Piece.PieceColor.RED, Piece.PieceType.SINGLE)));
                    }
                } else if (i == 7 || i == 5) {
                    if (j % 2 == 1) {
                        spaces.add(new Space(j, null));
                    } else {
                        spaces.add(new Space(j, new Piece(Piece.PieceColor.WHITE, Piece.PieceType.SINGLE)));
                    }
                } else if (i == 6) {
                    if (j % 2 == 0) {
                        spaces.add(new Space(j, null));
                    } else {
                        spaces.add(new Space(j, new Piece(Piece.PieceColor.WHITE, Piece.PieceType.SINGLE)));
                    }
                } else {
                    spaces.add(new Space(j, null));
                }//*/
            }
            rows.add(new Row(i, spaces));
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
