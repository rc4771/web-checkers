package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
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
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

    public static final String USERNAME_PARAM = "username";
    public static final String PLAYER_SESSION_KEY = "currentUser";

    private final GameCenter gameCenter;
    private final TemplateEngine templateEngine;

    /**
     * The constructor for the {@code POST /backupMove} route handler.
     *
     * @param gameCenter
     *    The game center instance for handling log in related stuff
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostBackupMoveRoute(final GameCenter gameCenter, final TemplateEngine templateEngine) {
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");

        LOG.config("PostBackupMoveRoute is initialized.");
    }

    /**
     *
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostBackupMoveRoute is invoked.");

        int gameID = Integer.parseInt(request.queryParams("gameID"));
        Game game = gameCenter.getGame(gameID);
        game.resetPendingMove();

        String type = "INFO", message = "";

        return String.format("{\"type\":\"%s\", \"text\":\"%s\"}", type, message);
    }
}
