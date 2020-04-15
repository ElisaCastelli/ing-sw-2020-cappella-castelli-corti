package it.polimi.ingsw.building;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingDomeTest {
    Dome dome=new Dome();
    BuildingDome buildingDome=new BuildingDome();
    @Test
    void getBlock() {
        assertEquals(dome.toString(),buildingDome.getBlock().toString());
    }
}