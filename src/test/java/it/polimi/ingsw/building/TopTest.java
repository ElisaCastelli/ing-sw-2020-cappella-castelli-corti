package it.polimi.ingsw.building;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TopTest {
    final Top topTest=new Top();

    @Test
    void getBlockIdentifier() {
        assertEquals(3,topTest.getBlockIdentifier());
    }

    @Test
    void getBlockName() {
        assertEquals("Top",topTest.getBlockName());
    }
}