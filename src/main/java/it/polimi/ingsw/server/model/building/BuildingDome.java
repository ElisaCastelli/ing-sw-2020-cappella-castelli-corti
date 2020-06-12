package it.polimi.ingsw.server.model.building;

/**
 * This class builds the dome in a building using the factory method
 */
public class BuildingDome extends Building {
    /**
     * @return an instance of a new Block which type is a Dome
     */
    public Block getBlock() {
        return new Dome();
    }
}
