package com.webcheckers.model;

import com.webcheckers.model.pieces.RedSinglePiece;
import com.webcheckers.model.pieces.WhiteKingPiece;
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

    @Test
    public void test_copy() {
        BlackSpace b1 = new BlackSpace(0, new RedSinglePiece());
        BlackSpace b2 = new BlackSpace(1, new WhiteKingPiece());
        WhiteSpace w1 = new WhiteSpace(0);
        WhiteSpace w2 = new WhiteSpace(1);

        // test cell indexes are equal
        assertEquals(b1.copy().getCellIdx(), b1.getCellIdx());
        assertEquals(b2.copy().getCellIdx(), b2.getCellIdx());
        assertEquals(w1.copy().getCellIdx(), w1.getCellIdx());
        assertEquals(w2.copy().getCellIdx(), w2.getCellIdx());

        BlackSpace b1c = (BlackSpace) b1.copy();
        BlackSpace b2c = (BlackSpace) b2.copy();

        // test pieces are equal
        assertEquals(b1c.getPiece().getColor(), b1.getPiece().getColor());
        assertEquals(b1c.getPiece().getType(), b1.getPiece().getType());
        assertEquals(b2c.getPiece().getType(), b2.getPiece().getType());
        assertEquals(b2c.getPiece().getColor(), b2.getPiece().getColor());
    }
}
