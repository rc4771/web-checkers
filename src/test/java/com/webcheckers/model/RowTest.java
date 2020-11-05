package com.webcheckers.model;
import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.pieces.RedSinglePiece;
import com.webcheckers.model.spaces.BlackSpace;
import com.webcheckers.model.spaces.WhiteSpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

@Tag ("Model-tier")
public class RowTest {
    private Row CuT;
    private int index;

    @BeforeEach
    public void setup(){
        ArrayList<Space> spaces = new ArrayList<Space>();
        spaces.add(new WhiteSpace(0));
        CuT = new Row(index, spaces);
    }

    /**
     * Tests if getSpaces correctly gets spaces
     */
    @Test
    public void testGetSpaces(){
    assertNotNull(CuT.getSpaces());
    }

    /**
     * Test to make sure all calls to next work correctly
     */
    @Test
    public void testGetIndex(){
    assertEquals(CuT.getIndex(), index);
    }

    /**
     * Tests to make sure iterator isnt null
     */
    @Test
    public void testIterator(){
        assertNotNull(CuT.iterator());
    }

    /**
     * Test to make sure copy correctly copies spaces
     */
    @Test
    public void testCopy() {
        ArrayList<Space> spaces = new ArrayList<>();
        Row row = new Row(0, spaces);
        assertTrue(row.getSpaces().isEmpty());
        assertEquals(0, row.getIndex());

        row = new Row(1, spaces);
        assertEquals(1, row.getIndex());

        Space space = new BlackSpace(0);
        spaces.add(space);
        row = new Row(1, spaces);
        assertEquals(space, row.getSpaces().get(0));
    }
}
