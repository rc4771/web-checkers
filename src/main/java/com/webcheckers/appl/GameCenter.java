package com.webcheckers.appl;

import java.util.logging.Logger;

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

    //Constructors

    //Public methods

    //Create client-specific services for the client who recently connected.
    public PlayerLobby newPlayerLobby() {
        LOG.fine("New lobby services instance created.");
        return new PlayerLobby(this);
    }

    //Create a new game.
    public WebChecker getGame() {
        return new WebChecker();
    }

    //Sitewide statistics when a game is finished.
    public void gameFinished() {
        synchronized (this) {
            totalGames++;
        }
    }

    //Give user messages about the current site statistics.
    public synchronized String getGameStatsMsg() {
        if (totalGames > 1) {
            return String.format(GAMES_PLAYED_FORMAT,totalGames);
        } else {
            return NO_GAME_MSG;
        }
    }

}
