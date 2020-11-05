package com.webcheckers.model.spaces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class WhiteSpaceTest {

    /**
     * Tests if white space is invalid
     */
    @Test
    public void testWhiteSpace_isValid() {
        assertFalse(new WhiteSpace(0).isValid());
    }


}
