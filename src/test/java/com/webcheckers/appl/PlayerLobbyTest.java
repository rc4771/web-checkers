package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Tag("Application-tier")
public class PlayerLobbyTest {

    @Test
    public void testSignInPlayer_validUsername() {
        PlayerLobby pl = createPlayerLobby();

        String[] usernames = {"DavidTheF", "RIT", "can spaces", "NUMBERS123", "CoMbO 123"};
        for (String username : usernames) {
            assertEquals(PlayerLobby.SignInResult.OK, pl.signInPlayer(username));
        }
    }

    @Test
    public void testSignInPlayer_nonAlphaNumeric() {
        PlayerLobby pl = createPlayerLobby();

        String[] usernames = {"s@#$%^&(=;'.", "less /*-+", "simple error!", "question?"};
        for (String username : usernames) {
            assertEquals(PlayerLobby.SignInResult.INVALID_USERNAME, pl.signInPlayer(username));
        }
    }

    @Test
    public void testSignInPlayer_takenUsername() {
        PlayerLobby pl = createPlayerLobby();

        assertEquals(PlayerLobby.SignInResult.OK, pl.signInPlayer("Test123"));
        assertEquals(PlayerLobby.SignInResult.USERNAME_TAKEN, pl.signInPlayer("Test123"));
    }

    @Test
    public void testSignInPlayer_emptyNullUsername() {
        PlayerLobby pl = createPlayerLobby();
        assertEquals(PlayerLobby.SignInResult.INVALID_USERNAME, pl.signInPlayer(null));
        assertEquals(PlayerLobby.SignInResult.INVALID_USERNAME, pl.signInPlayer(""));
        assertEquals(PlayerLobby.SignInResult.INVALID_USERNAME, pl.signInPlayer("     ")); // spaces
        assertEquals(PlayerLobby.SignInResult.INVALID_USERNAME, pl.signInPlayer("\t")); // tab
    }

    @Test
    public void testSignOutPlayer() {
        PlayerLobby pl = createPlayerLobby();
        assertEquals(PlayerLobby.SignInResult.OK, pl.signInPlayer("Test123"));
        assertEquals(PlayerLobby.SignOutResult.OK, pl.signOutPlayer(pl.getPlayer("Test123")));
    }

    @Test
    public void testSignOutPlayer_nullPlayer() {
        PlayerLobby pl = createPlayerLobby();
        assertEquals(PlayerLobby.SignOutResult.NULL_PLAYER, pl.signOutPlayer(null));
    }

    @Test
    public void testSignOutPlayer_playerNotSignedIn() {
        PlayerLobby pl = createPlayerLobby();
        Player p = new Player("Test123");
        assertEquals(PlayerLobby.SignOutResult.PLAYER_NOT_LOGGED_IN, pl.signOutPlayer(p));
    }

    @Test
    public void testGetPlayerCount() {
        PlayerLobby pl = createPlayerLobby();

        assertEquals(0, pl.getPlayerCount());

        String[] usernames = {"DavidTheF", "RIT", "can spaces", "NUMBERS123", "CoMbO 123"};
        for (String username : usernames) {
            pl.signInPlayer(username);
        }

        assertEquals(usernames.length, pl.getPlayerCount());
    }

    @Test
    public void testGetPlayerCount_invalidSignIns() {
        PlayerLobby pl = createPlayerLobby();

        assertEquals(0, pl.getPlayerCount());

        String[] invalidUsernames = {"symbols !@#$%^&*()-=[];'../", "slightly less symbols /*-+", "simple error!", "question?"};
        for (String username : invalidUsernames) {
            pl.signInPlayer(username);
        }

        assertEquals(0, pl.getPlayerCount());

        String[] validUsernames = {"DavidTheF", "RIT", "can spaces", "NUMBERS123", "CoMbO 123"};
        for (String username : validUsernames) {
            pl.signInPlayer(username);
        }

        assertEquals(validUsernames.length, pl.getPlayerCount());
    }

    @Test
    public void testGetPlayerUsernames() {
        PlayerLobby pl = createPlayerLobby();

        String[] usernames = {"DavidTheF", "RIT", "can spaces", "NUMBERS123", "CoMbO 123"};
        for (String username : usernames) {
            pl.signInPlayer(username);
        }

        List<String> recv = pl.getPlayerUsernames();

        // Tests that the lengths are the same AND that each username is found exactly
        assertEquals(recv.size(), usernames.length);
        for (String s : usernames) {
            recv.remove(s);
        }
        assertEquals(0, recv.size());
    }

    @Test
    public void testGetPlayerUsernames_excl() { // Test getting all usernames EXCEPT one
        PlayerLobby pl = createPlayerLobby();

        ArrayList<String> usernames = new ArrayList<>(Arrays.asList("DavidTheF", "RIT", "can spaces", "NUMBERS123", "CoMbO 123"));
        for (String username : usernames) {
            pl.signInPlayer(username);
        }

        String excl = usernames.get(0);
        List<String> recv = pl.getPlayerUsernames(excl);

        assertEquals(recv.size(), usernames.size() - 1);
        for (String s : recv) {
            usernames.remove(s);
        }
        assertEquals(1, usernames.size());
        assertEquals(excl, usernames.get(0));
    }

    @Test
    public void testGetPlayerUsernames_nonSignedIn() {
        PlayerLobby pl = createPlayerLobby();
        assertEquals(0, pl.getPlayerUsernames().size());
    }

    @Test
    public void testGetPlayer() {
        PlayerLobby pl = createPlayerLobby();
        String[] usernames = {"DavidTheF", "RIT", "can spaces", "NUMBERS123", "CoMbO 123"};
        for (String username : usernames) {
            pl.signInPlayer(username);
        }

        for (String username : usernames) {
            assertNotNull(pl.getPlayer(username));
        }
    }

    @Test
    public void testGetPlayer_noneSignedIn() {
        PlayerLobby pl = createPlayerLobby();
        assertNull(pl.getPlayer("swen261"));
    }

    @Test
    public void testGetPlayer_nullUsername() {
        PlayerLobby pl = createPlayerLobby();
        assertNull(pl.getPlayer(null));
    }

    private PlayerLobby createPlayerLobby() {
        return new PlayerLobby();
    }
}
