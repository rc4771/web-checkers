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
}
