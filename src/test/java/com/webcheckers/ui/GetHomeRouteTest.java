package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import spark.*;

import static com.webcheckers.ui.GetGameRoute.GAME_ID_ATTR;
import static com.webcheckers.ui.GetHomeRoute.ERROR_MESSAGE_ATTR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
class GetHomeRouteTest {
    private GetHomeRoute CuT; //component under test

    private Request request;
    private Session session;
    private Response response;

    private TemplateEngine engine;
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;
    private Player sessionPlayer;

    @BeforeEach
    public void SetUp(){
        playerLobby = new PlayerLobby();
        gameCenter = mock(GameCenter.class);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        session = mock(Session.class);
        request = mock(Request.class);
        sessionPlayer = mock(Player.class);
        CuT = new GetHomeRoute(playerLobby, gameCenter, engine);
        when(request.session()).thenReturn(session);
    }

    @Test
    void testConstructor_playerLobbyNull(){
        assertThrows(NullPointerException.class, () -> new GetHomeRoute(null, gameCenter, engine));
    }

    @Test
    void testConstructor_gameCenterNull(){
        assertThrows(NullPointerException.class, () -> new GetHomeRoute(playerLobby, null, engine));
    }

    @Test
    void testConstructor_templateEngineNull(){
        assertThrows(NullPointerException.class, () -> new GetHomeRoute(playerLobby, gameCenter, null));
    }

    @Test
    void testHandle_NullSessionPlayer(){
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(null);

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            // expected
        }

        verify(gameCenter, times(0)).getGameFromPlayer(ArgumentMatchers.any(Player.class));
        //verify(sessionPlayer, times(0)).getName();
    }

    @Test
    void testHandle_SessionPlayer(){
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(sessionPlayer);

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            // expected
        }

        verify(gameCenter).getGameFromPlayer(ArgumentMatchers.any(Player.class));
        //verify(sessionPlayer).getName();
    }

    @Test
    void testHandle_InvalidGameID(){
        when(request.queryParams(GAME_ID_ATTR)).thenReturn("-1");

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            // expected
        }

        assertNotNull(playerLobby.getPlayerUsernames());
        verify(response, times(0)).redirect(String.format("%s?%s=%d", WebServer.GAME_URL, GetGameRoute.GAME_ID_ATTR, gameCenter.getGameFromPlayer(sessionPlayer)));
    }

    @Test
    void testView(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewName(GetHomeRoute.VIEW_NAME);
    }
}
