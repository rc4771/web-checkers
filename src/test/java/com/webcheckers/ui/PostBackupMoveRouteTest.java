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
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import spark.*;

import static com.webcheckers.appl.PlayerLobby.SignInResult.OK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
/**
 * Unt testing for PostBackupMoveRoute
 */
public class PostBackupMoveRouteTest {
    /**
     * The Component under Test (CuT)
     */
    private PostBackupMoveRoute CuT;

    //friendly objects
    private GameCenter center;
    private Game game;
    private PlayerLobby lobby;
    private Gson gson;

    private Player redPlayer;
    private Player whitePlayer;

    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;

    @BeforeEach
    /**
     * Initialization for each test
     */
    public void setup() {
        request = Mockito.mock(Request.class);
        session = Mockito.mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        gson = new Gson();

        lobby = mock(PlayerLobby.class);
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(lobby);

        when(lobby.signInPlayer("rafeed")).thenReturn(OK);
        when(lobby.signInPlayer("rafe")).thenReturn(OK);
        lobby.signInPlayer("rafeed");
        lobby.signInPlayer("rafe");

        when(lobby.getPlayer("rafeed")).thenReturn(new Player("rafeed"));
        when(lobby.getPlayer("rafe")).thenReturn(new Player("rafe"));
        redPlayer = lobby.getPlayer("rafeed");
        whitePlayer = lobby.getPlayer("rafe");

        center = mock(GameCenter.class);

        when(center.newGame(redPlayer,whitePlayer)).thenReturn(new Game(0, redPlayer, whitePlayer));
        game = center.newGame(redPlayer, whitePlayer);

        CuT = new PostBackupMoveRoute(center, gson);
    }

    @Test
    /**
     * Tests the handle method
     */
    void handleTest(){
        JsonObject actionData = new JsonObject();
        JsonObject start = new JsonObject();
        start.add("row", new JsonPrimitive("0"));
        start.add("cell", new JsonPrimitive("1"));
        JsonObject end = new JsonObject();
        end.add("row", new JsonPrimitive("1"));
        end.add("cell", new JsonPrimitive("0"));
        actionData.add("start", start);
        actionData.add("end", end);

        when(request.queryParams(eq("actionData"))).thenReturn(actionData.toString());
        when(request.queryParams(eq("gameID"))).thenReturn("0");

        when(center.getGame(0)).thenReturn(game);

        Message message = gson.fromJson((String) CuT.handle(request, response), Message.class);
        assertEquals(Message.Type.INFO, message.getType());
        assertTrue(message.isSuccessful());
        assertEquals("Move backed up successfully", message.getText());
    }





}
