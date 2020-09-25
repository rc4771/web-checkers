package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;
import static spark.Spark.halt;

import com.webcheckers.model.WebChecker;
import com.webcheckers.appl.PlayerLobby;

public class GetGameRoute implements Route{
    //Static string constants for rendering the game view.
    static final String CHESS_BOARD = "chess_board.svg";
    static final String KING_PIECE_RED = "king-piece-red";
    static final String KING_PIECE_WHITE = "king-piece-white";
    static final String SINGLE_PIECE_RED = "single-piece-red.svg";
    static final String SINGLE_PIECE_WHITE = "single-piece-red.svg";

    private final TemplateEngine templateEngine;

    static final String VIEW = "game.ftl";
    static final String TITLE = "Web Checker";

    /**
     * The constructor for the {@code GET /game} route handler.
     *
     * @param   templateEngine
     *          {@link TemplateEngine} used for rendering page HTML.
     */
    GetGameRoute(final TemplateEngine templateEngine) {
        //validation
        Objects.requireNonNull(templateEngine,"templateEngine must not be null");
        this.templateEngine = templateEngine;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String handle(Request request, Response response) {
        //get game object and start one if no game is in progress.
        final Session httpSession = request.session();
        //TODO
        final PlayerLobby playerLobby =
         httpSession.attribute(GetHomeRoute.PLAYERLOBBY_KEY);

        //Null playerServices indicates a timed out session or an illegal URL request.
        //In either cases, the program will redirect back to the home page.

        if (playerLobby != null) {
            WebChecker game = playerLobby.currentGame();

            final Map<String, Object> vm = new HashMap<>();
            //TODO
            //Get some vm in here.
            vm.put(GetHomeRoute.TITLE_ATTR,TITLE);
            //Render the game form view.
            return templateEngine.render(new ModelAndView(vm, VIEW));
        } else {
            response.redirect(WebServer.HOME_URL);
            halt();
            return null;
        }
    }
}
