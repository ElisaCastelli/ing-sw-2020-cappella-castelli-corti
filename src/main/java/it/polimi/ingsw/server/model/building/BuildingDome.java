package it.polimi.ingsw.server.model.building;

public class BuildingDome extends Building {
    /**
     * @return an instance of a new Block which type is a Dome
     */
    public Block getBlock() {
        return new Dome();
    }
}
