package com.webcheckers.model;

import com.webcheckers.model.pieces.RedSinglePiece;
import com.webcheckers.model.pieces.WhiteSinglePiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("Model-tier")
public class MoveTest {

    private WhiteSinglePiece piece;

    /**
     * Sets up objects and mocks to be used in the test
     */
    @BeforeEach
    void setup(){
        piece = mock(WhiteSinglePiece.class);
    }

    /**
     * Test to check pieceCaptured method
     */
    @Test
    void testPieceCaptured(){
        Move CuT = new Move(new Position(3,6), new Position(5,4),
                new Move.PieceCapture(piece , new Position(4,5)));

        assertTrue(CuT.pieceCaptured(new Position(4,5)));
    }
}
