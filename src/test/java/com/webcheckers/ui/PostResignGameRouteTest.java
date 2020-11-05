package com.webcheckers.ui;

import static com.webcheckers.ui.GetGameRoute.SESSION_PLAYER_NULL_ERR_MSG;
import static com.webcheckers.ui.GetHomeRoute.ERROR_MESSAGE_ATTR;
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
    private Player sessionPlayer;

    @BeforeEach
    public void setup() {
        response = mock(Response.class);
        session = mock(Session.class);
        request = mock(Request.class);
        gameCenter = mock(GameCenter.class);
        gson = new Gson();
        CuT = new PostResignGameRoute(gameCenter, gson);

        sessionPlayer = mock(Player.class);

        when(request.session()).thenReturn(session);
        when(sessionPlayer.getIsTurn()).thenReturn(true);
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
    public void testHandle_noGame(){
        when(request.queryParams(GAME_ID_ATTR)).thenReturn("0");
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(sessionPlayer);

        assertEquals(gson.toJson(Message.error(PostResignGameRoute.NO_GAME_FOUND_ERR_MSG), Message.class), CuT.handle(request, response));
    }

    @Test
    public void testHandle_valid() {
        Game g = mock(Game.class);
        when(g.getGameID()).thenReturn(0);
        when(request.queryParams(GAME_ID_ATTR)).thenReturn("0");
        when(gameCenter.getGame(Integer.parseInt(request.queryParams(GAME_ID_ATTR)))).thenReturn(g);
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(sessionPlayer);

        when(g.getActive()).thenReturn(false);
        assertEquals(CuT.handle(request, response), gson.toJson(Message.info(PostResignGameRoute.RESIGN_SUCCESSFUL_MSG), Message.class));
    }

    @Test
    public void testHandle_nullPlayer(){
        Game g = mock(Game.class);
        when(g.getGameID()).thenReturn(0);
        when(request.queryParams(GAME_ID_ATTR)).thenReturn("0");
        when(gameCenter.getGame(Integer.parseInt(request.queryParams(GAME_ID_ATTR)))).thenReturn(g);
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(null);

        try{
            CuT.handle(request, response);
        }
        catch (HaltException e){
            //expected
        }
        verify(response).redirect(String.format("%s?%s=%s", WebServer.HOME_URL, ERROR_MESSAGE_ATTR, SESSION_PLAYER_NULL_ERR_MSG));
    }
}
