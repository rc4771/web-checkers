package com.webcheckers.model;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerTest {

    private static final String INV_NAME = "#Daca";
    private static final String VALID_NAME = "Daca";
    private static final String EMPTY = null;
    private static final String DUPE_NAME = "Daca";

    /**
     * Test if the username was taken.
     */
    @Test
    public void duplicateName() {
        PlayerLobby playerLobby = new PlayerLobby();

        playerLobby.signInPlayer(VALID_NAME);
        assertTrue(playerLobby.signInPlayer(DUPE_NAME) == PlayerLobby.SignInResult.USERNAME_TAKEN);
    }

    /**
     * Tests if username is valid or not.
     */
    @Test
    public void validName() {
        PlayerLobby playerLobby = new PlayerLobby();

        assertTrue(playerLobby.signInPlayer(VALID_NAME) == PlayerLobby.SignInResult.OK);


    }

    /**
     * Test if the player score is 0 if no game has been played yet.
     */
    @Test
    public void score_noGames() {
        Player CuT = new Player(VALID_NAME);
        assertEquals(0, CuT.score());
    }

    /**
     * Test if the player score is 0 if a game has been lost.
     */
    @Test
    public void score_loseGame() {
        Player CuT = new Player(VALID_NAME);
        CuT.loseGame();
        assertEquals(0, CuT.score());
    }

    /**
     * Test if the player score is +1 if a game has been won.
     */
    @Test
    public void score_winGame() {
        Player CuT = new Player(VALID_NAME);
        CuT.winGame();
        assertEquals(1, CuT.score());
    }

    /**
     * Test if two player score is equal.
     */
    @Test
    public void compareTo_equal() {
        Player CuT1 = new Player(VALID_NAME);
        Player CuT2 = new Player(VALID_NAME);
        CuT1.winGame();
        CuT2.winGame();
        assertEquals(0, CuT1.compareTo(CuT2));
    }
    /**
     * Test if one player has more points than the other.
     */
    @Test
    public void compareTo_greater() {
        Player CuT1 = new Player(VALID_NAME);
        Player CuT2 = new Player(VALID_NAME);
        CuT1.winGame();
        CuT2.loseGame();
        CuT2.winGame();
        assertTrue(CuT1.compareTo(CuT2) > 0);
    }
    /**
     * Test if one player has less points than the other.
     */
    @Test
    public void compareTo_less() {
        Player CuT1 = new Player(VALID_NAME);
        Player CuT2 = new Player(VALID_NAME);
        CuT1.winGame();
        CuT1.loseGame();
        CuT2.winGame();
        assertTrue(CuT1.compareTo(CuT2) < 0);
    }

    /**
     * Tests if username is invalid
     */
    @Test
    public void invalidName() {
        PlayerLobby playerLobby = new PlayerLobby();

        assertTrue(playerLobby.signInPlayer(INV_NAME) == PlayerLobby.SignInResult.INVALID_USERNAME);
        assertTrue(playerLobby.signInPlayer(EMPTY) == PlayerLobby.SignInResult.INVALID_USERNAME);
    }
}
