package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostCheckTurnRouteTest {

    private PostCheckTurnRoute CuT;
    private Gson gson;
    private Request request;
    private Session session;
    private Response response;
    private Player player;
    private GameCenter gameCenter;

    @BeforeEach
    public void setup() {
        response = mock(Response.class);
        session = mock(Session.class);
        request = mock(Request.class);
        gson = new Gson();
        player = mock(Player.class);
        gameCenter = mock(GameCenter.class);
        CuT = new PostCheckTurnRoute(gameCenter, gson);

        when(request.session()).thenReturn(session);
    }

    @Test
    public void testConstructor() {
        new PostCheckTurnRoute(gameCenter, gson);
    }

    @Test
    public void testConstructor_nullGson() {
        assertThrows(NullPointerException.class, () -> new PostCheckTurnRoute(gameCenter, null));
    }

    @Test
    public void testConstructor_nullGameCenter() {
        assertThrows(NullPointerException.class, () -> new PostCheckTurnRoute(null, gson));
    }

    @Test
    public void testHandle_noPlayerInSession() {
        assertEquals(gson.toJson(Message.error("false")), CuT.handle(request, response));
    }

    @Test
    public void testHandle_inactivePlayer() {
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(player);
        Game game = mock(Game.class);
        when(gameCenter.getGame(gameCenter.getGameFromPlayer(player))).thenReturn(game);
        when(game.getActive()).thenReturn(true);
        player.setIsTurn(false);
        when(player.getIsTurn()).thenReturn(false);
        assertEquals(gson.toJson(Message.info("false")), CuT.handle(request, response));
    }

    @Test
    public void testHandle_activePlayer() {
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(player);
        player.setIsTurn(true);
        when(player.getIsTurn()).thenReturn(true);
        assertEquals(gson.toJson(Message.info("true")), CuT.handle(request, response));
    }

    @Test
    public void testHandle_inactiveGame(){
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(player);
        player.setIsTurn(true);
        when(player.getIsTurn()).thenReturn(true);
        Game game = mock(Game.class);
        when(gameCenter.getGameFromPlayer(player)).thenReturn(0);
        when(gameCenter.getGame(0)).thenReturn(game);
        when(game.getActive()).thenReturn(false);
        assertEquals(gson.toJson(Message.info("true")), CuT.handle(request, response));
    }

    @Test
    public void testHandle_NullGame(){
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(player);
        player.setIsTurn(true);
        when(player.getIsTurn()).thenReturn(true);
        Game game = mock(Game.class);
        when(gameCenter.getGameFromPlayer(player)).thenReturn(0);
        when(gameCenter.getGame(0)).thenReturn(null);
        when(game.getActive()).thenReturn(true);
        assertEquals(gson.toJson(Message.info("true")), CuT.handle(request, response));
    }

    @Test
    public void testHandle_NullGame_InactiveGame(){
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(player);
        player.setIsTurn(true);
        when(player.getIsTurn()).thenReturn(true);
        Game game = mock(Game.class);
        when(gameCenter.getGameFromPlayer(player)).thenReturn(0);
        when(gameCenter.getGame(0)).thenReturn(null);
        when(game.getActive()).thenReturn(false);
        assertEquals(gson.toJson(Message.info("true")), CuT.handle(request, response));
    }

    @Test
    public void testHandle_activeGame_existingGame_true(){
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(player);
        player.setIsTurn(false);
        when(player.getIsTurn()).thenReturn(false);
        Game game = mock(Game.class);
        when(gameCenter.getGameFromPlayer(player)).thenReturn(0);
        when(gameCenter.getGame(0)).thenReturn(game);
        when(game.getActive()).thenReturn(true);
        assertEquals(gson.toJson(Message.info("false")), CuT.handle(request, response));
    }

    @Test
    public void testHandle_activeGame_existingGame_false(){
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(player);
        player.setIsTurn(true);
        when(player.getIsTurn()).thenReturn(true);
        Game game = mock(Game.class);
        when(gameCenter.getGameFromPlayer(player)).thenReturn(0);
        when(gameCenter.getGame(0)).thenReturn(game);
        when(game.getActive()).thenReturn(true);
        assertEquals(gson.toJson(Message.info("true")), CuT.handle(request, response));
    }
}
