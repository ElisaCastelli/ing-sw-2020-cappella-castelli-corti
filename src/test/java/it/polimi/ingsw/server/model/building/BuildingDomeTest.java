package it.polimi.ingsw.server.model.building;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingDomeTest {
    final Dome dome=new Dome();
    final BuildingDome buildingDome=new BuildingDome();
    @Test
    void getBlock() {
        assertEquals(dome.toString(),buildingDome.getBlock().toString());
    }
}