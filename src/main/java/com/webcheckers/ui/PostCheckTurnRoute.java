package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The {@code POST /checkTurn} route handler.
 *
 * @author <a href='mailto:dja7394@rit.edu'>David Allen</a>
 */
public class PostCheckTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    public static final String USERNAME_PARAM = "username";
    public static final String PLAYER_SESSION_KEY = "currentUser";

    private final GameCenter gameCenter;
    private final Gson gson;

    /**
     * The constructor for the {@code POST /checkTurn} route handler.
     *
     * @param gson
     *          The GSON instance to parse JSON objects and strings
     */
    public PostCheckTurnRoute(GameCenter gameCenter, Gson gson) {
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        this.gson = Objects.requireNonNull(gson, "gson is required");

        LOG.config("PostCheckTurnRoute is initialized.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostCheckTurnRoute is invoked.");
        //
        final Session httpSession = request.session();

        Message msg = Message.error("false");
        Player sessionPlayer;
        if ((sessionPlayer = httpSession.attribute(PostSignInRoute.PLAYER_SESSION_KEY)) != null) {
            Game g;
            if ((g = gameCenter.getGame(gameCenter.getGameFromPlayer(sessionPlayer))) == null || !g.getActive()) {
                msg = Message.info("true");
            } else {
                msg = Message.info(sessionPlayer.getIsTurn() ? "true" : "false");
            }
        }

        return gson.toJson(msg, Message.class);
    }
}
