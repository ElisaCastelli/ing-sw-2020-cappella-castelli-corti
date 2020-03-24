package it.polimi.ingsw.building;

import junit.framework.TestCase;

public class DomeTest extends TestCase {
    Dome domeTest =new Dome();

    public void testGetBlockIdentifier() {
        assertEquals(4,domeTest.getBlockIdentifier());
    }

    public void testGetBlockName() {
        assertEquals("Dome",domeTest.getBlockIdentifier());
    }
}