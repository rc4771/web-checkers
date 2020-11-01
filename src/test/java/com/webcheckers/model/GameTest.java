package com.webcheckers.model;

import com.webcheckers.util.MoveValidator;
import com.webcheckers.util.exceptions.moves.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
@Tag("Model-tier")
public class GameTest {

    /**
     * creates a Game for the tests
     *
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

    /**
     * Test to check if getPlayerColor method works.
     */
    @Test
    void testGetPlayerColor() {
        final Game CuT = createTestGameInstance();

        assertEquals(Piece.PieceColor.RED, CuT.getPlayerColor(CuT.getRedPlayer()));
        assertEquals(Piece.PieceColor.WHITE, CuT.getPlayerColor(CuT.getWhitePlayer()));

    }

    /**
     * Test to check setIsActive
     */
    @Test
    void testSetIsActive() {
        final Game CuT = createTestGameInstance();

        CuT.setActive(false);
        assertFalse(CuT.getActive());
    }

    /**
     * Tests if jumps work properly
     */
    @Test
    void testSetPendingMoveJump() {
        final Game CuT = createTestGameInstance();

        CuT.setPendingMove(2, 7, 3, 6);
        CuT.submitMove();
        CuT.setPendingMove(5, 4, 4, 5);
        CuT.submitMove();
        CuT.setPendingMove(3, 6, 5, 4);
        CuT.submitMove();

        assertTrue(CuT.getBoard().hasPieceAt(5, 4));
        assertEquals(Piece.PieceColor.RED, CuT.getBoard().getPieceAt(5, 4).getColor());
    }

    /**
     * Tests checking for when white player wins
     */
    @Test
    void testCheckWhiteWin() {
        Game CuT = createTestGameInstance();

        Board board = CuT.getBoard();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.hasPieceAt(i, j)) {
                    if (board.getPieceColorAt(i, j).equals(Piece.PieceColor.RED)) {
                        board.removePiece(i, j);
                        board.setPieceAt(i,j, null);
                    }
                }
            }

        }
        assertEquals(Game.WinType.WHITE_WIN, CuT.checkWin());
    }

    /**
     * Tests checking for when red player wins
     */
    @Test
    void testCheckRedWin() {
        Game CuT = createTestGameInstance();

        Board board = CuT.getBoard();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.hasPieceAt(i, j)) {
                    if (board.getPieceColorAt(i, j).equals(Piece.PieceColor.WHITE)) {
                        board.removePiece(i, j);
                        board.setPieceAt(i,j, null);
                    }
                }
            }

        }
        assertEquals(Game.WinType.RED_WIN, CuT.checkWin());
    }
}