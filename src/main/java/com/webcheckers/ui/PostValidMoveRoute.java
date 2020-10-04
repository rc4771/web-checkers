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
import static spark.Spark.redirect;

/**
 * The {@code POST /validMove} route handler.
 *
 * @author <a href='mailto:dja7394@rit.edu'>David Allen</a>
 */
public class PostValidMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

    public static final String USERNAME_PARAM = "username";
    public static final String PLAYER_SESSION_KEY = "currentUser";

    private final GameCenter gameCenter;
    private final TemplateEngine templateEngine;
    private final Gson gson;

    /**
     * The constructor for the {@code POST /validMove} route handler.
     *
     * @param playerLobby
     *    The player lobby instance for handling log in related stuff
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostValidMoveRoute(final GameCenter gameCenter, final TemplateEngine templateEngine, final Gson gson) {
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.gson = Objects.requireNonNull(gson, "gson is required");

        LOG.config("PostValidMoveRoute is initialized.");
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
        LOG.finer("PostValidMoveRoute is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();

        int gameID = Integer.parseInt(request.queryParams("gameID"));
        Game game = gameCenter.getGame(gameID);

        JsonObject jsonData = gson.fromJson(request.queryParams("actionData"), JsonObject.class);
        int startRow = jsonData.get("start").getAsJsonObject().get("row").getAsInt();
        int startCell = jsonData.get("start").getAsJsonObject().get("cell").getAsInt();
        int endRow = jsonData.get("end").getAsJsonObject().get("row").getAsInt();
        int endCell = jsonData.get("end").getAsJsonObject().get("cell").getAsInt();

        System.out.println(String.format("(%d, %d) -> (%d, %d)", startRow, startCell, endRow, endCell));

        Game.MoveResult moveResult = game.validateMove(startRow, startCell, endRow, endCell);
        String type, message;

        switch (moveResult) {
            case OK: {
                type = "OK";
                message = "";
                game.setPendingMove(startRow, startCell, endRow, endCell);
                break;
            }
            case PIECE_NULL_ERR: {
                type = "ERROR";
                message = "There is no piece at those coordinates";
                break;
            }
            case END_OCCUPIED_ERR: {
                type = "ERROR";
                message = "There is a piece at the space you're jumping to";
                break;
            }
            case MOVE_DIRECTION_ERR: {
                type = "ERROR";
                message = "That type of piece cannot move in that direction";
                break;
            }
            case TOO_FAR_ERR: {
                type = "ERROR";
                message = "Your cannot move more than 1 space without jumping";
                break;
            }
            default: {
                type = "ERROR";
                message = "An unknown error has occurred, please contact the developers!";
            }
        }

        return String.format("{\"type\":\"%s\", \"text\":\"%s\"}", type, message);
    }
}
