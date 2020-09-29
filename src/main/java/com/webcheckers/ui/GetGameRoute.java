package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Board;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

import static com.webcheckers.ui.GetHomeRoute.*;
import static spark.Spark.halt;

import com.webcheckers.model.WebChecker;
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

    public int TABLE_ROW = 8;
    static final String GAME_VIEW = "game.ftl";
    static final String TITLE = "Web Checker";
    static final String GAME_ID_ATTR = "gameID";

    /**
     * The constructor for the {@code GET /game} route handler.
     *
     * @param   templateEngine
     *          {@link TemplateEngine} used for rendering page HTML.
     */
    GetGameRoute(final TemplateEngine templateEngine, final PlayerLobby playerLobby, final GameCenter gameCenter) {
        //validation
        Objects.requireNonNull(templateEngine,"templateEngine must not be null");
        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        this.gameCenter = gameCenter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String handle(Request request, Response response) {
        //get game object and start one if no game is in progress.
        final Session httpSession = request.session();

        if (request.queryParams(GAME_ID_ATTR) == null) {
            Player redPlayer = httpSession.attribute(PostSignInRoute.PLAYER_SESSION_KEY);
            Player whitePlayer = playerLobby.getPlayer(request.queryParams(GetHomeRoute.OPPONENT_USER_ATTR));

            Game game = gameCenter.newGame(redPlayer, whitePlayer);
            response.redirect(String.format("%s?%s=%d", WebServer.GAME_URL, GAME_ID_ATTR, game.getGameID()));
            halt();
            return null;
        }

        final Map<String, Object> vm = new HashMap<>();

        // get the current user
        Player sessionPlayer;
        if ((sessionPlayer = httpSession.attribute(PostSignInRoute.PLAYER_SESSION_KEY)) != null) {
            Map<String, Object> vmCurrentUser = new HashMap<>();
            vmCurrentUser.put(CURRENT_USER_NAME_ATTR, sessionPlayer.getName());
            vm.put(CURRENT_USER_ATTR, vmCurrentUser);

            // Build and display the list of players, excluding the current one, to the home page
            List<String> playerUsernames = playerLobby.getPlayerUsernames(sessionPlayer.getName());
            vm.put(PLAYER_LIST_ATTR, playerUsernames.size() > 0 ? playerUsernames : null);
        }

        Game game = gameCenter.getGame(Integer.parseInt(request.queryParams(GAME_ID_ATTR)));

        vm.put(GetHomeRoute.TITLE_ATTR,TITLE);
        vm.put("board",game.getBoard());
        vm.put("viewMode", "PLAY");
        vm.put("redPlayer", game.getRedPlayer()); // TODO use an actual player
        vm.put("whitePlayer", game.getWhitePlayer()); // TODO use an actual player
        vm.put("activeColor", "RED"); // TODO actually figure this out
        return templateEngine.render(new ModelAndView(vm, GAME_VIEW));
    }
}
