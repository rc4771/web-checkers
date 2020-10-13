package com.webcheckers.model;

import java.util.logging.Logger;

/**
 * Represents a player
 *
 * @author <a href='mailto:dja7394@rit.edu'>David Allen</a>
 */
public class Player {
    private static final Logger LOG = Logger.getLogger(Player.class.getName());

    /**
     * This player's username
     */
    private String name;
    /**
     * Returns true if it's the player's turn
     */
    private boolean isTurn;

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

    public boolean getIsTurn() {return isTurn;}

    public void setIsTurn(boolean turn) {
        this.isTurn = turn;
}
}
