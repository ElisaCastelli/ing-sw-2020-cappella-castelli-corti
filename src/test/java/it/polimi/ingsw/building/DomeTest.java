package it.polimi.ingsw.building;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomeTest {
    final Dome domeTest =new Dome();
    @Test
    void getBlockIdentifier() {
        assertEquals(4,domeTest.getBlockIdentifier());
    }

    @Test
    void getBlockName() {
        assertEquals("Dome",domeTest.getBlockName());
    }
}