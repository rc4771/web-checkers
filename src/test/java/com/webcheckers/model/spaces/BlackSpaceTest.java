package com.webcheckers.model.spaces;

import com.webcheckers.model.Piece;
import com.webcheckers.model.pieces.RedSinglePiece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlackSpaceTest {
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
