package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.webcheckers.ui.GetHomeRoute.*;
import static spark.Spark.halt;

/**
 * sends the game page in spectator mode to the user
 *
 * @author Rafeed Choudhury
 */
public class GetSpectatorGameRoute implements Route {

    /** Renders the web page */
    private final TemplateEngine templateEngine;


    /** Stores the games */
    private final GameCenter gameCenter;

    /** The name of the FTL file */
    static final String GAME_VIEW = "game.ftl";


    /** The GAME ID attribute */
    static final String GAME_ID_ATTR = "gameID";

    /** The name of the page */
    static final String TITLE = "Web Checker";

    private final Gson gson;

    /**
     * The constructor for the {@code GET /game} route handler.
     *
     * @param   templateEngine
     *          {@link TemplateEngine} used for rendering page HTML.
     * @param   gameCenter
     *          {@link GameCenter} used to handle game logic across the site
     */
    public GetSpectatorGameRoute(final TemplateEngine templateEngine, final GameCenter gameCenter, final Gson gson) {
        this.templateEngine = Objects.requireNonNull(templateEngine,"templateEngine must not be null");
        this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter must not be null");
        this.gson = Objects.requireNonNull(gson, "gson is required");
    }

    @Override
    public Object handle(Request request, Response response) {

        final Game game = gameCenter.getGame(Integer.parseInt(request.queryParams(GAME_ID_ATTR)));
        final Map<String, Object> vm = new HashMap<>();
        final Session httpSession = request.session();
        Player sessionPlayer = httpSession.attribute(PostSignInRoute.PLAYER_SESSION_KEY);
        vm.put("viewMode", "SPECTATOR");
        Map<String, Object> vmCurrentUser = new HashMap<>();
        vmCurrentUser.put(CURRENT_USER_NAME_ATTR, sessionPlayer.getName());
        vm.put(CURRENT_USER_ATTR, vmCurrentUser);

        vm.put(GetHomeRoute.TITLE_ATTR,TITLE);
        vm.put("board",game.getBoard().transposeForColor(game.getCurrentTurn()));
        vm.put("redPlayer", game.getRedPlayer());
        vm.put("whitePlayer", game.getWhitePlayer());
        vm.put("activeColor", game.getCurrentTurn());
        Message msg;
        if (game.getCurrentTurn() == Piece.PieceColor.RED){
            msg = Message.info(String.format("It is now %s 's turn.", game.getRedPlayer().getName()));
        }
        else {
            msg = Message.info(String.format("It is now %s 's turn.", game.getWhitePlayer().getName()));
        }

        if (!game.getActive()){
            if (game.checkWin().equals(Game.WinType.RED_WIN)){
                msg = Message.info(String.format("The game is over! %s has won the game!", game.getRedPlayer().getName()));
            }
            else{
                msg = Message.info(String.format("The game is over! %s has won the game!", game.getWhitePlayer().getName()));
            }
            redirectHomeWithMessage(response, msg.toString());
        }
        vm.put("message", msg);

        return templateEngine.render(new ModelAndView(vm, GAME_VIEW));

    }
    /**
     * Redirects with a message to the home page. This is mainly useful for an error, but also for if the player
     * selects another player to start a game with, but that player is already in a game.
     */
    private String redirectHomeWithMessage(Response response, String message) {
        response.redirect(String.format("%s?%s=%s", WebServer.HOME_URL, Message.Type.INFO, message));
        halt();
        return null;
    }
}
