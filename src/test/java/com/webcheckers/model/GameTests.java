package com.webcheckers.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

    public Game createTestGameInstance() {
        Player redPlayer = new Player("testPlayerRed");
        Player whitePlayer = new Player("testPlayerWhite");
        return new Game(0, redPlayer, whitePlayer);
    }

    /**
     * Tests if submitting moves works or not
     */
    @Test
    public void testMoveSubmission() {
        Game game = createTestGameInstance();

        assertTrue(game.getBoard().hasPieceAt(2, 7));
        assertFalse(game.getBoard().hasPieceAt(3, 6));

        game.setPendingMove(2, 7, 3, 6);
        game.submitMove();

        assertFalse(game.getBoard().hasPieceAt(2, 7));
        assertTrue(game.getBoard().hasPieceAt(3, 6));
    }

    /**
     * Tests if backing up a move works or not
     */
    @Test
    public void testMoveBackup() {
        Game game = createTestGameInstance();

        assertTrue(game.getBoard().hasPieceAt(2, 7));
        assertFalse(game.getBoard().hasPieceAt(3, 6));

        game.setPendingMove(2, 7, 3, 6);
        game.resetPendingMove();
        game.submitMove();      // submitMove() should return early if the pending move was reset

        assertTrue(game.getBoard().hasPieceAt(2, 7));
        assertFalse(game.getBoard().hasPieceAt(3, 6));
    }

    /**
     * Tests the occupation rules of moving a piece, corresponding to 1) and 2) in validateMove(..).
     */
    @Test
    public void testMoveValidationOccupation() {
        Game game = createTestGameInstance();

        // Test where: valid where start has a valid piece and end is empty
        assertEquals(Game.MoveResult.OK, game.validateMove(2, 7, 3, 6));

        game.setPendingMove(2, 7, 3, 6);
        game.submitMove();

        assertEquals(Game.MoveResult.PIECE_NULL_ERR, game.validateMove(3, 7, 4, 5));
        assertEquals(Game.MoveResult.END_OCCUPIED_ERR, game.validateMove(2, 5, 3, 6));


        // Test where: move is too far away
        assertEquals(Game.MoveResult.TOO_FAR_ERR, game.validateMove(2, 3, 4, 1));
    }

    /**
     *  Tests the direction rule for moving a piece, corresponding to 3) in validateMove(..)
     */
    @Test
    public void testMoveValidationDirection() {
        Game game = createTestGameInstance();

        // Red player side
        assertEquals(Game.MoveResult.OK, game.validateMove(2, 7, 3, 6));
        game.setPendingMove(2, 7, 3, 6);
        game.submitMove();
        assertEquals(Game.MoveResult.MOVE_DIRECTION_ERR, game.validateMove(3, 6, 2, 7));
        assertEquals(Game.MoveResult.OK, game.validateMove(3, 6, 4, 5));

        // White player side
        assertEquals(Game.MoveResult.OK, game.validateMove(5, 2, 4, 3));
        game.setPendingMove(5, 2, 4, 3);
        game.submitMove();
        assertEquals(Game.MoveResult.MOVE_DIRECTION_ERR, game.validateMove(4, 3, 5, 2));
        assertEquals(Game.MoveResult.OK, game.validateMove(4, 3, 3, 2));
    }

    /**
     * Tests to distance rule for moving a piece, corresponding to 4) in validateMove(..)
     */
    @Test
    public void testMoveValidationDistance() {
        Game game = createTestGameInstance();
        assertEquals(Game.MoveResult.OK, game.validateMove(2, 5, 3, 4));
        assertEquals(Game.MoveResult.TOO_FAR_ERR, game.validateMove(2, 5, 4, 3));
    }
}
