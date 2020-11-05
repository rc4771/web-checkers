package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

@Tag("Application-tier")
public class GameCenterTest {

    @Test
    public void testNewGame_notNull() {
        GameCenter gc = createGameCenter();

        assertNotEquals(null, gc.newGame(createDummyPlayer(0), createDummyPlayer(1)));
    }

    @Test
    public void testNewGame_uniqueIDs() {
        GameCenter gc = createGameCenter();
        int playerIndex = 0;

        Game g1 = gc.newGame(createDummyPlayer(++playerIndex), createDummyPlayer(++playerIndex));
        Game g2 = gc.newGame(createDummyPlayer(++playerIndex), createDummyPlayer(++playerIndex));
        Game g3 = gc.newGame(createDummyPlayer(++playerIndex), createDummyPlayer(++playerIndex));
        Game g4 = gc.newGame(createDummyPlayer(++playerIndex), createDummyPlayer(++playerIndex));

        assertNotEquals(g1.getGameID(), g2.getGameID());
        assertNotEquals(g1.getGameID(), g3.getGameID());
        assertNotEquals(g1.getGameID(), g4.getGameID());

        assertNotEquals(g2.getGameID(), g3.getGameID());
        assertNotEquals(g2.getGameID(), g4.getGameID());

        assertNotEquals(g3.getGameID(), g4.getGameID());
    }

    @Test
    public void testNewGame_nullPlayer() {
        GameCenter gc = createGameCenter();
        int playerIndex = 0;

        assertNull(gc.newGame(createDummyPlayer(++playerIndex), null));
        assertNull(gc.newGame(null, createDummyPlayer(++playerIndex)));
        assertNull(gc.newGame(null, null));
    }

    @Test
    public void testNewGame_playerAlreadyInGame() {
        GameCenter gc = createGameCenter();
        int playerIndex = 0;

        Player p1 = createDummyPlayer(++playerIndex);
        Player p2 = createDummyPlayer(++playerIndex);
        Player p3 = createDummyPlayer(++playerIndex);

        assertNotNull(gc.newGame(p1, p2));
        assertNull(gc.newGame(p2, p3));
    }

    @Test
    public void testNewGame_validateID() {
        GameCenter gc = createGameCenter();
        int playerIndex = 0;

        Game g1 = gc.newGame(createDummyPlayer(++playerIndex), createDummyPlayer(++playerIndex));
        Game g2 = gc.newGame(createDummyPlayer(++playerIndex), createDummyPlayer(++playerIndex));
        Game g3 = gc.newGame(createDummyPlayer(++playerIndex), createDummyPlayer(++playerIndex));
        Game g4 = gc.newGame(createDummyPlayer(++playerIndex), createDummyPlayer(++playerIndex));

        assertTrue(g1.getGameID() >= 0);
        assertTrue(g2.getGameID() >= 0);
        assertTrue(g3.getGameID() >= 0);
        assertTrue(g4.getGameID() >= 0);
    }

    @Test
    public void testGetGame_validID() {
        GameCenter gc = createGameCenter();
        int playerIndex = 0;

        Game g1 = gc.newGame(createDummyPlayer(++playerIndex), createDummyPlayer(++playerIndex));
        Game g2 = gc.newGame(createDummyPlayer(++playerIndex), createDummyPlayer(++playerIndex));
        Game g3 = gc.newGame(createDummyPlayer(++playerIndex), createDummyPlayer(++playerIndex));
        Game g4 = gc.newGame(createDummyPlayer(++playerIndex), createDummyPlayer(++playerIndex));

        assertEquals(gc.getGame(g1.getGameID()), g1);
        assertEquals(gc.getGame(g2.getGameID()), g2);
        assertEquals(gc.getGame(g3.getGameID()), g3);
        assertEquals(gc.getGame(g4.getGameID()), g4);
    }

    @Test
    public void testGetGame_invalidID() {
        GameCenter gc = createGameCenter();
        int playerIndex = 0;

        Game g1 = gc.newGame(createDummyPlayer(++playerIndex), createDummyPlayer(++playerIndex));
        Game g2 = gc.newGame(createDummyPlayer(++playerIndex), createDummyPlayer(++playerIndex));

        int invalidID1 = 0;
        while (invalidID1 == g1.getGameID() || invalidID1 == g2.getGameID()) {
            invalidID1++;
        }

        int invalidID2 = 0;
        while (invalidID2 == g1.getGameID() || invalidID2 == g2.getGameID() || invalidID2 == invalidID1) {
            invalidID2++;
        }

        assertNull(gc.getGame(invalidID1));
        assertNull(gc.getGame(invalidID2));
        assertNull(gc.getGame(-1));
    }

    @Test
    public void testIsPlayerInGame() {
        GameCenter gc = createGameCenter();
        int playerIndex = 0;

        Player p1 = createDummyPlayer(++playerIndex);
        Player p2 = createDummyPlayer(++playerIndex);
        Player p3 = createDummyPlayer(++playerIndex);

        assertFalse(gc.isPlayerInGame(p1));
        assertFalse(gc.isPlayerInGame(p2));
        assertFalse(gc.isPlayerInGame(p3));

        gc.newGame(p1, p2);

        assertTrue(gc.isPlayerInGame(p1));
        assertTrue(gc.isPlayerInGame(p2));
        assertFalse(gc.isPlayerInGame(p3));
    }

    @Test
    public void testPlayerInGame_nullPlayer() {
        GameCenter gc = createGameCenter();
        int playerIndex = 0;

        Player p1 = createDummyPlayer(++playerIndex);
        Player p2 = createDummyPlayer(++playerIndex);

        assertFalse(gc.isPlayerInGame(null));
        gc.newGame(p1, p2);
        assertFalse(gc.isPlayerInGame(null));
    }

    @Test
    public void testGetGameFromPlayer() {
        GameCenter gc = createGameCenter();
        int playerIndex = 0;

        Player p1 = createDummyPlayer(++playerIndex);
        Player p2 = createDummyPlayer(++playerIndex);
        Player p3 = createDummyPlayer(++playerIndex);

        assertEquals(-1, gc.getGameFromPlayer(p1));
        assertEquals(-1, gc.getGameFromPlayer(p2));
        assertEquals(-1, gc.getGameFromPlayer(p3));

        Game g1 = gc.newGame(p1, p2);

        assertEquals(g1.getGameID(), gc.getGameFromPlayer(p1));
        assertEquals(g1.getGameID(), gc.getGameFromPlayer(p2));
        assertEquals(-1, gc.getGameFromPlayer(p3));
    }

    @Test
    public void testGetGameFromPlayer_nullPlayer() {
        GameCenter gc = createGameCenter();
        int playerIndex = 0;

        assertEquals(-1, gc.getGameFromPlayer(null));
        gc.newGame(createDummyPlayer(++playerIndex), createDummyPlayer(++playerIndex));
        assertEquals(-1, gc.getGameFromPlayer(null));
    }

    @Test
    public void testGetGameList() {
        GameCenter cut = new GameCenter();
        Player redPlayer = createDummyPlayer(0);
        Player whitePlayer = createDummyPlayer(1);
        cut.newGame(redPlayer, whitePlayer);

        List<Game> games = cut.getGameList();
        assertEquals(1, games.size());
        Game game = games.get(0);
        assertEquals(redPlayer, game.getRedPlayer());
        assertEquals(whitePlayer, game.getWhitePlayer());

    }

    @Test
    public void testGetGameList_inactive() {
        GameCenter cut = new GameCenter();
        Player redPlayer = createDummyPlayer(0);
        Player whitePlayer = createDummyPlayer(1);
        cut.newGame(redPlayer, whitePlayer);

        List<Game> games = cut.getGameList();
        assertEquals(1, games.size());
        Game game = games.get(0);
        game.setActive(false);

        games = cut.getGameList();
        assertTrue(games.isEmpty());
    }

    private Player createDummyPlayer(int id) {
        Player player = mock(Player.class);
        when(player.getName()).thenReturn(String.format("dummyPlayer#%d", id));
        return player;
    }

    private GameCenter createGameCenter() {
        return new GameCenter();
    }
}
