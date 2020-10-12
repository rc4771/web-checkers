package com.webcheckers.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PieceTest {

    /**
     * Test if the piece has the red color.
     */
    @Test
    public void colorTestRed() {
        Piece piece = new Piece(Piece.PieceColor.RED, Piece.PieceType.SINGLE);
        assertTrue(piece.getColor() == Piece.PieceColor.RED);
    }

    /**
     * Test if the piece has the white color.
     */
    @Test
    public void colorTestWhite() {
        Piece piece = new Piece(Piece.PieceColor.WHITE, Piece.PieceType.SINGLE);
        assertTrue(piece.getColor() == Piece.PieceColor.WHITE);
    }

    /**
     * Test if the piece is a normal piece
     */
    @Test
    public void singleTest() {
        Piece piece = new Piece(Piece.PieceColor.RED, Piece.PieceType.SINGLE);
        assertTrue(piece.getType() == Piece.PieceType.SINGLE);
    }
}
