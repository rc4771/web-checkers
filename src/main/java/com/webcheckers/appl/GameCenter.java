package com.webcheckers.appl;

import java.util.HashMap;
import java.util.logging.Logger;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.model.WebChecker;
import org.eclipse.jetty.util.log.Log;

/**
 * Object made for coordination of Web Application and keep sitewide statistics.
 */
public class GameCenter {
    private static final Logger LOG = Logger.getLogger(GameCenter.class.getName());

    //Constants

    //Output strings for current status.
    public final static String GAMES_PLAYED_FORMAT = "WebChecker has been played %d times.";
    public final static String NO_GAME_MSG = "No games have been played, yet. Let the games begin.";
    //Attributes

    private int totalGames = 0;

    private HashMap<Integer, Game> currentGames;

    //Constructors

    public GameCenter() {
        totalGames = 0;
        currentGames = new HashMap<>();
    }

    //Public methods

    public Game newGame(Player redPlayer, Player whitePlayer) {
        LOG.fine(String.format("Created a new game with players '%s' and '%s'", redPlayer.getUsername(),
                whitePlayer.getUsername()));
        int gameID = totalGames++;
        Game game = new Game(gameID, redPlayer, whitePlayer);
        currentGames.put(gameID, game);

        return game;
    }

    public Game getGame(int gameID) {
        return currentGames.get(gameID);
    }
}
