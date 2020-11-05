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
public class PostSpectatorCheckTurnRouteTest {
    private Gson gson;
    private GameCenter gameCenter;
    private PostSpectatorCheckTurnRoute CuT;
    private Request request;
    private Session session;
    private Response response;
    private Game game;
    private Player redPlayer;
    private Player whitePlayer;
    private Player sessionPlayer;

    @BeforeEach
    void setup(){
        gson = new Gson();
        gameCenter = mock(GameCenter.class);
        CuT = new PostSpectatorCheckTurnRoute(gson, gameCenter);

        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        game = mock(Game.class);
        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);
        sessionPlayer = mock(Player.class);

        when(request.queryParams(GAME_ID_ATTR)).thenReturn("0");
        when(gameCenter.getGame(0)).thenReturn(game);
        when(game.getRedPlayer()).thenReturn(redPlayer);
        when(game.getWhitePlayer()).thenReturn(whitePlayer);
        when(request.session()).thenReturn(session);
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(sessionPlayer);
    }

    @Test
    void testConstructor_valid(){
        new PostSpectatorCheckTurnRoute(gson, gameCenter);
    }

    @Test
    void testConstructor_GsonNull(){
        assertThrows(NullPointerException.class, () -> new PostSpectatorCheckTurnRoute(null, gameCenter));
    }

    @Test
    void testConstructor_GameCenterNull(){
        assertThrows(NullPointerException.class, () -> new PostSpectatorCheckTurnRoute(gson, null));
    }

    @Test
    void testHandle_valid_redPlayerTurn(){
        when(redPlayer.getIsTurn()).thenReturn(true);
        when(whitePlayer.getIsTurn()).thenReturn(false);
        assertEquals(gson.toJson(Message.info("true")), CuT.handle(request, response));
    }

    @Test
    void testHandle_valid_whitePlayerTurn(){
        when(redPlayer.getIsTurn()).thenReturn(false);
        when(whitePlayer.getIsTurn()).thenReturn(true);
        assertEquals(gson.toJson(Message.info("true")), CuT.handle(request, response));
    }

    @Test
    void testHandle_valid_false(){
        when(redPlayer.getIsTurn()).thenReturn(false);
        when(whitePlayer.getIsTurn()).thenReturn(false);
        assertEquals(gson.toJson(Message.info("false")), CuT.handle(request, response));
    }

    @Test
    void testHandle_valid_true(){
        when(redPlayer.getIsTurn()).thenReturn(true);
        when(whitePlayer.getIsTurn()).thenReturn(true);
        assertEquals(gson.toJson(Message.info("true")), CuT.handle(request, response));
    }

    @Test
    void testHandle_invalid(){
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(null);
        assertEquals(gson.toJson(Message.error("false")), CuT.handle(request, response));
    }

}
