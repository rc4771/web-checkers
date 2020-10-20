package com.webcheckers.model;

import com.webcheckers.model.pieces.RedSinglePiece;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
public class PositionTest {
    /**
     * Tests equals method
     */
    @Test
    void testEquals(){
        Position CuT = new Position(2, 1);
        assertTrue(CuT.equals(new Position(2, 1)));
        assertFalse(CuT.equals(new Position(3, 2)));
        assertFalse(CuT.equals(new RedSinglePiece()));
    }

}
