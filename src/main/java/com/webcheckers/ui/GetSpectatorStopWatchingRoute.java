package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;

/**
 * Redirect spectator to home once exit button is pressed
 *
 * @author Rafeed Choudhury
 */
public class GetSpectatorStopWatchingRoute implements Route {
    /** Stores the players */
    private final PlayerLobby playerLobby;

    /** Stores all the games */
    private final GameCenter gameCenter;

    /** Renders the webpage */
    private final TemplateEngine templateEngine;

    public GetSpectatorStopWatchingRoute(final PlayerLobby playerLobby, final GameCenter gameCenter, final TemplateEngine templateEngine) {
        this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    }
    @Override
    public Object handle(Request request, Response response) {
        String message = "You have successfully exited the game.";
        response.redirect(String.format("%s?%s=%s", WebServer.HOME_URL, Message.Type.INFO, message));
        return null;
    }
}
