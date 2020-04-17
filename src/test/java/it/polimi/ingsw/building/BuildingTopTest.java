package it.polimi.ingsw.building;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTopTest {
    final Top top=new Top();
    final BuildingTop buildingTop=new BuildingTop();
    @Test
    void getBlock() {
        assertEquals(top.toString(),buildingTop.getBlock().toString());
    }
}