package com.webcheckers.ui;

import static com.webcheckers.ui.PostResignGameRoute.GAME_ID_ATTR;
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
        gameCenter = mock(GameCenter.class);
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
        when(request.queryParams(GAME_ID_ATTR)).thenReturn("0");
        assertEquals(CuT.handle(request, response), gson.toJson(Message.error(PostResignGameRoute.NO_GAME_FOUND_ERR_MSG), Message.class));
    }

    @Test
    public void testHandle_valid() {
        Game g = mock(Game.class);
        when(g.getGameID()).thenReturn(0);
        when(request.queryParams(GAME_ID_ATTR)).thenReturn("0");
        when(gameCenter.getGame(Integer.parseInt(request.queryParams(GAME_ID_ATTR)))).thenReturn(g);

        when(g.getActive()).thenReturn(false);
        assertEquals(CuT.handle(request, response), gson.toJson(Message.info(PostResignGameRoute.RESIGN_SUCCESSFUL_MSG), Message.class));
    }
}
