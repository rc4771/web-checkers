package com.webcheckers.model;

import java.util.logging.Logger;

/**
 * Represents a player
 *
 * @author <a href='mailto:dja7394@rit.edu'>David Allen</a>
 */
public class Player implements Comparable<Player> {
    private static final Logger LOG = Logger.getLogger(Player.class.getName());
    private int wins;
    private int losses;

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

    /**
     * Checks if it is the current player's turn
     * @return True if it is
     */
    public boolean getIsTurn() {return isTurn;}

    /**
     * Sets whether or not it's the player's turn
     * @param turn The value to set it to
     */
    public void setIsTurn(boolean turn) {
        this.isTurn = turn;
}

    /**
     * Get the ranking of the player
     * @return The win ration of the player
     */
    public double score() {
        if (wins + losses > 0) {
            return ((double) wins) / ((double) (wins + losses));
        } else {
            return 0;
        }
    }

    /**
     * Indicate that the player has won a game
     */
    public void winGame() {
        wins++;
    }

    /**
     * Indicate that a player has lost a game
     */
    public void loseGame() {
        losses++;
    }

    /**
     * Compares two players by their score
     * @param player The player to compare to
     * @return A positive number is the score is greater, a negative number if less, and a zero if equal
     */
    public int compareTo(Player player) {
        return (int) Math.round((this.score() - player.score()) * Integer.MAX_VALUE);
    }
}
