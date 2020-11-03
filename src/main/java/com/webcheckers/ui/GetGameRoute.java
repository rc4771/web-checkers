package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

/**
 * Sends the game page to the user
 *
 * @author David Allen
 */
public class GetGameRoute implements Route{
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    //Static string constants for rendering the game view.
    static final String CHESS_BOARD = "chess_board.svg";
    static final String KING_PIECE_RED = "king-piece-red";
    static final String KING_PIECE_WHITE = "king-piece-white";
    static final String SINGLE_PIECE_RED = "single-piece-red.svg";
    static final String SINGLE_PIECE_WHITE = "single-piece-red.svg";

    static final String OPPONENT_IN_GAME_ERR_MSG = "That player is already in a game";
    static final String SESSION_PLAYER_NULL_ERR_MSG = "Player object from session was null, contact the developers!";
    static final String GAME_OBJECT_NULL_ERR_MSG = "Game object was null, contact the developers!";

    /** Renders the web page */
    private final TemplateEngine templateEngine;

    /** Stores all the players */
    private final PlayerLobby playerLobby;

    /** Stores the games */
    private final GameCenter gameCenter;
    private final Gson gson;

    /** The name of the FTL file */
    static final String GAME_VIEW = "game.ftl";

    /** The name of the page */
    static final String TITLE = "Web Checker";

    /** The GAME ID attribute */
    static final String GAME_ID_ATTR = "gameID";

    static final String WIN_MSG = "Game Over! You have captured all the pieces! You have won the game!";
    static final String LOSE_MSG = "Game Over! You have lost all your pieces. You have lost the game.";
    static final String RESIGN_MSG = "Game Over! A player has resigned";

    /**
     * The constructor for the {@code GET /game} route handler.
     *
     * @param   templateEngine
     *          {@link TemplateEngine} used for rendering page HTML.
     * @param   gameCenter
     *          {@link GameCenter} used to handle game logic across the site
     */
    GetGameRoute(final TemplateEngine templateEngine, final PlayerLobby playerLobby, final GameCenter gameCenter, final Gson gson) {
        this.templateEngine = Objects.requireNonNull(templateEngine,"templateEngine must not be null");
        this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        this.gson = Objects.requireNonNull(gson, "gson is required");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String handle(Request request, Response response) {
        final Session httpSession = request.session();

        // get the current user
        Player sessionPlayer;
        if ((sessionPlayer = httpSession.attribute(PostSignInRoute.PLAYER_SESSION_KEY)) == null) {
            return redirectHomeWithMessage(response, SESSION_PLAYER_NULL_ERR_MSG);
        }

        // If there's no gameID, then check to see if we're providing params to create a game
        // then redirect with the new gameID
        if (request.queryParams(GAME_ID_ATTR) == null) {
            // Ensure there's an opponent player to create a new game with, otherwise just go back to home
            Player opponentPlayer;
            String opponentName;

            if ((opponentName = request.queryParams(AI_OPPONENT_ATTR)) != null) {
                opponentPlayer = new AIPlayer(Integer.parseInt(opponentName));
            }
            else if ((opponentName = request.queryParams(GetHomeRoute.OPPONENT_USER_ATTR)) != null) {
                opponentPlayer = playerLobby.getPlayer(opponentName);
            } else {
                response.redirect(String.format("%s?%s=Something invalid happened", WebServer.HOME_URL, ERROR_MESSAGE_ATTR));
                halt();
                return null;
            }

            // If the player selected is already in a game, notify the user
            if (gameCenter.isPlayerInGame(opponentPlayer)) {
                return redirectHomeWithMessage(response, OPPONENT_IN_GAME_ERR_MSG);
            }

            // Successfully created a new game, redirect with that gameID
            Game game = gameCenter.newGame(sessionPlayer, opponentPlayer);
            response.redirect(String.format("%s?%s=%d", WebServer.GAME_URL, GAME_ID_ATTR, game.getGameID()));
            halt();
            return null;
        }

        final Map<String, Object> vm = new HashMap<>();

        Map<String, Object> vmCurrentUser = new HashMap<>();
        vmCurrentUser.put(CURRENT_USER_NAME_ATTR, sessionPlayer.getName());
        vm.put(CURRENT_USER_ATTR, vmCurrentUser);

        // Build and display the list of players, excluding the current one, to the home page
        List<String> playerUsernames = playerLobby.getPlayerUsernames(sessionPlayer.getName());
        vm.put(PLAYER_LIST_ATTR, playerUsernames.size() > 0 ? playerUsernames : null);

        Game game;
        if ((game = gameCenter.getGame(Integer.parseInt(request.queryParams(GAME_ID_ATTR)))) == null) {
            return redirectHomeWithMessage(response, GAME_OBJECT_NULL_ERR_MSG);
        }

        Piece.PieceColor playerColor = game.getPlayerColor(sessionPlayer);
        Player activePlayer = game.getRedPlayer().getIsTurn() ? game.getRedPlayer() : game.getWhitePlayer();

        final Map<String, Object> modeOptions = new HashMap<>(2);
        modeOptions.put("isGameOver", false);

        //checking for end of game
        if(!game.getActive()) {
            modeOptions.put("isGameOver", true);
            Game.WinType winType = game.checkWin();
            //check if sessionPlayer won the game
            if ((winType.equals(Game.WinType.RED_WIN) && playerColor.equals(Piece.PieceColor.RED)) ||
                    (winType.equals(Game.WinType.WHITE_WIN) && playerColor.equals(Piece.PieceColor.WHITE))) {
                modeOptions.put("gameOverMessage", WIN_MSG);       //notify player that they won
            }
            else if((winType.equals(Game.WinType.RED_WIN) && playerColor.equals(Piece.PieceColor.WHITE)) || (winType.equals(Game.WinType.WHITE_WIN) &&
                    playerColor.equals(Piece.PieceColor.WHITE))){
                modeOptions.put("gameOverMessage", LOSE_MSG);      //notify player that they lost
                redirectHomeWithMessage(response, LOSE_MSG);
            }
            else{   //notify resignation
                modeOptions.put("gameOverMessage", RESIGN_MSG);
            }
            gameCenter.endGame(game);                             //end the game
        }

        vm.put(GetHomeRoute.TITLE_ATTR,TITLE);
        vm.put("board",game.getBoard().transposeForColor(playerColor));
        vm.put("viewMode", "PLAY");
        vm.put("modeOptionsAsJSON", gson.toJson(modeOptions));
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
