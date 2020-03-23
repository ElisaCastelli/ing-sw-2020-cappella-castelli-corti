package it.polimi.ingsw.building;

import it.polimi.ingsw.building.Base;
import it.polimi.ingsw.building.Block;
import it.polimi.ingsw.building.Building;

public class BuildingBase extends Building {

    public Block getBlock() {
        return new Base();
    }
}
