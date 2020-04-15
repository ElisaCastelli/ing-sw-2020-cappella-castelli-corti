package it.polimi.ingsw.building;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseTest {
    final Base baseTest=new Base();
    @Test
    void getBlockIdentifier() {
        assertEquals(1,baseTest.getBlockIdentifier());
    }

    @Test
    void getBlockName() {
        assertEquals("Base",baseTest.getBlockName());
    }
}