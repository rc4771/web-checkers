package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
@Tag("Model-tier")
public class GameTest {

    /**
     * creates a Game for the tests
     * @return Game
     */
    Game createTestGameInstance() {
        Player redPlayer = new Player("testPlayerRed");
        Player whitePlayer = new Player("testPlayerWhite");
        return new Game(0, redPlayer, whitePlayer);
    }

    /**
     * Tests if submitting moves works or not
     */
    @Test
    void testMoveSubmission() {
        final Game CuT = createTestGameInstance();

        assertTrue(CuT.getBoard().hasPieceAt(2, 7));
        assertFalse(CuT.getBoard().hasPieceAt(3, 6));

        CuT.setPendingMove(2, 7, 3, 6);
        CuT.submitMove();

        assertFalse(CuT.getBoard().hasPieceAt(2, 7));
        assertTrue(CuT.getBoard().hasPieceAt(3, 6));
    }

    /**
     * Tests if backing up a move works or not
     */
    @Test
    void testMoveBackup() {
        final Game CuT = createTestGameInstance();

        assertTrue(CuT.getBoard().hasPieceAt(2, 7));
        assertFalse(CuT.getBoard().hasPieceAt(3, 6));

        CuT.setPendingMove(2, 7, 3, 6);
        CuT.resetPendingMove();
        CuT.submitMove();      // submitMove() should return early if the pending move was reset

        assertTrue(CuT.getBoard().hasPieceAt(2, 7));
        assertFalse(CuT.getBoard().hasPieceAt(3, 6));
    }

    /**
     * Tests the occupation rules of moving a piece, corresponding to 1) and 2) in validateMove(..).
     */
    @Test
    void testMoveValidationOccupation() {
        final Game CuT = createTestGameInstance();

        // Test where: valid where start has a valid piece and end is empty
        assertEquals(Game.MoveResult.OK, CuT.validateMove(2, 7, 3, 6));

        CuT.setPendingMove(2, 7, 3, 6);
        CuT.submitMove();

        assertEquals(Game.MoveResult.PIECE_NULL_ERR, CuT.validateMove(3, 7, 4, 5));
        assertEquals(Game.MoveResult.END_OCCUPIED_ERR, CuT.validateMove(2, 5, 3, 6));


        // Test where: move is too far away
        assertEquals(Game.MoveResult.TOO_FAR_ERR, CuT.validateMove(5, 2, 3, 3));
    }

    /**
     *  Tests the direction rule for moving a piece, corresponding to 3) in validateMove(..)
     */
    @Test
    void testMoveValidationDirection() {
        final Game CuT = createTestGameInstance();

        // Red player side
        assertEquals(Game.MoveResult.OK, CuT.validateMove(2, 7, 3, 6));
        CuT.setPendingMove(2, 7, 3, 6);
        CuT.submitMove();
        assertEquals(Game.MoveResult.MOVE_DIRECTION_ERR, CuT.validateMove(3, 6, 2, 7));

        // White player side
        assertEquals(Game.MoveResult.OK, CuT.validateMove(5, 2, 4, 3));
        CuT.setPendingMove(5, 2, 4, 3);
        CuT.submitMove();
        assertEquals(Game.MoveResult.MOVE_DIRECTION_ERR, CuT.validateMove(4, 3, 5, 2));
    }

    /**
     * Tests to distance rule for moving a piece, corresponding to 4) in validateMove(..)
     */
    @Test
    void testMoveValidationDistance() {
        final Game CuT = createTestGameInstance();
        assertEquals(Game.MoveResult.OK, CuT.validateMove(2, 5, 3, 4));
        assertEquals(Game.MoveResult.TOO_FAR_ERR, CuT.validateMove(2, 5, 4, 3));
    }

    /**
     * Test to check if turns are checked and applied
     */
    @Test
    void testMoveValidationTurn(){
        final Game CuT = createTestGameInstance();

        // Red player side
        assertTrue(CuT.getRedPlayer().getIsTurn());
        assertEquals(Game.MoveResult.OK, CuT.validateMove(2, 7, 3, 6));
        CuT.setPendingMove(2, 7, 3, 6);
        CuT.submitMove();
        assertFalse(CuT.getRedPlayer().getIsTurn());
        assertEquals(Game.MoveResult.NOT_TURN_ERR, CuT.validateMove(3, 6, 4, 5));

        // White player side
        assertTrue(CuT.getWhitePlayer().getIsTurn());
        assertEquals(Game.MoveResult.OK, CuT.validateMove(5, 2, 4, 3));
        CuT.setPendingMove(5, 2, 4, 3);
        CuT.submitMove();
        assertFalse(CuT.getWhitePlayer().getIsTurn());
        assertEquals(Game.MoveResult.NOT_TURN_ERR, CuT.validateMove(4, 3, 3, 2));
    }

    /**
     * Test to check if getPlayerColor method works.
     */
    @Test
    void testGetPlayerColor(){
        final Game CuT = createTestGameInstance();

        assertEquals(Piece.PieceColor.RED, CuT.getPlayerColor(CuT.getRedPlayer()));
        assertEquals(Piece.PieceColor.WHITE, CuT.getPlayerColor(CuT.getWhitePlayer()));
    }

}
