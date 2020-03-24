package it.polimi.ingsw.building;

import junit.framework.TestCase;

public class TopTest extends TestCase {
    Top topTest=new Top();

    public void testGetBlockIdentifier() {
        assertEquals(3,topTest.getBlockIdentifier());
    }

    public void testGetBlockName() {
        assertEquals("Top",topTest.getBlockIdentifier());
    }
}