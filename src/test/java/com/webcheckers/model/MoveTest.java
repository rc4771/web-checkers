package com.webcheckers.model;

import com.webcheckers.model.pieces.RedSinglePiece;
import com.webcheckers.model.pieces.WhiteSinglePiece;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
public class MoveTest {
    /**
     * Test to check pieceCaptured method
     */
    @Test
    void testPieceCaptured(){
        Move CuT = new Move(new Position(3,6), new Position(5,4),
                new Move.PieceCapture(new WhiteSinglePiece() , new Position(4,5)));

        assertTrue(CuT.pieceCaptured(new Position(4,5)));
    }
}
