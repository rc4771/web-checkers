package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.*;

import static com.webcheckers.ui.GetGameRoute.GAME_ID_ATTR;
import static com.webcheckers.ui.GetGameRoute.LOSE_MSG;
import static com.webcheckers.ui.GetHomeRoute.AI_OPPONENT_ATTR;
import static com.webcheckers.ui.GetHomeRoute.ERROR_MESSAGE_ATTR;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetGameRouteTest {

    private GetGameRoute CuT;
    private Request request;
    private Session session;
    private Response response;

    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;
    private Gson gson;

    private String testPlayer = "Test123";

    /**
     * setup before each test
     */
    @BeforeEach
    public void setup() {
        playerLobby = new PlayerLobby();
        gameCenter = new GameCenter();
        gson = new Gson();
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        session = mock(Session.class);
        request = mock(Request.class);
        CuT = new GetGameRoute(engine, playerLobby, gameCenter, gson);

        when(request.session()).thenReturn(session);

        playerLobby.signInPlayer(testPlayer);
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(playerLobby.getPlayer(testPlayer));
    }

    /**
     * Test for a NullPointerException for a null templateEngine
     */
    @Test
    public void testConstructor_engineNull() {
        assertThrows(NullPointerException.class, () -> new GetGameRoute(null, playerLobby, gameCenter, gson));
    }

    /**
     * Test for a NullPointerException for a null playerLobby
     */
    @Test
    public void testConstructor_playerLobbyNull() {
        assertThrows(NullPointerException.class, () -> new GetGameRoute(engine, null, gameCenter, gson));
    }

    /**
     * Test for a NullPointerException for a null gameCenter
     */
    @Test
    public void testConstructor_gameCenterNull() {
        assertThrows(NullPointerException.class, () -> new GetGameRoute(engine, playerLobby, null, gson));
    }

    /**
     * Test for a NullPointerException for a null Gson
     */
    @Test
    public void testConstructor_gsonNull() {
        assertThrows(NullPointerException.class, () -> new GetGameRoute(engine, playerLobby, gameCenter, null));
    }

    /**
     * Test to handle a null sessionPlayer
     */
    @Test
    public void testHandle_nullSessionPlayer() {
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(null);

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            // expected
        }

        verify(response).redirect(String.format("%s?%s=%s", WebServer.HOME_URL, ERROR_MESSAGE_ATTR, GetGameRoute.SESSION_PLAYER_NULL_ERR_MSG));
    }

    /**
     * Test to handle no Game ID and a missing opponent
     */
    @Test
    public void testHandle_noGameID_missingOpponent() {
        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            // expected
        }

        verify(response).redirect(String.format("%s?%s=Something invalid happened", WebServer.HOME_URL, ERROR_MESSAGE_ATTR));
    }

    /**
     * Test to handle no gameID and if the opponent is in another game
     */
    @Test
    public void testHandle_noGameID_opponentInGame() {
        Player p = playerLobby.getPlayer(testPlayer);
        playerLobby.signInPlayer("Test1234");
        Player op = playerLobby.getPlayer("Test1234");

        gameCenter.newGame(p, op);

        when(request.queryParams(GetHomeRoute.OPPONENT_USER_ATTR)).thenReturn(op.getName());

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            // expected
        }

        verify(response).redirect(String.format("%s?%s=%s", WebServer.HOME_URL, ERROR_MESSAGE_ATTR, GetGameRoute.OPPONENT_IN_GAME_ERR_MSG));
    }

    /**
     * Test to handle no game ID in a valid case
     */
    @Test
    public void testHandle_noGameID_valid() {
        Player p = playerLobby.getPlayer(testPlayer);
        playerLobby.signInPlayer("Test1234");
        Player op = playerLobby.getPlayer("Test1234");

        when(request.queryParams(GetHomeRoute.OPPONENT_USER_ATTR)).thenReturn(op.getName());

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            // expected
        }

        verify(response).redirect(String.format("%s?%s=%d", WebServer.GAME_URL, GAME_ID_ATTR, 0));
    }

    /**
     * Test to handle an invalid Game ID
     */
    @Test
    public void testHandle_invalidGameID() {
        when(request.queryParams(GAME_ID_ATTR)).thenReturn("-1");

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            // expected
        }

        verify(response).redirect(String.format("%s?%s=%s", WebServer.HOME_URL, ERROR_MESSAGE_ATTR, GetGameRoute.GAME_OBJECT_NULL_ERR_MSG));
    }

    /**
     * Test to handle a valid request
     */
    @Test
    public void testHandle_valid() {
        Player p = playerLobby.getPlayer(testPlayer);
        playerLobby.signInPlayer("Test1234");
        Player op = playerLobby.getPlayer("Test1234");

        Game g = gameCenter.newGame(p, op);
        when(request.queryParams(GAME_ID_ATTR)).thenReturn(Integer.toString(g.getGameID()));

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);
    }

    /**
     * Test to handle with a valid AI Opponent
     */
    @Test
    public void testHandle_valid_AIOpponent(){
        Player p = playerLobby.getPlayer(testPlayer);
        playerLobby.signInPlayer("Test1234");
        Player op = playerLobby.getPlayer("Test1234");
        when(request.queryParams(AI_OPPONENT_ATTR)).thenReturn("test");

        Game g = gameCenter.newGame(p, op);
        when(request.queryParams(GAME_ID_ATTR)).thenReturn(Integer.toString(g.getGameID()));

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);
    }

    /**
     * Test to handle with an active and valid whitePlayer
     */
    @Test
    public void testHandle_valid_activePlayerWhite(){
        Player p = playerLobby.getPlayer(testPlayer);
        playerLobby.signInPlayer("Test1234");
        Player op = playerLobby.getPlayer("Test1234");

        Game g = gameCenter.newGame(p, op);
        when(request.queryParams(GAME_ID_ATTR)).thenReturn(Integer.toString(g.getGameID()));
        g.getWhitePlayer().setIsTurn(true);
        g.getRedPlayer().setIsTurn(false);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);
    }

    /**
     * Test to handle an inactive game due to resignation
     */
    @Test
    public void testHandle_gameInactive_resignation() {
        Player p = playerLobby.getPlayer(testPlayer);
        playerLobby.signInPlayer("Test1234");
        Player op = playerLobby.getPlayer("Test1234");

        Game g = gameCenter.newGame(p, op);
        g.setActive(false);

        when(request.queryParams(GAME_ID_ATTR)).thenReturn(Integer.toString(g.getGameID()));

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);
    }

    /**
     * Test to handle an inactive game due to active redPlayer win
     */
    @Test
    public void testHandle_gameInactive_redWin_redPlayer() {
        Player p = playerLobby.getPlayer(testPlayer);
        playerLobby.signInPlayer("Test1234");
        Player op = playerLobby.getPlayer("Test1234");

        Game g = gameCenter.newGame(p, op);

        // Clear white pieces
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (g.getBoard().hasPieceAt(i, j) && g.getBoard().getPieceColorAt(i, j) == Piece.PieceColor.WHITE) {
                    g.getBoard().setPieceAt(i, j, null);
                }
            }
        }
        g.checkWin();

        g.setActive(false);

        when(request.queryParams(GAME_ID_ATTR)).thenReturn(Integer.toString(g.getGameID()));

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);
    }

    /**
     * Test to handle an inactive game due to active whitPlayer loss
     */
    @Test
    public void testHandle_gameInactive_redWin_whitePlayer() {
        Player p = playerLobby.getPlayer(testPlayer);
        playerLobby.signInPlayer("Test1234");
        Player op = playerLobby.getPlayer("Test1234");
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(op);

        Game g = gameCenter.newGame(p, op);

        // Clear white pieces
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (g.getBoard().hasPieceAt(i, j) && g.getBoard().getPieceColorAt(i, j) == Piece.PieceColor.WHITE) {
                    g.getBoard().setPieceAt(i, j, null);
                }
            }
        }
        g.checkWin();

        g.setActive(false);

        when(request.queryParams(GAME_ID_ATTR)).thenReturn(Integer.toString(g.getGameID()));

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            // expected
        }
        verify(response).redirect(String.format("%s?%s=%s", WebServer.HOME_URL, ERROR_MESSAGE_ATTR, LOSE_MSG));
    }

    /**
     * Test to handle inactive game due to active redPlayer loss
     */
    @Test
    public void testHandle_gameInactive_whiteWin_redPlayer() {
        Player p = playerLobby.getPlayer(testPlayer);
        playerLobby.signInPlayer("Test1234");
        Player op = playerLobby.getPlayer("Test1234");

        Game g = gameCenter.newGame(p, op);

        // Clear red pieces
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (g.getBoard().hasPieceAt(i, j) && g.getBoard().getPieceColorAt(i, j) == Piece.PieceColor.RED) {
                    g.getBoard().setPieceAt(i, j, null);
                }
            }
        }
        g.checkWin();

        g.setActive(false);

        when(request.queryParams(GAME_ID_ATTR)).thenReturn(Integer.toString(g.getGameID()));

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        try {
            CuT.handle(request, response);
        } catch (HaltException e) { //expected
        }
        verify(response).redirect(String.format("%s?%s=%s", WebServer.HOME_URL, ERROR_MESSAGE_ATTR, LOSE_MSG));
    }

    /**
     * Test to handle inactive game to handle active whitePlayer win
     */
    @Test
    void testHandle_gameInactive_whiteWin_whitePlayer(){
        Player p = playerLobby.getPlayer(testPlayer);
        playerLobby.signInPlayer("Test1234");
        Player op = playerLobby.getPlayer("Test1234");

        Game g = gameCenter.newGame(op, p);

        // Clear red pieces
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (g.getBoard().hasPieceAt(i, j) && g.getBoard().getPieceColorAt(i, j) == Piece.PieceColor.RED) {
                    g.getBoard().setPieceAt(i, j, null);
                }
            }
        }
        g.checkWin();

        g.setActive(false);

        when(request.queryParams(GAME_ID_ATTR)).thenReturn(Integer.toString(g.getGameID()));

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewName(GetGameRoute.GAME_VIEW);
    }
}
