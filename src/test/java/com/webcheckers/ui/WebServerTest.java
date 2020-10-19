package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.Test;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WebServerTest {

	@Test
	public void constructor_test() {
		TemplateEngine engine = mock(TemplateEngine.class);
		Gson gson = new Gson();
		PlayerLobby lobby = mock(PlayerLobby.class);
		GameCenter center = mock(GameCenter.class);

		new WebServer(engine, gson, lobby, center); // make sure no errors occur
	}

	@Test
	public void nullEngine_test() {
		TemplateEngine engine = null;
		Gson gson = new Gson();
		PlayerLobby lobby = mock(PlayerLobby.class);
		GameCenter center = mock(GameCenter.class);

		assertThrows(Exception.class, () -> new WebServer(engine, gson, lobby, center));
	}

	@Test
	public void nullLobby_test() {
		TemplateEngine engine = mock(TemplateEngine.class);
		Gson gson = new Gson();
		PlayerLobby lobby = null;
		GameCenter center = mock(GameCenter.class);

		assertThrows(Exception.class, () -> new WebServer(engine, gson, lobby, center));
	}

	@Test
	public void nullCenter_test() {
		TemplateEngine engine = mock(TemplateEngine.class);
		Gson gson = new Gson();
		PlayerLobby lobby = mock(PlayerLobby.class);
		GameCenter center = null;

		assertThrows(Exception.class, () -> new WebServer(engine, gson, lobby, center));
	}

	@Test
	public void intialize_test() {
		TemplateEngine engine = mock(TemplateEngine.class);
		Gson gson = new Gson();
		PlayerLobby lobby = mock(PlayerLobby.class);
		GameCenter center = mock(GameCenter.class);

		WebServer server = new WebServer(engine, gson, lobby, center);
		server.initialize(); // make sure no errors occur, not much can be tested here
	}
}