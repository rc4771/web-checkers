package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static com.webcheckers.ui.GetGameRoute.GAME_ID_ATTR;
import static com.webcheckers.ui.GetHomeRoute.ERROR_MESSAGE_ATTR;
import static com.webcheckers.ui.PostSignInRoute.PLAYER_SESSION_KEY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
@Tag("UI-tier")
public class GetSpectatorGameRouteTest {
    private GetSpectatorGameRoute CuT;
    private TemplateEngine templateEngine;
    private GameCenter gameCenter;
    private Gson gson;
    private Request request;
    private Response response;
    private Game game;
    private Player redPlayer;
    private Player whitePlayer;

    /**
     * Setup before each test
     */
    @BeforeEach
    void setup(){
        gameCenter = mock(GameCenter.class);
        gson = new Gson();
        templateEngine = mock(TemplateEngine.class);
        CuT = new GetSpectatorGameRoute(templateEngine, gameCenter, gson);
        Session httpSession = mock(Session.class);
        request = mock(Request.class);
        PlayerLobby playerLobby = new PlayerLobby();
        redPlayer = new Player("rafeed");
        whitePlayer = new Player("rafe");
        Player sessionPlayer = new Player("tester");
        playerLobby.signInPlayer("rafeed");
        playerLobby.signInPlayer("rafe");
        playerLobby.signInPlayer("tester");
        when(httpSession.attribute(PLAYER_SESSION_KEY)).thenReturn(sessionPlayer);
        response = mock(Response.class);

        game = mock(Game.class);
        when(request.session()).thenReturn(httpSession);
        when(httpSession.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(sessionPlayer);
        when(request.queryParams(GAME_ID_ATTR)).thenReturn("0");
        when(gameCenter.getGame(0)).thenReturn(game);




    }

    /**
     * Test for a NullPointerException for a null Gson
     */
    @Test
    void testConstructor_gsonNull(){
        assertThrows(NullPointerException.class, () -> new GetSpectatorGameRoute(templateEngine, gameCenter, null));
    }

    /**
     * Test for a NullPointerException for a null gameCenter
     */
    @Test
    void testConstructor_gameCenterNull(){
        assertThrows(NullPointerException.class, () -> new GetSpectatorGameRoute(templateEngine, null, gson));
    }

    /**
     * Test for a NullPointerException for a null templateEngine
     */
    @Test
    void testConstructor_templateEngineNull(){
        assertThrows(NullPointerException.class, () -> new GetSpectatorGameRoute(null, gameCenter, gson));
    }

    /**
     * Test for a valid GetSpectatorGameRoute
     */
    @Test
    void testConstructor_valid(){
        new GetSpectatorGameRoute(templateEngine, gameCenter, gson);
    }

    /**
     * Test to handle a valid request when it's the redPlayer's turn
     */
    @Test
    void handle_valid_redTurn(){
        Board board = mock(Board.class);
        when(game.getBoard()).thenReturn(board);
        when(game.getRedPlayer()).thenReturn(redPlayer);
        when(game.getWhitePlayer()).thenReturn(whitePlayer);
        when(game.getCurrentTurn()).thenReturn(Piece.PieceColor.RED);
        when(board.transposeForColor(Piece.PieceColor.RED)).thenReturn(board);
        when(game.getActive()).thenReturn(true);
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);

    }

    /**
     * Test to handle a valid request when it's the whitePlayer's turn
     */
    @Test
    void handle_valid_whiteTurn(){
        Board board = mock(Board.class);
        when(game.getBoard()).thenReturn(board);
        when(game.getRedPlayer()).thenReturn(redPlayer);
        when(game.getWhitePlayer()).thenReturn(whitePlayer);
        when(game.getCurrentTurn()).thenReturn(Piece.PieceColor.WHITE);
        when(board.transposeForColor(Piece.PieceColor.WHITE)).thenReturn(board);
        when(game.getActive()).thenReturn(true);
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);
    }

    /**
     * Test to handle inactive game due to redPlayer win
     */
    @Test
    void handle_valid_inactive_redWin(){
        Board board = mock(Board.class);
        when(game.getBoard()).thenReturn(board);
        when(game.getRedPlayer()).thenReturn(redPlayer);
        when(game.getWhitePlayer()).thenReturn(whitePlayer);
        when(game.getCurrentTurn()).thenReturn(Piece.PieceColor.RED);
        when(board.transposeForColor(Piece.PieceColor.RED)).thenReturn(board);
        when(game.getActive()).thenReturn(false);
        when(game.checkWin()).thenReturn(Game.WinType.RED_WIN);

        try {
            CuT.handle(request, response);
        } catch (HaltException e){
            //expected
        }

        verify(response).redirect(String.format("%s?%s=%s", WebServer.HOME_URL, Message.Type.INFO, Message.info(String.format("The game is over! %s has won the game!", game.getRedPlayer().getName()))));
    }

    /**
     * Test to handle inactive game due to whitePlayer win
     */
    @Test
    void handle_valid_inactive_whiteWin(){
        Board board = mock(Board.class);
        when(game.getBoard()).thenReturn(board);
        when(game.getRedPlayer()).thenReturn(redPlayer);
        when(game.getWhitePlayer()).thenReturn(whitePlayer);
        when(game.getCurrentTurn()).thenReturn(Piece.PieceColor.WHITE);
        when(board.transposeForColor(Piece.PieceColor.WHITE)).thenReturn(board);
        when(game.getActive()).thenReturn(false);
        when(game.checkWin()).thenReturn(Game.WinType.WHITE_WIN);

        try {
            CuT.handle(request, response);
        } catch (HaltException e){
            //expected
        }

        verify(response).redirect(String.format("%s?%s=%s", WebServer.HOME_URL, Message.Type.INFO, Message.info(String.format("The game is over! %s has won the game!", game.getWhitePlayer().getName()))));
    }

}
