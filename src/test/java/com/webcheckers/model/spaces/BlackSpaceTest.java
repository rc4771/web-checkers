package com.webcheckers.model.spaces;

import com.webcheckers.model.Piece;
import com.webcheckers.model.pieces.RedSinglePiece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlackSpaceTest {
    /**
     * Tests if space will be valid with no piece
     */
    @Test
    public void testBlackSpace_isValid_nullPiece() {
        assertTrue(new BlackSpace(0, null).isValid());
    }

    /**
     * Tests if space will be invalid with piece
     */
    @Test
    public void testBlackSpace_isValid() {
        assertFalse(new BlackSpace(0, new RedSinglePiece()).isValid());
    }

    /**
     * Tests if space will return a null piece
     */
    @Test
    public void testBlackSpace_getPiece_nullPiece(){
        assertNull(new BlackSpace(0).getPiece());
    }

    /**
     * Tests if getPiece will return correct piece
     */
    @Test
    public void testBlackSpace_getPiece() {
        Piece p = new RedSinglePiece();
        assertEquals(p, new BlackSpace(0, p).getPiece());
    }

    /**
     * Tests if setPiece actually sets a piece in the space
     */
    @Test
    public void testBlackSpace_setPiece() {
        BlackSpace s = new BlackSpace(0);
        Piece p = new RedSinglePiece();
        s.setPiece(p);

        assertEquals(p, s.getPiece());
    }
}
