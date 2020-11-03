package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostCheckTurnRouteTest {

    private PostCheckTurnRoute CuT;
    private Gson gson;
    private Request request;
    private Session session;
    private Response response;

    @BeforeEach
    public void setup() {
        response = mock(Response.class);
        session = mock(Session.class);
        request = mock(Request.class);
        gson = new Gson();
        CuT = new PostCheckTurnRoute(mock(GameCenter.class), gson);
        when(request.session()).thenReturn(session);
    }

    @Test
    public void testConstructor() {
        new PostCheckTurnRoute(mock(GameCenter.class), new Gson());
    }

    @Test
    public void testConstructor_nullGson() {
        assertThrows(NullPointerException.class, () -> new PostCheckTurnRoute(mock(GameCenter.class), null));
    }

    @Test
    public void testHandle_noPlayerInSession() {
        assertEquals(gson.toJson(Message.error("false")), CuT.handle(request, response));
    }

    @Test
    public void testHandle_inactivePlayer() {
        Player p = new Player("Test123");
        p.setIsTurn(false);

        when(request.session().attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(p);
        assertEquals(gson.toJson(Message.info("false")), CuT.handle(request, response));
    }

    @Test
    public void testHandle_activePlayer() {
        Player p = new Player("Test123");
        p.setIsTurn(true);

        when(request.session().attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(p);
        assertEquals(gson.toJson(Message.info("true")), CuT.handle(request, response));
    }
}
