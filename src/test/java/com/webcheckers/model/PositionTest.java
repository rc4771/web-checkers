package com.webcheckers.model;

import com.webcheckers.model.pieces.RedSinglePiece;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("Model-tier")
public class PositionTest {

    private RedSinglePiece piece;

    /**
     * Tests equals method
     */
    @Test
    void testEquals(){
        piece = mock(RedSinglePiece.class);
        Position CuT = new Position(2, 1);
        assertTrue(CuT.equals(new Position(2, 1)));
        assertFalse(CuT.equals(new Position(3, 2)));
        assertFalse(CuT.equals(piece));
    }

}
