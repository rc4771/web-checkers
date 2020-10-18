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
import static spark.Spark.redirect;

/**
 * The {@code POST /validMove} route handler.
 *
 * @author <a href='mailto:dja7394@rit.edu'>David Allen</a>
 */
public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

    public static final String USERNAME_PARAM = "username";
    public static final String PLAYER_SESSION_KEY = "currentUser";

    private final GameCenter gameCenter;
    private final TemplateEngine templateEngine;
    private final Gson gson;

    /**
     * The constructor for the {@code POST /backupMove} route handler.
     *
<<<<<<< HEAD:src/main/java/com/webcheckers/ui/PostValidMoveRoute.java
     * @param gameCenter The game center instance for handling log in related stuff
=======
     * @param gameCenter
     *          the GameCenter used to handle game logic across the site
>>>>>>> master:src/main/java/com/webcheckers/ui/PostValidateMoveRoute.java
     * @param templateEngine
     *          the HTML template rendering engine
     * @param gson
     *          The GSON instance to parse JSON objects and strings
     */
    public PostValidateMoveRoute(final GameCenter gameCenter, final TemplateEngine templateEngine, final Gson gson) {
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.gson = Objects.requireNonNull(gson, "gson is required");

        LOG.config("PostValidateMoveRoute is initialized.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostValidateMoveRoute is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();

        int gameID = Integer.parseInt(request.queryParams("gameID"));
        Game game = gameCenter.getGame(gameID);

        JsonObject jsonData = gson.fromJson(request.queryParams("actionData"), JsonObject.class);
        int startRow = jsonData.get("start").getAsJsonObject().get("row").getAsInt();
        int startCell = jsonData.get("start").getAsJsonObject().get("cell").getAsInt();
        int endRow = jsonData.get("end").getAsJsonObject().get("row").getAsInt();
        int endCell = jsonData.get("end").getAsJsonObject().get("cell").getAsInt();

        Game.MoveResult moveResult = game.validateMove(startRow, startCell, endRow, endCell);
        Message msg;

        // Set the message type and text based on the move validation result
        switch (moveResult) {
            case OK: {
                msg = Message.info("Move is valid, you can either Backup that move or Submit your turn");
                game.setPendingMove(startRow, startCell, endRow, endCell);
                break;
            }
            case PIECE_NULL_ERR: {
                // When a PIECE_NULL_ERR happens, it's because the player is dragging the piece they've already dragged
                // a second time, so they should back the piece up first using the button, THEN re drag
                msg = Message.error("Please reset your move before trying to move again by using the Backup button");
                break;
            }
            case END_OCCUPIED_ERR: {
                msg = Message.error("There is a piece at the space you're jumping to");
                break;
            }
            case MOVE_DIRECTION_ERR: {
                msg = Message.error("That type of piece cannot move in that direction");
                break;
            }
            case TOO_FAR_ERR: {
                msg = Message.error("Your cannot move more than 1 space without jumping");
                break;
            }
            case NOT_TURN_ERR: {
                msg = Message.error("It is not your turn, please wait for the other player to finish their turn");
                break;
            } case INVALID_JUMP: {
                msg = Message.error("That is not a valid jump move");
                break;
            } case MUST_MAKE_JUMP: {
                msg = Message.error("You made a single move, even though you can make a jump. Please jump");
                break;
            }
            default: {
                msg = Message.error("An unknown error has occurred, please contact the developers!");
            }
        }

        return gson.toJson(msg, Message.class);
    }
}
