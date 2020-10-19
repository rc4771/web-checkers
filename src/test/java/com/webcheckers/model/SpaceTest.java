package com.webcheckers.model;

import com.webcheckers.model.pieces.RedSinglePiece;
import com.webcheckers.model.pieces.WhiteSinglePiece;
import com.webcheckers.model.spaces.BlackSpace;
import com.webcheckers.model.spaces.WhiteSpace;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag ("Model-tier")
public class SpaceTest {
    @Test
    public void testGetCellIdx(){
        Space s = new WhiteSpace(42);
        assertEquals(s.getCellIdx(), 42);

        s = new BlackSpace(42, null);
        assertEquals(s.getCellIdx(), 42);
    }

    @Test
    public void testWhiteSpace_isValid() {
        assertFalse(new WhiteSpace(0).isValid());
    }

    @Test
    public void testBlackSpace_isValid_nullPiece() {
        assertTrue(new BlackSpace(0, null).isValid());
    }

    @Test
    public void testBlackSpace_isValid() {
        assertFalse(new BlackSpace(0, new RedSinglePiece()).isValid());
    }

    @Test
    public void testBlackSpace_getPiece_nullPiece(){
        assertNull(new BlackSpace(0).getPiece());
    }

    @Test
    public void testBlackSpace_getPiece() {
        Piece p = new RedSinglePiece();
        assertEquals(p, new BlackSpace(0, p).getPiece());
    }

    @Test
    public void testBlackSpace_setPiece() {
        BlackSpace s = new BlackSpace(0);
        Piece p = new RedSinglePiece();
        s.setPiece(p);
        
        assertEquals(p, s.getPiece());
    }
}
