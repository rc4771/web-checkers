package com.webcheckers.util;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Tag("Util-tier")
public class MessageTest {

    private final static String ERROR_MSG = "Error occured.";
    private final static String INFO_MSG = "Valid execution";

    /**\
     * Test to make sure error messages are identified
     */
    @Test
    void errorMsg(){
        final Message CuT = Message.error(ERROR_MSG);
        assertEquals(CuT.getType(), Message.Type.ERROR);

        assertNotEquals(CuT.getType(), Message.Type.INFO);
    }

    /**
     * Test to make sure info messages are identified
     */
    @Test
    void infoMsg(){
        final Message CuT = Message.info(INFO_MSG);
        assertEquals(CuT.getType(), Message.Type.INFO);

        assertNotEquals(CuT.getType(), Message.Type.ERROR);
    }

    /**
     * Test to see if isSuccessful method works
     */
    @Test
    void isSuccessfulTest(){
        final Message CuT = Message.info(INFO_MSG);

        assertTrue(CuT.isSuccessful());

        final Message CuT_Error = Message.error(ERROR_MSG);

        assertFalse(CuT_Error.isSuccessful());
    }

    /**
     * Test to see if getText method works
     */
    @Test
    void getTextTest(){
        final Message CuT = Message.info(INFO_MSG);

        assertEquals(INFO_MSG, CuT.getText());
        assertNotEquals(CuT.getText(), ERROR_MSG);
    }

}
