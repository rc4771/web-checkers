package com.webcheckers.appl;

import com.webcheckers.model.Player;
import com.webcheckers.util.NameValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * The object to coordinate player sign ins for the web application.
 *
 * @author <a href='mailto:dja7394@rit.edu'>David Allen</a>
 */
public class PlayerLobby {

    //
    //Attributes
    //

    /// Gamecenter for quick call.
    private GameCenter gameCenter;

    /** Handles logging */
    private static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());

    /** The result of the sign-in attempt */
    public enum SignInResult {OK, INVALID_USERNAME, USERNAME_TAKEN}

    /**
     * The grand hashmap containing all players that are signed in, mapped by their username. This username is
     * guaranteed to be not null, non-empty, and strictly alphanumeric (with spaces).
     */
    private HashMap<String, Player> signedInPlayers;

    /**
     * Creates a new PlayerLobby instance to handle sign ins.
     */
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
        if (!NameValidator.isValidUsername(username)) {
            return SignInResult.INVALID_USERNAME;
        }

        if (signedInPlayers.containsKey(username)) {
            return SignInResult.USERNAME_TAKEN;
        }

        signedInPlayers.put(username, new Player(username));

        return SignInResult.OK;
    }

    /**
     * Gets the total number of players signed in.
     * @return
     *      An int representing the total number of signed in players
     */
    public int getPlayerCount() {
        return signedInPlayers.size();
    }

    /**
     * Gets a list of the names of all players currently signed in.
     * @return
     *      A list of all player's usernames
     */
    public List<String> getPlayerUsernames() {
        return getPlayerUsernames(null);
    }

    /**
     * Gets a list of the names of all players currently signed in.
     *
     * @param excludingUsername
     *      A username to exclude from the list returned, as to get all player names EXCEPT one
     *
     * @return
     *      A list of all player's usernames
     */
    public List<String> getPlayerUsernames(String excludingUsername) {
        List<String> usernames = new ArrayList<>();
        for (final Player player : signedInPlayers.values()) {
            if (excludingUsername != null && excludingUsername.equals(player.getName()))
                continue;

            usernames.add(player.getName());
        }

        return usernames;
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
}
