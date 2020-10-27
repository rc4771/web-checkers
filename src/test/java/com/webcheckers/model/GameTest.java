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