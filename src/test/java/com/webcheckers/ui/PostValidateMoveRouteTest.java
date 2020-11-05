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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
class PostValidateMoveRouteTest {

	/*** The component-under-test (CuT).*/
	private PostValidateMoveRoute CuT;

	// friendly objects
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

	/**
	 * Sets up all the objects the tests will use
	 */
	@BeforeEach
	public void setup() {
		request = Mockito.mock(Request.class);
		session = Mockito.mock(Session.class);
		when(request.session()).thenReturn(session);
		engine = mock(TemplateEngine.class);
		gson = new Gson();

		lobby = new PlayerLobby();
		when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(lobby);

		lobby.signInPlayer("bota");
		lobby.signInPlayer("alta");

		redPlayer = lobby.getPlayer("bota");
		whitePlayer = lobby.getPlayer("alta");

		center = new GameCenter();
		game = center.newGame(redPlayer, whitePlayer);

		CuT = new PostValidateMoveRoute(center, gson);
	}

	/**
	 * Tests validating a move without a piece at it
	 */
	@Test
	void pieceNull_test() {
		JsonObject actionData = new JsonObject();
		JsonObject start = new JsonObject();
		start.add("row", new JsonPrimitive("3"));
		start.add("cell", new JsonPrimitive("3"));
		JsonObject end = new JsonObject();
		end.add("row", new JsonPrimitive("3"));
		end.add("cell", new JsonPrimitive("5"));
		actionData.add("start", start);
		actionData.add("end", end);

		when(request.queryParams(eq("actionData"))).thenReturn(actionData.toString());
		when(request.queryParams(eq("gameID"))).thenReturn("0");

		Message message = gson.fromJson((String) CuT.handle(request, response), Message.class);
		assertEquals(Message.Type.ERROR, message.getType());
		assertFalse(message.isSuccessful());
		assertEquals("Please reset your move before trying to move again by using the Backup button", message.getText());
	}

	/**
	 * Tests a validate move but with a piece where its trying to move to
	 */
	@Test
	void endOccupied_test() {
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

		Message message = gson.fromJson((String) CuT.handle(request, response), Message.class);
		assertEquals(Message.Type.ERROR, message.getType());
		assertFalse(message.isSuccessful());
		assertEquals("There is a piece at the space you're jumping to", message.getText());
	}

	/**
	 * Tests move direction validation
	 */
	@Test
	void moveDirection_test() {
		JsonObject actionData = new JsonObject();
		JsonObject start = new JsonObject();
		start.add("row", new JsonPrimitive("3"));
		start.add("cell", new JsonPrimitive("2"));
		JsonObject end = new JsonObject();
		end.add("row", new JsonPrimitive("2"));
		end.add("cell", new JsonPrimitive("1"));
		actionData.add("start", start);
		actionData.add("end", end);

		when(request.queryParams(eq("actionData"))).thenReturn(actionData.toString());
		when(request.queryParams(eq("gameID"))).thenReturn("0");

		game.setPendingMove(2, 1, 3, 2);
		game.submitMove();

		Message message = gson.fromJson((String) CuT.handle(request, response), Message.class);
		assertEquals(Message.Type.ERROR, message.getType());
		assertFalse(message.isSuccessful());
		assertEquals("That type of piece cannot move in that direction", message.getText());
	}

	/**
	 * Tests a move that went more than a single space, shouldn't work
	 */
	@Test
	void tooFar_test() {
		JsonObject actionData = new JsonObject();
		JsonObject start = new JsonObject();
		start.add("row", new JsonPrimitive("2"));
		start.add("cell", new JsonPrimitive("1"));
		JsonObject end = new JsonObject();
		end.add("row", new JsonPrimitive("4"));
		end.add("cell", new JsonPrimitive("1"));
		actionData.add("start", start);
		actionData.add("end", end);

		when(request.queryParams(eq("actionData"))).thenReturn(actionData.toString());
		when(request.queryParams(eq("gameID"))).thenReturn("0");

		Message message = gson.fromJson((String) CuT.handle(request, response), Message.class);
		assertEquals(Message.Type.ERROR, message.getType());
		assertFalse(message.isSuccessful());
		assertEquals("Your cannot move more than 1 space without jumping", message.getText());
	}

	/**
	 * Test a valid path of the move validator
	 */
	@Test
	void ok_test() {
		JsonObject actionData = new JsonObject();
		JsonObject start = new JsonObject();
		start.add("row", new JsonPrimitive("2"));
		start.add("cell", new JsonPrimitive("1"));
		JsonObject end = new JsonObject();
		end.add("row", new JsonPrimitive("3"));
		end.add("cell", new JsonPrimitive("1"));
		actionData.add("start", start);
		actionData.add("end", end);

		when(request.queryParams(eq("actionData"))).thenReturn(actionData.toString());
		when(request.queryParams(eq("gameID"))).thenReturn("0");

		Message message = gson.fromJson((String) CuT.handle(request, response), Message.class);
		assertEquals(Message.Type.INFO, message.getType());
		assertTrue(message.isSuccessful());
		assertEquals("Move is valid, you can either Backup that move or Submit your turn", message.getText());
	}
}