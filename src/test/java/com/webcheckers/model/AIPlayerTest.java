package com.webcheckers.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Tag("Model-tier")
public class AIPlayerTest {

    @Test
    void testConstructor(){
    AIPlayer CuT = new AIPlayer(3);
    assertEquals(CuT.getName(), CuT.ROBOT_EMOJI + CuT.AI_NAMES[3]);
    }

    @Test
    void testGetAIPlayerIndex(){
    AIPlayer CuT = new AIPlayer(1);
    assertEquals(CuT.getAIPlayerIndex(), 1);
    }
    




}
