package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.webcheckers.appl.GameCenter;
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
 * The {@code POST /submitTurn} route handler.
 *
 * @author <a href='mailto:dja7394@rit.edu'>David Allen</a>
 */
public class PostSubmitTurnRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

    public static final String USERNAME_PARAM = "username";
    public static final String PLAYER_SESSION_KEY = "currentUser";

    private final GameCenter gameCenter;
    private final TemplateEngine templateEngine;
    private final Gson gson;

    /**
     * The constructor for the {@code POST /backupMove} route handler.
     *
     * @param gameCenter
<<<<<<< HEAD
     *    The game center instance for handling log in related stuff
=======
     *          the GameCenter used to handle game logic across the site
>>>>>>> master
     * @param templateEngine
     *          the HTML template rendering engine
     * @param gson
     *          The GSON instance to parse JSON objects and strings
     */
    public PostSubmitTurnRoute(final GameCenter gameCenter, final TemplateEngine templateEngine, final Gson gson) {
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
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

        Game.WinType winType = game.checkWin();

        Message msg;

        //set game active to false if a player wins
        if (!winType.equals(Game.WinType.NO_WIN)) {
            game.setActive(false);
        }

        msg = Message.info("Move submitted successfully");
        return gson.toJson(msg, Message.class);

    }
}
