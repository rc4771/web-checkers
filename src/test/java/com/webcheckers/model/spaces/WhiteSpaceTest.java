package com.webcheckers.model.spaces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class WhiteSpaceTest {

    @Test
    public void testWhiteSpace_isValid() {
        assertFalse(new WhiteSpace(0).isValid());
    }


}
