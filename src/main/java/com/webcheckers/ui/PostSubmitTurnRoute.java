package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The {@code POST /submitTurn} route handler.
 *
 * @author <a href='mailto:dja7394@rit.edu'>David Allen</a>
 */
public class PostSubmitTurnRoute implements Route {

    /** Handles logging */
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

    /** Stores all of the games */
    private final GameCenter gameCenter;

    /** Handles JSON objects */
    private final Gson gson;

    /**
     * The constructor for the {@code POST /backupMove} route handler.
     *
     * @param gameCenter
     *    The game center instance for handling log in related stuff
     * @param gson
     *          The GSON instance to parse JSON objects and strings
     */
    public PostSubmitTurnRoute(final GameCenter gameCenter, final Gson gson) {
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        this.gson = Objects.requireNonNull(gson, "gson is required");

        LOG.config("PostSubmitTurnRoute is initialized.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostSubmitTurnRoute is invoked.");

        int gameID = Integer.parseInt(request.queryParams("gameID"));
        Game game = gameCenter.getGame(gameID);

        game.submitMove();

        return gson.toJson(Message.info("Move submitted successfully"), Message.class);
    }
}
