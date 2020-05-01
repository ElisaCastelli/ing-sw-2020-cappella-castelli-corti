package it.polimi.ingsw.server.model.building;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MiddleTest {
    final Middle middleTest=new Middle();
    @Test
    void getBlockIdentifier() {
        assertEquals(2,middleTest.getBlockIdentifier());
    }

    @Test
    void getBlockName() {
        assertEquals("Middle",middleTest.getBlockName());
    }
}