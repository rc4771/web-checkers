package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.*;
import spark.*;

public class PostResignGameRouteTest {
    private PostResignGameRoute CuT;
    private Request request;
    private Session session;
    private Response response;
    private GameCenter gameCenter;
    private Gson gson;

    @BeforeEach
    public void setup() {
        response = mock(Response.class);
        session = mock(Session.class);
        request = mock(Request.class);
        gameCenter = new GameCenter();
        gson = new Gson();
        CuT = new PostResignGameRoute(gameCenter, gson);

        when(request.session()).thenReturn(session);
    }

    @Test
    public void testConstructor_nullGameCenter() {
        assertThrows(NullPointerException.class, () -> new PostResignGameRoute(null, gson));
    }

    @Test
    public void testConstructor_nullGson() {
        assertThrows(NullPointerException.class, () -> new PostResignGameRoute(gameCenter, null));
    }

    @Test
    public void testHandle_noGameID() {
        assertEquals(CuT.handle(request, response), gson.toJson(Message.error(PostResignGameRoute.NO_GAME_ID_ERR_MSG), Message.class));
    }

    @Test
    public void testHandle_noGame() {
        when(request.queryParams(PostResignGameRoute.GAME_ID_ATTR)).thenReturn("0");
        assertEquals(CuT.handle(request, response), gson.toJson(Message.error(PostResignGameRoute.NO_GAME_FOUND_ERR_MSG), Message.class));
    }

    @Test
    public void testHandle_valid() {
        Player p1 = new Player("Test123");
        Player p2 = new Player("Test321");
        Game g = gameCenter.newGame(p1, p2);

        when(request.queryParams(PostResignGameRoute.GAME_ID_ATTR)).thenReturn(Integer.toString(g.getGameID()));
        assertEquals(CuT.handle(request, response), gson.toJson(Message.info(PostResignGameRoute.RESIGN_SUCCESSFUL_MSG), Message.class));
    }
}
