package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * The object to coordinate player sign ins for the web application.
 *
 * @author <a href='mailto:dja7394@rit.edu'>David Allen</a>
 */
public class PlayerLobby {
    private static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());

    private HashMap<String, Player> signedInPlayers;

    public PlayerLobby() {
        signedInPlayers = new HashMap<>();
    }

    public boolean isPlayerSignedIn(String username) {
        return signedInPlayers.containsKey(username);
    }

    /**
     * Checks if a string is a valid username, checks to make sure it's not null, not empty, not blank, and only
     * contains alphanumeric characters and spaces.
     * @param username
     *      The username string to test
     * @return
     *      A boolean for if the username is valid or not
     */
    public boolean isValidUsername(String username) {
        if (username == null)
            return false;

        return !username.isBlank() && !username.isEmpty() && username.matches("^[a-zA-Z0-9_ ]*$");
    }
}
