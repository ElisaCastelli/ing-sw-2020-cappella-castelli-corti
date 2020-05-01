package it.polimi.ingsw.server.model.building;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingInvisibleBlkTest {
    final InvisibleBlock invisibleBlock=new InvisibleBlock();
    final BuildingInvisibleBlk buildingInvisibleBlk=new BuildingInvisibleBlk();
    @Test
    void getBlock() {
        assertEquals(invisibleBlock.toString(),buildingInvisibleBlk.getBlock().toString());
    }
}