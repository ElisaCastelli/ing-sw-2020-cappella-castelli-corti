package it.polimi.ingsw.building;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {
    final Building buildingTest=new Building();
    @Test
    void build() {
        buildingTest.build();
        assertEquals("Base{blockName='Base'}",buildingTest.getBlocksNameInArray(0));
        buildingTest.build();
        assertEquals("Middle{blockName='Middle'}",buildingTest.getBlocksNameInArray(1));
        buildingTest.build();
        assertEquals("Top{blockName='Top'}",buildingTest.getBlocksNameInArray(2));
        buildingTest.build();
        assertEquals("Dome{blockName='Dome'}",buildingTest.getBlocksNameInArray(3));
        buildingTest.clear();
    }

    @Test
    void testBuild() {
        buildingTest.build(4);
        assertEquals(4, buildingTest.getArrayOfBlocks().size());
        assertEquals("Dome{blockName='Dome'}",buildingTest.getBlocksNameInArray(0));
        assertEquals("InvisibleBlock{blockName='I'm Invisible'}",buildingTest.getBlocksNameInArray(1));
        assertEquals("InvisibleBlock{blockName='I'm Invisible'}",buildingTest.getBlocksNameInArray(2));
        assertEquals("InvisibleBlock{blockName='I'm Invisible'}",buildingTest.getBlocksNameInArray(3));
        buildingTest.clear();
        buildingTest.build();
        assertEquals("Base{blockName='Base'}",buildingTest.getBlocksNameInArray(0));
        buildingTest.build(4);
        assertEquals(4, buildingTest.getArrayOfBlocks().size());
        assertEquals("Dome{blockName='Dome'}",buildingTest.getBlocksNameInArray(1));
        assertEquals("InvisibleBlock{blockName='I'm Invisible'}",buildingTest.getBlocksNameInArray(2));
        assertEquals("InvisibleBlock{blockName='I'm Invisible'}",buildingTest.getBlocksNameInArray(3));
    }

    @Test
    void clear() {
        buildingTest.clear();
        assertEquals(0, buildingTest.getArrayOfBlocks().size());
    }
}