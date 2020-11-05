package com.webcheckers.ui;

import static com.webcheckers.ui.GetHomeRoute.ERROR_MESSAGE_ATTR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static spark.Spark.halt;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
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
    private GameCenter gameCenter;

    /**
     * Sets up objects and mocks to be used during the test
     */
    @BeforeEach
    public void setup() {
        playerLobby = mock(PlayerLobby.class);
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        session = mock(Session.class);
        request = mock(Request.class);
        gameCenter = mock(GameCenter.class);
        CuT = new PostSignoutRoute(gameCenter, playerLobby, engine);

        when(request.session()).thenReturn(session);
    }

    /**
     * Tests the valid path for the constructor
     */
    @Test
    public void testConstructor() {
        // This shouldn't throw a null pointer exception if it works, that's the "assertion"
        new PostSignoutRoute(gameCenter, playerLobby, engine);
    }

    /**
     * Test the constructor failing with a null TemplateEngine
     */
    @Test
    public void testConstructor_nullTE() {
        assertThrows(NullPointerException.class, () -> new PostSignoutRoute(gameCenter, playerLobby, null));
    }

    /**
     * Tests the constructor failing with a null PlayerLobby
     */
    @Test
    public void testConstructor_nullPlayerLobby() {
        assertThrows(NullPointerException.class, () -> new PostSignoutRoute(gameCenter, null, engine));
    }

    /**
     * Tests the constructor failing with a null GameCenter
     */
    @Test
    public void testConstructor_nullGameCenter() {
        assertThrows(NullPointerException.class, () -> new PostSignoutRoute(null, playerLobby, engine));
    }

    /**
     * Tests the handle() method with a valid path
     */
    @Test
    public void testHandle() {
        // Setup signed in player
        Player player = mock(Player.class);
        when(player.getName()).thenReturn("TestPlayer123");
        when(playerLobby.signInPlayer(player.getName())).thenReturn(PlayerLobby.SignInResult.OK);
        assertEquals(PlayerLobby.SignInResult.OK, playerLobby.signInPlayer(player.getName()));

        // Because this is a mock object we can't set the attribute directly, we need to do this
        // when/then format
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(player);
        when(playerLobby.signOutPlayer(player)).thenReturn(PlayerLobby.SignOutResult.OK);

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            // expected
        }

        verify(response).redirect(WebServer.HOME_URL);
    }

    /**
     * Tests the handle() method with a null Player in the session assuring it returns an error
     */
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

    /**
     * Tests the make sure handle() returns an error with a player that isn't logged in
     */
    @Test
    public void testHandle_playerNotLoggedIn() {
        Player player = mock(Player.class);

        // Because this is a mock object we can't set the attribute directly, we need to do this
        // when/then format
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(player);
        when(playerLobby.signOutPlayer(player)).thenReturn(PlayerLobby.SignOutResult.PLAYER_NOT_LOGGED_IN);

        try {
            CuT.handle(request, response);
            fail("Redirects invoke halt exceptions.");
        } catch (HaltException e) {
            // expected
        }

        verify(response).redirect(String.format("%s?%s=%s", WebServer.HOME_URL, ERROR_MESSAGE_ATTR, PlayerLobby.SignOutResult.PLAYER_NOT_LOGGED_IN.getErrorMessage()));
    }

    /**
     * Tests the handle() method that it redirects to home during a game
     */
    @Test
    public void testHandle_activeGame(){
        Player player = mock(Player.class);
        Game game = mock(Game.class);
        when(player.getName()).thenReturn("TestPlayer123");
        when(playerLobby.signInPlayer(player.getName())).thenReturn(PlayerLobby.SignInResult.OK);
        assertEquals(PlayerLobby.SignInResult.OK, playerLobby.signInPlayer(player.getName()));

        // Because this is a mock object we can't set the attribute directly, we need to do this
        // when/then format
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(player);
        when(playerLobby.signOutPlayer(player)).thenReturn(PlayerLobby.SignOutResult.OK);
        when(gameCenter.getGameFromPlayer(player)).thenReturn(0);
        when(gameCenter.getGame(0)).thenReturn(game);

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            // expected
        }

        verify(response).redirect(WebServer.HOME_URL);

    }
}
