package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.model.Player;
import spark.*;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.util.Message;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import static com.webcheckers.ui.GetGameRoute.SESSION_PLAYER_NULL_ERR_MSG;
import static com.webcheckers.ui.GetHomeRoute.ERROR_MESSAGE_ATTR;
import static spark.Spark.halt;

public class PostResignGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostResignGameRoute.class.getName());

    //String Constants
    static final String GAME_ID_ATTR = "gameID";

    static final String NO_GAME_ID_ERR_MSG = "No gameID found, could not resign";
    static final String NO_GAME_FOUND_ERR_MSG = "No game was found for this gameID, could not resign";
    static final String RESIGN_SUCCESSFUL_MSG = "Resigned successfully";
    static final String NOT_YOUR_TURN_MSG = "It is not your turn to resign";

    //private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;
    private final Gson gson;

    /**
     * The constructor for the {@code POST /resignGame} route handler.
     *
     * @param gameCenter
     *    Used to handle game logic across the site
     * @param gson
     *   Used to convert objects to JSON format
     */
    public PostResignGameRoute(GameCenter gameCenter, Gson gson){
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        this.gson = Objects.requireNonNull(gson, "gson is required");
    }

    /**
     * Attempts to resign player from their game.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   Message with info stored as JSON
     */
    @Override
    public Object handle(Request request, Response response){
        if (request.queryParams(GAME_ID_ATTR) == null) {
            return gson.toJson(Message.error(NO_GAME_ID_ERR_MSG), Message.class);
        }

        // get the current user
        final Session httpSession = request.session();
        Player sessionPlayer;
        if ((sessionPlayer = httpSession.attribute(PostSignInRoute.PLAYER_SESSION_KEY)) == null) {
            return redirectHomeWithMessage(response, SESSION_PLAYER_NULL_ERR_MSG);
        } else {
            // sessionPlayer.loseGame(); // add to losses
        }

        if (!sessionPlayer.getIsTurn()) {
            return gson.toJson(Message.error(NOT_YOUR_TURN_MSG), Message.class);
        }

        Game game;
        if ((game = gameCenter.getGame(Integer.parseInt(request.queryParams(GAME_ID_ATTR)))) == null) {
            return gson.toJson(Message.error(NO_GAME_FOUND_ERR_MSG), Message.class);
        }

        game.setActive(false);
        return gson.toJson(Message.info(RESIGN_SUCCESSFUL_MSG), Message.class);
    }

    /**
     * Redirects with a message to the home page. This is mainly useful for an error, but also for if the player
     * selects another player to start a game with, but that player is already in a game.
     */
    private String redirectHomeWithMessage(Response response, final String message) {
        response.redirect(String.format("%s?%s=%s", WebServer.HOME_URL, ERROR_MESSAGE_ATTR, message));
        halt();
        return null;
    }
}
