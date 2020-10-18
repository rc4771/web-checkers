package com.webcheckers.model;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
     * Tests if username is invalid
     */
    @Test
    public void invalidName() {
        PlayerLobby playerLobby = new PlayerLobby();

        assertTrue(playerLobby.signInPlayer(INV_NAME) == PlayerLobby.SignInResult.INVALID_USERNAME);
        assertTrue(playerLobby.signInPlayer(EMPTY) == PlayerLobby.SignInResult.INVALID_USERNAME);
    }
}
