package com.webcheckers.ui;

import java.util.*;

import com.webcheckers.model.Board;
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

    public int TABLE_ROW = 8;
    static final String GAME_VIEW = "game.ftl";
    static final String TITLE = "Web Checker";
    static final String PIECE_TYPE = "space.piece.type";
    static final String PIECE_COLOR = "space.piece.color";
    static final String ROW_INDEX = "row.index";
    static final String CELL_INDEX = "space.cellIdx";

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
        final PlayerLobby playerLobby =
         httpSession.attribute(GetHomeRoute.PLAYERLOBBY_KEY);

        Board board = new Board();

        final Map<String, Object> vm = new HashMap<>();
        vm.put(GetHomeRoute.TITLE_ATTR,TITLE);
        vm.put("board",board);
        vm.put(ROW_INDEX,"8"); //index by row.
        vm.put(CELL_INDEX,"8"); //index by space.
        vm.put(PIECE_TYPE,"PAWN"); //Is it a king or pawn piece.
        vm.put(PIECE_COLOR,"RED"); //Color of the piece
        return templateEngine.render(new ModelAndView(vm, GAME_VIEW));
    }
}
