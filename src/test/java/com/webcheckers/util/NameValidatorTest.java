package com.webcheckers.util;

import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("Util-tier")
public class NameValidatorTest {

    /**
     * Tests that valid usernames will be accepted
     */
    @Test
    public void testSignInPlayer_validUsername() {
        String[] usernames = {"DavidTheF", "RIT", "can spaces", "NUMBERS123", "CoMbO 123"};
        for (String username : usernames) {
            assertTrue(NameValidator.isValidUsername(username));
        }
    }

    /**
     * Tests to make sure invalid usernames are rejected
     */
    @Test
    public void testSignInPlayer_nonAlphaNumeric() {
        String[] usernames = {"s@#$%^&(=;'.", "less /*-+", "simple error!", "question?"};
        for (String username : usernames) {
            assertFalse(NameValidator.isValidUsername(username));
        }
    }
}
