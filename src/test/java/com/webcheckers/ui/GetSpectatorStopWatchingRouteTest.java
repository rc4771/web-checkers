package com.webcheckers.ui;

import com.google.gson.Gson;
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

import static com.webcheckers.ui.GetGameRoute.GAME_ID_ATTR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class GetSpectatorStopWatchingRouteTest {
    private PlayerLobby playerLobby;
    private GameCenter gameCenter;
    private TemplateEngine templateEngine;
    private GetSpectatorStopWatchingRoute CuT;
    private Response response;
    private Request request;

    @BeforeEach
    void setup(){
        playerLobby = mock(PlayerLobby.class);
        gameCenter = mock(GameCenter.class);
        templateEngine = mock(TemplateEngine.class);
        response = mock(Response.class);
        request = mock(Request.class);

        CuT = new GetSpectatorStopWatchingRoute(playerLobby, gameCenter, templateEngine);
    }

    @Test
    void testConstructor_valid(){
        new GetSpectatorStopWatchingRoute(playerLobby, gameCenter, templateEngine);
    }

    @Test
    void testConstructor_playerLobbyNull(){
        assertThrows(NullPointerException.class, () -> new GetSpectatorStopWatchingRoute(null, gameCenter, templateEngine));
    }

    @Test
    void testConstructor_gameCenterNull(){
        assertThrows(NullPointerException.class, () -> new GetSpectatorStopWatchingRoute(playerLobby, null, templateEngine));
    }

    @Test
    void testConstructor_templateEngineNull(){
        assertThrows(NullPointerException.class, () -> new GetSpectatorStopWatchingRoute(playerLobby, gameCenter, null));
    }

    @Test
    void testHandle_valid(){
        CuT.handle(request, response);
        verify(response).redirect(String.format("%s?%s=%s", WebServer.HOME_URL, Message.Type.INFO, "You have successfully exited the game."));
    }
}
