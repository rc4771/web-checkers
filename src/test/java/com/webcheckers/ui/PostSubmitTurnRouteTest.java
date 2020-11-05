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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
/**
 * Unit testing for PostSubmitRouteTurn
 */
public class PostSubmitTurnRouteTest {
    /**
     * The Component under Test (CuT)
     */
    private PostSubmitTurnRoute CuT;

    //friendly objects
    private GameCenter center;
    private Game game;
    private PlayerLobby lobby;
    private Gson gson;


    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;

    @BeforeEach
    /**
     * Initializes each test with mocks
     */
    public void setup() {
        request = Mockito.mock(Request.class);
        session = Mockito.mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        gson = new Gson();

        lobby = mock(PlayerLobby.class);
        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(lobby);

        when(lobby.signInPlayer("rafeed")).thenReturn(PlayerLobby.SignInResult.OK);
        when(lobby.signInPlayer("rafe")).thenReturn(PlayerLobby.SignInResult.OK);

        lobby.signInPlayer("rafeed");
        lobby.signInPlayer("rafe");

        center = mock(GameCenter.class);
        game = mock(Game.class);

        CuT = new PostSubmitTurnRoute(center, gson);
    }

    @Test
    /**
     * Tests the handle method
     */
    void handleTest(){
        JsonObject actionData = new JsonObject();
        JsonObject start = new JsonObject();
        start.add("row", new JsonPrimitive("2"));
        start.add("cell", new JsonPrimitive("0"));
        JsonObject end = new JsonObject();
        end.add("row", new JsonPrimitive("3"));
        end.add("cell", new JsonPrimitive("2"));
        actionData.add("start", start);
        actionData.add("end", end);

        when(request.queryParams(eq("actionData"))).thenReturn(actionData.toString());
        when(request.queryParams(eq("gameID"))).thenReturn("0");
        when(center.getGame(0)).thenReturn(game);
        when(game.checkWin()).thenReturn(Game.WinType.NO_WIN);

        Message message = gson.fromJson((String) CuT.handle(request, response), Message.class);
        assertEquals(Message.Type.INFO, message.getType());
        assertTrue(message.isSuccessful());
        assertEquals("Move submitted successfully", message.getText());
    }

    @Test
    void testHandle_validRedWin(){
        JsonObject actionData = new JsonObject();
        JsonObject start = new JsonObject();
        start.add("row", new JsonPrimitive("2"));
        start.add("cell", new JsonPrimitive("0"));
        JsonObject end = new JsonObject();
        end.add("row", new JsonPrimitive("3"));
        end.add("cell", new JsonPrimitive("2"));
        actionData.add("start", start);
        actionData.add("end", end);

        when(request.queryParams(eq("actionData"))).thenReturn(actionData.toString());
        when(request.queryParams(eq("gameID"))).thenReturn("0");
        when(center.getGame(0)).thenReturn(game);
        when(game.checkWin()).thenReturn(Game.WinType.RED_WIN);

        Player redPlayer = mock(Player.class);
        Player whitePlayer = mock(Player.class);
        when(game.getRedPlayer()).thenReturn(redPlayer);
        when(game.getWhitePlayer()).thenReturn(whitePlayer);

        Message message = gson.fromJson((String) CuT.handle(request, response), Message.class);
        assertEquals(Message.Type.INFO, message.getType());
        assertTrue(message.isSuccessful());
        assertEquals("Move submitted successfully", message.getText());

    }

    @Test
    void testHandle_validWhiteWin(){
        JsonObject actionData = new JsonObject();
        JsonObject start = new JsonObject();
        start.add("row", new JsonPrimitive("2"));
        start.add("cell", new JsonPrimitive("0"));
        JsonObject end = new JsonObject();
        end.add("row", new JsonPrimitive("3"));
        end.add("cell", new JsonPrimitive("2"));
        actionData.add("start", start);
        actionData.add("end", end);

        when(request.queryParams(eq("actionData"))).thenReturn(actionData.toString());
        when(request.queryParams(eq("gameID"))).thenReturn("0");
        when(center.getGame(0)).thenReturn(game);
        when(game.checkWin()).thenReturn(Game.WinType.WHITE_WIN);

        Player redPlayer = mock(Player.class);
        Player whitePlayer = mock(Player.class);
        when(game.getRedPlayer()).thenReturn(redPlayer);
        when(game.getWhitePlayer()).thenReturn(whitePlayer);

        Message message = gson.fromJson((String) CuT.handle(request, response), Message.class);
        assertEquals(Message.Type.INFO, message.getType());
        assertTrue(message.isSuccessful());
        assertEquals("Move submitted successfully", message.getText());

    }





}
