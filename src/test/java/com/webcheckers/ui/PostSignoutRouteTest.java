package com.webcheckers.ui;

import static com.webcheckers.ui.GetHomeRoute.ERROR_MESSAGE_ATTR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static spark.Spark.halt;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import spark.*;

@Tag("UI-tier")
public class PostSignoutRouteTest {

    private PostSignoutRoute CuT;
    private Request request;
    private Session session;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby playerLobby;

    // Setup BEFORE EACH test
    @BeforeEach
    public void setup() {
        playerLobby = new PlayerLobby();
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        session = mock(Session.class);
        request = mock(Request.class);
        CuT = new PostSignoutRoute(playerLobby, engine);

        when(request.session()).thenReturn(session);
    }

    @Test
    public void testConstructor() {
        // This shouldn't throw a null pointer exception if it works, that's the "assertion"
        new PostSignoutRoute(playerLobby, engine);
    }

    @Test
    public void testConstructor_nullTE() {
        assertThrows(NullPointerException.class, () -> new PostSignoutRoute(playerLobby, null));
    }

    @Test
    public void testConstructor_nullPlayerLobby() {
        assertThrows(NullPointerException.class, () -> new PostSignoutRoute(null, engine));
    }

    @Test
    public void testHandle() {
        // Setup signed in player
        Player p = new Player("Test123");
        assertEquals(PlayerLobby.SignInResult.OK, playerLobby.signInPlayer(p.getName()));

        // Because this is a mock object we can't set the attribute directly, we need to do this
        // when/then format
        when(request.session().attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(p);

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            // expected
        }

        verify(response).redirect(WebServer.HOME_URL);
    }

    @Test
    public void testHandle_noPlayerSession() {
        session.attribute(PostSignInRoute.PLAYER_SESSION_KEY, null);

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            // expected
        }

        verify(response).redirect(String.format("%s?%s=%s", WebServer.HOME_URL, ERROR_MESSAGE_ATTR, PostSignoutRoute.NO_SESSION_LAYER_ERROR_MESSAGE));
    }


    @Test
    public void testHandle_playerNotLoggedIn() {
        Player p = new Player("Test123");

        // Because this is a mock object we can't set the attribute directly, we need to do this
        // when/then format
        when(request.session().attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(p);

        try {
            CuT.handle(request, response);
            fail("Redirects invoke halt exceptions.");
        } catch (HaltException e) {
            // expected
        }

        verify(response).redirect(String.format("%s?%s=%s", WebServer.HOME_URL, ERROR_MESSAGE_ATTR, PlayerLobby.SignOutResult.PLAYER_NOT_LOGGED_IN.getErrorMessage()));
    }
}
