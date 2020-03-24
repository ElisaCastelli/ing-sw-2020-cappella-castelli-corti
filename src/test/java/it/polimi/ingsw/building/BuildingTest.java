package it.polimi.ingsw.building;

import junit.framework.TestCase;

public class BuildingTest extends TestCase {
    Building buildingTest=new Building();

    public void testBuild() {
        buildingTest.build(1);
        assertEquals("Base{blockName='Base'}",buildingTest.getBlocksNameInArray(0));
        buildingTest.build(2);
        assertEquals("Middle{blockName='Middle'}",buildingTest.getBlocksNameInArray(1));
        buildingTest.build(3);
        assertEquals("Top{blockName='Top'}",buildingTest.getBlocksNameInArray(2));
        buildingTest.build(4);
        assertEquals("Dome{blockName='Dome'}",buildingTest.getBlocksNameInArray(3));
    }

    public void testClear() {
        buildingTest.clear();
        assertTrue(buildingTest.getArrayOfBlocks().size() == 0);
    }
}