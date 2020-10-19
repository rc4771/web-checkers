package com.webcheckers.model;

import com.webcheckers.model.pieces.RedSinglePiece;
import com.webcheckers.model.pieces.WhiteSinglePiece;
import com.webcheckers.model.spaces.BlackSpace;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag ("Model-tier")
public class SpaceTest {
    private BlackSpace CuT;
    private int cellIdx;
    private int rowIdx;
    private Piece piece;

    @BeforeEach
    public void setup(){
        cellIdx = 0;
        rowIdx = 0;
        piece = new RedSinglePiece();
        CuT = new BlackSpace(cellIdx, piece);
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
        Piece testPiece = new WhiteSinglePiece();
        CuT.setPiece(testPiece);
        assertEquals(CuT.getPiece(), testPiece);
    }
}
