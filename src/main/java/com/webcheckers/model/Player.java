package com.webcheckers.model;

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
    private String name;

    private Game currentGame;

    /**
     * Creates a new player with a username
     * @param name
     *      The username for this player. MUST be not be null, empty, or contain non alphanumeric characters
     *      besides spaces
     */
    public Player(String name) {
        this.name = name;

        LOG.fine(String.format("Created a new player with username \"%s\"", name));
    }

    /**
     * Returns the username for this player
     * @return
     *      The username for this player as a string
     */
    public String getName() {
        return name;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game game) {
        currentGame = game;
    }
}
