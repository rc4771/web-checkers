package com.webcheckers.model;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testGetSpaces(){
    assertNotNull(CuT.getSpaces());
    }
    @Test
    public void testGetIndex(){
    assertEquals(CuT.getIndex(), index);
    }
    @Test
    public void testIterator(){
        assertNotNull(CuT.iterator());
    }
}
