package com.webcheckers.model.pieces;

import com.webcheckers.model.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SinglePieceTest {
    /**
     * Test if the piece is a normal piece
     */
    @Test
    public void singleTest() {
        Piece piece = new RedSinglePiece();
        assertTrue(piece.getType() == Piece.PieceType.SINGLE);
    }
}
