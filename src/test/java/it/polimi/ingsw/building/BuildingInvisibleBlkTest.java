package it.polimi.ingsw.building;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingInvisibleBlkTest {
    InvisibleBlock invisibleBlock=new InvisibleBlock();
    BuildingInvisibleBlk buildingInvisibleBlk=new BuildingInvisibleBlk();
    @Test
    void getBlock() {
        assertEquals(invisibleBlock.toString(),buildingInvisibleBlk.getBlock().toString());
    }
}