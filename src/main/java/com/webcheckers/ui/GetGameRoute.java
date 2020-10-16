package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.*;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

import static com.webcheckers.ui.GetHomeRoute.*;
import static spark.Spark.halt;

import com.webcheckers.appl.PlayerLobby;

public class GetGameRoute implements Route{
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    //Static string constants for rendering the game view.
    static final String CHESS_BOARD = "chess_board.svg";
    static final String KING_PIECE_RED = "king-piece-red";
    static final String KING_PIECE_WHITE = "king-piece-white";
    static final String SINGLE_PIECE_RED = "single-piece-red.svg";
    static final String SINGLE_PIECE_WHITE = "single-piece-red.svg";

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final GameCenter gameCenter;

    static final String GAME_VIEW = "game.ftl";
    static final String TITLE = "Web Checker";
    static final String GAME_ID_ATTR = "gameID";

    /**
     * The constructor for the {@code GET /game} route handler.
     *
     * @param   templateEngine
     *          {@link TemplateEngine} used for rendering page HTML.
     * @param   gameCenter
     *          {@link GameCenter} used to handle game logic across the site
     */
    GetGameRoute(final TemplateEngine templateEngine, final PlayerLobby playerLobby, final GameCenter gameCenter) {
        this.templateEngine = Objects.requireNonNull(templateEngine,"templateEngine must not be null");
        this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter must not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String handle(Request request, Response response) {
        final Session httpSession = request.session();

        // If there's no gameID, then check to see if we're providing params to create a game
        // then redirect with the new gameID
        if (request.queryParams(GAME_ID_ATTR) == null) {
            // Ensure there's an opponent player to create a new game with, otherwise just go back to home
            String opponentName;
            if ((opponentName = request.queryParams(GetHomeRoute.OPPONENT_USER_ATTR)) == null) {
                response.redirect(String.format("%s?%s=Something invalid happened", WebServer.HOME_URL, ERROR_MESSAGE_ATTR));
                halt();
                return null;
            }

            Player sessionPlayer = httpSession.attribute(PostSignInRoute.PLAYER_SESSION_KEY);
            Player opponentPlayer = playerLobby.getPlayer(opponentName);

            // If the player selected is already in a game, notify the user
            if (gameCenter.isPlayerInGame(opponentPlayer)) {
                return redirectHomeWithMessage(response, "That player is already in a game");
            }

            // Successfully created a new game, redirect with that gameID
            Game game = gameCenter.newGame(sessionPlayer, opponentPlayer);
            response.redirect(String.format("%s?%s=%d", WebServer.GAME_URL, GAME_ID_ATTR, game.getGameID()));
            halt();
            return null;
        }

        final Map<String, Object> vm = new HashMap<>();

        // get the current user
        Player sessionPlayer;
        if ((sessionPlayer = httpSession.attribute(PostSignInRoute.PLAYER_SESSION_KEY)) == null) {
            return redirectHomeWithMessage(response, "Player object from session was null, contact the developers!");
        }

        Map<String, Object> vmCurrentUser = new HashMap<>();
        vmCurrentUser.put(CURRENT_USER_NAME_ATTR, sessionPlayer.getName());
        vm.put(CURRENT_USER_ATTR, vmCurrentUser);

        // Build and display the list of players, excluding the current one, to the home page
        List<String> playerUsernames = playerLobby.getPlayerUsernames(sessionPlayer.getName());
        vm.put(PLAYER_LIST_ATTR, playerUsernames.size() > 0 ? playerUsernames : null);

        Game game;
        if ((game = gameCenter.getGame(Integer.parseInt(request.queryParams(GAME_ID_ATTR)))) == null) {
            return redirectHomeWithMessage(response, "Game object was null, contact the developers!");
        }

        Piece.PieceColor playerColor = game.getPlayerColor(sessionPlayer);
        Player activePlayer = game.getRedPlayer().getIsTurn() ? game.getRedPlayer() : game.getWhitePlayer();

        vm.put(GetHomeRoute.TITLE_ATTR,TITLE);
        vm.put("board",game.getBoard().transposeForColor(playerColor));
        vm.put("viewMode", "PLAY");
        vm.put("redPlayer", game.getRedPlayer());
        vm.put("whitePlayer", game.getWhitePlayer());
        vm.put("activeColor", game.getPlayerColor(activePlayer).toString());

        return templateEngine.render(new ModelAndView(vm, GAME_VIEW));
    }

    /**
     * Redirects with a message to the home page. This is mainly useful for an error, but also for if the player
     * selects another player to start a game with, but that player is already in a game.
     */
    private String redirectHomeWithMessage(Response response, String message) {
        response.redirect(String.format("%s?%s=%s", WebServer.HOME_URL, ERROR_MESSAGE_ATTR, message));
        halt();
        return null;
    }
}
