package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag ("Model-tier")
public class SpaceTest {
    private Space CuT;
    private int cellIdx;
    private int rowIdx;
    private Piece piece;

    @BeforeEach
    public void setup(){
        cellIdx = 0;
        rowIdx = 0;
        piece = new Piece(Piece.PieceColor.RED, Piece.PieceType.SINGLE);
        CuT = new Space(cellIdx, rowIdx, piece);
    }

    @Test
    public void testGetCellIdx(){
        assertEquals(CuT.getCellIdx(), cellIdx);
    }

    @Test
    public void testGetPiece(){
        assertNotNull(CuT.getPiece());
    }

    @Test
    public void testSetPiece(){
        Piece testPiece = new Piece(Piece.PieceColor.WHITE, Piece.PieceType.SINGLE);
        CuT.setPiece(testPiece);
        assertEquals(CuT.getPiece(), testPiece);
    }
}
