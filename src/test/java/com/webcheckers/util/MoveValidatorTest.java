package com.webcheckers.util;

import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.util.exceptions.moves.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("Util-tier")
public class MoveValidatorTest {

    Game createTestGameInstance() {
        Player redPlayer = new Player("testPlayerRed");
        Player whitePlayer = new Player("testPlayerWhite");
        return new Game(0, redPlayer, whitePlayer);
    }

    /**
     * Tests the occupation rules of moving a piece, corresponding to 1) and 2) in validateMove(..).
     */
    @Test
    void testMoveValidationOccupation() {
        final Game CuT = createTestGameInstance();

        // Test where: valid where start has a valid piece and end is empty
        assertDoesNotThrow(() -> MoveValidator.validateMove(CuT.getBoard(), Piece.PieceColor.RED, 2, 7, 3, 6));

        CuT.setPendingMove(2, 7, 3, 6);
        CuT.submitMove();

        assertThrows(PieceNullMoveException.class, () -> MoveValidator.validateMove(CuT.getBoard(), Piece.PieceColor.RED, 3, 7, 4, 5));
        assertThrows(EndOccupiedMoveException.class, () -> MoveValidator.validateMove(CuT.getBoard(), Piece.PieceColor.RED, 2, 5, 3, 6));


        // Test where: move is too far away
        assertThrows(TooFarMoveException.class, () -> MoveValidator.validateMove(CuT.getBoard(), Piece.PieceColor.WHITE, 5, 2, 3, 3));
    }

    /**
     * Tests the direction rule for moving a piece, corresponding to 3) in validateMove(..)
     */
    @Test
    void testMoveValidationDirection() {
        final Game CuT = createTestGameInstance();

        // Red player side
        assertDoesNotThrow(() -> MoveValidator.validateMove(CuT.getBoard(), Piece.PieceColor.RED, 2, 7, 3, 6));
        CuT.setPendingMove(2, 7, 3, 6);
        CuT.submitMove();
        assertThrows(MoveDirectionMoveException.class, () -> MoveValidator.validateMove(CuT.getBoard(), Piece.PieceColor.RED, 3, 6, 2, 7));

        // White player side
        assertDoesNotThrow(() -> MoveValidator.validateMove(CuT.getBoard(), Piece.PieceColor.WHITE, 5, 2, 4, 3));
        CuT.setPendingMove(5, 2, 4, 3);
        CuT.submitMove();
        assertThrows(MoveDirectionMoveException.class, () -> MoveValidator.validateMove(CuT.getBoard(), Piece.PieceColor.WHITE, 4, 3, 5, 2));
    }

    /**
     * Tests to distance rule for moving a piece, corresponding to 4) in validateMove(..)
     */
    @Test
    void testMoveValidationDistance() {
        final Game CuT = createTestGameInstance();
        assertDoesNotThrow(() -> MoveValidator.validateMove(CuT.getBoard(), Piece.PieceColor.RED, 2, 5, 3, 4));
        assertThrows(TooFarMoveException.class, () -> MoveValidator.validateMove(CuT.getBoard(), Piece.PieceColor.RED, 2, 5, 4, 3));
    }

    @Test
    void testMustJump() {
        final Game CuT = createTestGameInstance();
        CuT.setPendingMove(5, 0, 4, 1);
        CuT.submitMove();
        CuT.setPendingMove(4, 1, 3, 2);
        CuT.submitMove();
        assertThrows(MustJumpMoveException.class, () -> MoveValidator.validateMove(CuT.getBoard(), Piece.PieceColor.RED, 2, 1, 3, 0));
    }

    /**
     * Test to check if turns are checked and applied
     */
    @Test
    void testMoveValidationTurn() {
        final Game CuT = createTestGameInstance();

        // Red player side
        assertTrue(CuT.getRedPlayer().getIsTurn());
        assertDoesNotThrow(() -> MoveValidator.validateMove(CuT.getBoard(), Piece.PieceColor.RED, 2, 7, 3, 6));
        CuT.setPendingMove(2, 7, 3, 6);
        CuT.submitMove();
        assertFalse(CuT.getRedPlayer().getIsTurn());
        assertThrows(NotTurnMoveException.class, () -> MoveValidator.validateMove(CuT.getBoard(), Piece.PieceColor.WHITE, 3, 6, 4, 5));

        // White player side
        assertTrue(CuT.getWhitePlayer().getIsTurn());
        assertDoesNotThrow(() -> MoveValidator.validateMove(CuT.getBoard(), Piece.PieceColor.WHITE, 5, 2, 4, 3));
        CuT.setPendingMove(5, 2, 4, 3);
        CuT.submitMove();
        assertFalse(CuT.getWhitePlayer().getIsTurn());
        assertThrows(NotTurnMoveException.class, () -> MoveValidator.validateMove(CuT.getBoard(), Piece.PieceColor.RED, 4, 3, 3, 2));
    }
}
