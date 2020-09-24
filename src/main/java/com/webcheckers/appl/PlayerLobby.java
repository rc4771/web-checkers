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

    public enum SignInResult {OK, INVALID_USERNAME, USERNAME_TAKEN}

    private HashMap<String, Player> signedInPlayers;

    public PlayerLobby() {
        signedInPlayers = new HashMap<>();
    }

    /**
     * Attempts to sign in a player with a given username
     * @param username
     *      The username to sign in with. This will be error checked
     * @return
     *      An enum indicating the result of the sign in attempt:
     *      OK                  - If the sign in attempt was successful
     *      INVALID_USERNAME    - If the username entered is invalid
     *      USERNAME_TAKEN      - If the username is already taken by another player
     */
    public SignInResult signInPlayer(String username) {
        if (!isValidUsername(username)) {
            return SignInResult.INVALID_USERNAME;
        }

        if (signedInPlayers.containsKey(username)) {
            return SignInResult.USERNAME_TAKEN;
        }

        signedInPlayers.put(username, new Player(username));

        return SignInResult.OK;
    }

    /**
     * Gets a player instance by it's username.
     * @param username
     *      The username of the player to get
     * @return
     *      The instance of the player. Will be null if there is no player by this username
     */
    public Player getPlayer(String username) {
        return signedInPlayers.get(username);
    }

    /**
     * Checks if a string is a valid username, checks to make sure it's not null, not empty, not blank, and only
     * contains alphanumeric characters and spaces.
     * @param username
     *      The username string to test
     * @return
     *      A boolean for if the username is valid or not
     */
    private boolean isValidUsername(String username) {
        if (username == null)
            return false;

        return !username.isBlank() && !username.isEmpty() && username.matches("^[a-zA-Z0-9_ ]*$");
    }
}
