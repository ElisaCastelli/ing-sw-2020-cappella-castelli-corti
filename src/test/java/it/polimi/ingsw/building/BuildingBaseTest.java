package it.polimi.ingsw.building;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingBaseTest {
    Base base=new Base();
    BuildingBase building=new BuildingBase();
    @Test
    void getBlock() {
        assertEquals(base.toString(),building.getBlock().toString());
    }
}