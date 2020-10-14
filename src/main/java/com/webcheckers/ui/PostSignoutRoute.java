package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.GetHomeRoute.ERROR_MESSAGE_ATTR;
import static spark.Spark.halt;

/**
 * The {@code POST /signout} route handler.
 *
 * @author <a href='mailto:dja7394@rit.edu'>David Allen</a>
 */
public class PostSignoutRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSignoutRoute.class.getName());

    private static final String NO_SESSION_LAYER_ERROR_MESSAGE = "You are not signed in so you cannot log out (no Player in session)";

    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    /**
     * The constructor for the {@code POST /signout} route handler.
     *
     * @param playerLobby
     *    The player lobby instance for handling log in related stuff
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostSignoutRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");

        LOG.config("PostSignoutRoute is initialized.");
    }

    /**
     * Attempts to sign out a user.
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
        LOG.info("PostSignoutRoute is invoked.");
        //
        final Session httpSession = request.session();

        Player sessionPlayer;
        if ((sessionPlayer = httpSession.attribute(PostSignInRoute.PLAYER_SESSION_KEY)) == null) {
            return redirectHomeWithMessage(response, NO_SESSION_LAYER_ERROR_MESSAGE);
        }

        httpSession.attribute(PostSignInRoute.PLAYER_SESSION_KEY, null);

        PlayerLobby.SignOutResult signOutResult = playerLobby.signOutPlayer(sessionPlayer);

        if (signOutResult != PlayerLobby.SignOutResult.OK) {
            return redirectHomeWithMessage(response, signOutResult.getErrorMessage());
        }

        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }

    /**
     * Redirects with a message to the home page. This is mainly useful for an error.
     */
    private String redirectHomeWithMessage(Response response, String message) {
        response.redirect(String.format("%s?%s=%s", WebServer.HOME_URL, ERROR_MESSAGE_ATTR, message));
        halt();
        return null;
    }
}
