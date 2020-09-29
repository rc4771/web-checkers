package com.webcheckers.model;

import com.webcheckers.ui.PostSignInRoute;
import java.util.logging.Logger;

/**
 *
 *
 * @author <a href='mailto:dja7394@rit.edu'>David Allen</a>
 */
public class Player {
    private static final Logger LOG = Logger.getLogger(Player.class.getName());

    /**
     * This player's username
     */
    private String username;

    private Game currentGame;

    /**
     * Creates a new player with a username
     * @param username
     *      The username for this player. MUST be not be null, empty, or contain non alphanumeric characters
     *      besides spaces
     */
    public Player(String username) {
        this.username = username;

        LOG.fine(String.format("Created a new player with username \"%s\"", username));
    }

    /**
     * Returns the username for this player
     * @return
     *      The username for this player as a string
     */
    public String getUsername() {
        return username;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game game) {
        currentGame = game;
    }
}
