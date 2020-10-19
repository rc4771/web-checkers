package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;
import com.webcheckers.util.Message;
import com.webcheckers.util.MoveValidator;
import com.webcheckers.util.exceptions.moves.MoveException;
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

    /** Handles logging */
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

    /** Stores all ongoing games */
    private final GameCenter gameCenter;

    /** Parses Json */
    private final Gson gson;

    /**
     * The constructor for the {@code POST /backupMove} route handler.
     *
     * @param gameCenter
     *          the GameCenter used to handle game logic across the site
     * @param gson
     *          The GSON instance to parse JSON objects and strings
     */
    public PostValidateMoveRoute(final GameCenter gameCenter, final Gson gson) {
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
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
        Board board = game.getBoard();
        Piece.PieceColor player = game.getCurrentTurn();

        JsonObject jsonData = gson.fromJson(request.queryParams("actionData"), JsonObject.class);
        int startRow = jsonData.get("start").getAsJsonObject().get("row").getAsInt();
        int startCell = jsonData.get("start").getAsJsonObject().get("cell").getAsInt();
        int endRow = jsonData.get("end").getAsJsonObject().get("row").getAsInt();
        int endCell = jsonData.get("end").getAsJsonObject().get("cell").getAsInt();

        Message msg;
        try {
            MoveValidator.validateMove(board, player, startRow, startCell, endRow, endCell);
            msg = Message.info("Move is valid, you can either Backup that move or Submit your turn");
            game.setPendingMove(startRow, startCell, endRow, endCell);
        } catch (MoveException me) {
            msg = me.toMessage();
        }

        return gson.toJson(msg, Message.class);
    }
}
