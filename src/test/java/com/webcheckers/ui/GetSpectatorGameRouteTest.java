package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import spark.*;

import static com.webcheckers.ui.GetGameRoute.GAME_ID_ATTR;
import static com.webcheckers.ui.GetHomeRoute.ERROR_MESSAGE_ATTR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
@Tag("UI-tier")
public class GetSpectatorGameRouteTest {
    private GetSpectatorGameRoute CuT;
    private TemplateEngine templateEngine;
    private GameCenter gameCenter;
    private Gson gson;
    private Session session;
    private Request request;
    private Response response;
    private Game game;
    private Player redPlayer;
    private Player whitePlayer;
    private Player sessionPlayer;

    @BeforeEach
    void setup(){
        templateEngine = mock(TemplateEngine.class);
        gameCenter= mock(GameCenter.class);
        Gson gson = new Gson();
        CuT = new GetSpectatorGameRoute(templateEngine, gameCenter, gson);

        session = mock(Session.class);
        request = mock(Request.class);
        response = mock(Response.class);
        game = mock(Game.class);

        when(request.session()).thenReturn(session);

        when(request.queryParams(GAME_ID_ATTR)).thenReturn("0");

        sessionPlayer = mock(Player.class);

        when(session.attribute(PostSignInRoute.PLAYER_SESSION_KEY)).thenReturn(sessionPlayer);
        when(sessionPlayer.getName()).thenReturn("Rafeed");
        Board board = mock(Board.class);
        when(game.getBoard()).thenReturn(board);
        when(game.getCurrentTurn()).thenReturn(Piece.PieceColor.RED);
        when(board.transposeForColor(Piece.PieceColor.RED)).thenReturn(board);

        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);

        when(game.getRedPlayer()).thenReturn(redPlayer);
        when(game.getWhitePlayer()).thenReturn(whitePlayer);



    }

    @Test
    void testConstructor_gsonNull(){
        assertThrows(NullPointerException.class, () -> new GetSpectatorGameRoute(templateEngine, gameCenter, null));
    }

    @Test
    void testConstructor_gameCenterNull(){
        assertThrows(NullPointerException.class, () -> new GetSpectatorGameRoute(templateEngine, null, gson));
    }

    @Test
    void testConstructor_templateEngineNull(){
        assertThrows(NullPointerException.class, () -> new GetSpectatorGameRoute(null, gameCenter, gson));
    }

    @Test
    void handle_RedTurn(){
        when(redPlayer.getName()).thenReturn("rafe");
        when(whitePlayer.getName()).thenReturn("raf");
        Message message = gson.fromJson((String) CuT.handle(request, response), Message.class);
        assertEquals("It is now rafe 's turn.", message.toString());
    }
}
