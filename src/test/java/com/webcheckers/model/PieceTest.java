package com.webcheckers.model;

import com.webcheckers.model.pieces.RedSinglePiece;
import com.webcheckers.model.pieces.WhiteSinglePiece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PieceTest {

    /**
     * Test if the piece has the red color.
     */
    @Test
    public void colorTestRed() {
        Piece piece = new RedSinglePiece();
        assertTrue(piece.getColor() == Piece.PieceColor.RED);
    }

    /**
     * Test if the piece has the white color.
     */
    @Test
    public void colorTestWhite() {
        Piece piece = new WhiteSinglePiece();
        assertTrue(piece.getColor() == Piece.PieceColor.WHITE);
    }

    /**
     * Test if the piece is a normal piece
     */
    @Test
    public void singleTest() {
        Piece piece = new RedSinglePiece();
        assertTrue(piece.getType() == Piece.PieceType.SINGLE);
    }

    /**
     * Test for opposite method
     */
    @Test
    void testOpposite(){
        Piece.PieceColor CuT = Piece.PieceColor.RED;
        assertEquals(Piece.PieceColor.WHITE, CuT.opposite());

        Piece.PieceColor CuT2 = Piece.PieceColor.WHITE;
        assertEquals(Piece.PieceColor.RED, CuT2.opposite());
    }

    /**
     * Test for toString method of PieceType
     */
    @Test
    void testToString(){
        Piece.PieceType CuT = Piece.PieceType.KING;

        assertEquals("KING", CuT.toString());
    }
}
