package com.webcheckers.util;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Util-tier")
public class MessageTest {

    @Test
    /**\
     * Test to make sure error messages are identified
     */
    void errorMsg(){
        final Message CuT = Message.error("This is an error.");
        assertEquals(CuT.getType(), Message.Type.ERROR);

        assertNotEquals(CuT.getType(), Message.Type.INFO);
    }

    @Test
    /**
     * Test to make sure info messages are identified
     */
    void infoMsg(){
        final Message CuT = Message.info("This is an info message.");
        assertEquals(CuT.getType(), Message.Type.INFO);

        assertNotEquals(CuT.getType(), Message.Type.ERROR);
    }

    @Test
    /**
     * Test to see if isSuccessful works
     */
    void isSuccessfulTest(){
        final Message CuT = Message.info("Info message.");

        assertTrue(CuT.isSuccessful());

        final Message CuT_Error = Message.error("Error message.");

        assertFalse(CuT_Error.isSuccessful());
    }
}
