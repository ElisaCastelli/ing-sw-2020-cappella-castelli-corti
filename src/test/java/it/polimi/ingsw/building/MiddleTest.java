package it.polimi.ingsw.building;

import junit.framework.TestCase;

public class MiddleTest extends TestCase {
    Middle middleTest=new Middle();

    public void testGetBlockIdentifier() {
        assertEquals(2,middleTest.getBlockIdentifier());
    }

    public void testGetBlockName() {
        assertEquals("Middle",middleTest.getBlockName());
    }
}