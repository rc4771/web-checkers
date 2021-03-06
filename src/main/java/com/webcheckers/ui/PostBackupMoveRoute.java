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
 * The {@code POST /backupMove} route handler.
 *
 * @author <a href='mailto:dja7394@rit.edu'>David Allen</a>
 */
public class PostBackupMoveRoute implements Route {

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
    public PostBackupMoveRoute(final GameCenter gameCenter, final Gson gson) {
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        this.gson = Objects.requireNonNull(gson, "gson is required");

        LOG.config("PostBackupMoveRoute is initialized.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostBackupMoveRoute is invoked.");

        int gameID = Integer.parseInt(request.queryParams("gameID"));
        Game game = gameCenter.getGame(gameID);
        game.resetPendingMove();

        return gson.toJson(Message.info("Move backed up successfully"), Message.class);
    }
}
