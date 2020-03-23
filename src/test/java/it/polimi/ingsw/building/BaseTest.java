package it.polimi.ingsw.building;

import junit.framework.TestCase;

public class BaseTest extends TestCase {
    Base baseTest=new Base();


    public void testGetBlockIdentifier() {
        assertEquals(1,baseTest.getBlockIdentifier());
    }

    public void testGetBlockName() {
        assertEquals("Base",baseTest.getBlockName());
    }
}