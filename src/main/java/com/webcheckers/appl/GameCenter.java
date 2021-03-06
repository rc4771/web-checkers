package com.webcheckers.appl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;

/**
 * An object to coordinate games and game statistics across the site.
 *
 * @author David Allen
 */
public class GameCenter {

    /** Handles logging */
    private static final Logger LOG = Logger.getLogger(GameCenter.class.getName());

    //Constants

    //Attributes

    /**
     * A counter to assign game IDs to game objects.
     */
    private int gameIDCounter = 0;

    /**
     * A hashmap of *active* games, mapping integer game IDs (starting from 0) to their game objects
     */
    private HashMap<Integer, Game> currentGames;

    //Constructors

    /**
     * Creates a new game center to coordinate games across the site
     */
    public GameCenter() {
        gameIDCounter = 0;
        currentGames = new HashMap<>();
    }

    //Public methods

    /**
     * Creates a new game with two players
     * @param redPlayer
     *      The red player, player cannot be null or already in a game
     * @param whitePlayer
     *      The white player, player cannot be null or already in a game
     * @return
     *      A new game object (the game ID is found within Game)
     */
    public Game newGame(Player redPlayer, Player whitePlayer) {
        if (redPlayer == null || whitePlayer == null) {
            return null;
        }

        if (isPlayerInGame(redPlayer) || isPlayerInGame(whitePlayer)) {
            return null;
        }

        LOG.fine(String.format("Created a new game with players '%s' and '%s'", redPlayer.getName(),
                whitePlayer.getName()));
        int gameID = gameIDCounter++;
        Game game = new Game(gameID, redPlayer, whitePlayer);
        currentGames.put(gameID, game);

        return game;
    }

    /**
     * Gets the game object from a game ID.
     * @param gameID
     *      The integer game ID, should not be -1 or less
     * @return
     *      The game object. This CAN be null if the game ID is invalid, or the game is no longer running
     */
    public Game getGame(int gameID) {
        return currentGames.get(gameID);
    }

    /**
     * Checks to see if a player is in a game or not.
     * @param player
     *      The player in question, must not be null
     * @return
     *      True if the player is in a game, false if they aren't
     */
    public boolean isPlayerInGame(Player player) {
        return getGameFromPlayer(player) != -1;
    }

    /**
     * Gets the game a player is currently in.
     * @param player
     *      The player in question, must not be null
     * @return
     *      The game ID that the player is currently in. If the player is not in a game, it returns -1
     */
    public int getGameFromPlayer(Player player) {
        if (player == null) {
            return -1;
        }

        for (int gameID : currentGames.keySet()) {
            Game game = currentGames.get(gameID);

            if ((game.getRedPlayer().getName().equals(player.getName())
                    || game.getWhitePlayer().getName().equals(player.getName()))
                    && game.getActive()) {
                return gameID;
            }
        }

        return -1;
    }

    public List<Game> getGameList() {
        List<Game> games = new ArrayList<>(currentGames.size());

        for (int gameID : currentGames.keySet()) {
            if (currentGames.get(gameID).getActive()) {
                games.add(currentGames.get(gameID));
            }
        }
        
        return games;
    }
}
