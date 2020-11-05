package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.Objects;

import static com.webcheckers.ui.GetGameRoute.GAME_ID_ATTR;

/**
 * checks turns and updates spectator
 *
 * @author Rafeed Choudhury
 */
public class PostSpectatorCheckTurnRoute implements Route {

    private final Gson gson;

    /** Stores all the games */
    private final GameCenter gameCenter;

    public PostSpectatorCheckTurnRoute(final Gson gson, final GameCenter gameCenter){

        this.gson = Objects.requireNonNull(gson, "gson is required");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter must not be null");

    }

    /**
     * Checks the turns for each player in the game spectated
     * @param request the request
     * @param response the response
     * @return message to spectator
     */
    @Override
    public Object handle(Request request, Response response) {
        final Game game = gameCenter.getGame(Integer.parseInt(request.queryParams(GAME_ID_ATTR)));
        Player redPlayer = game.getRedPlayer();
        Player whitePlayer = game.getWhitePlayer();
        final Session httpSession = request.session();

        Message msg = Message.error("false");
        Player sessionPlayer;
        if ((sessionPlayer = httpSession.attribute(PostSignInRoute.PLAYER_SESSION_KEY)) != null) {
            msg = Message.info(redPlayer.getIsTurn() || whitePlayer.getIsTurn() ? "true" : "false");
        }

        return gson.toJson(msg, Message.class);

    }
}
