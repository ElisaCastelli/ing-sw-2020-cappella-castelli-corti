package it.polimi.ingsw.building;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingMiddleTest {
    Middle middle=new Middle();
    BuildingMiddle buildingMiddle=new BuildingMiddle();
    @Test
    void getBlock() {
        assertEquals(middle.toString(),buildingMiddle.getBlock().toString());
    }
}