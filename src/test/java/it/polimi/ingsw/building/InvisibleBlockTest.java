package it.polimi.ingsw.building;

import junit.framework.TestCase;

public class InvisibleBlockTest extends TestCase {
    InvisibleBlock invisibleBlock=new InvisibleBlock();

    public void testGetBlockName() {
        assertEquals("I'm invisible",invisibleBlock.getBlockName());
    }
}