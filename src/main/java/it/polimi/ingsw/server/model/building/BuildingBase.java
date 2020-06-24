package it.polimi.ingsw.server.model.building;

/**
 * This class builds the base in a building using the factory method
 */

public class BuildingBase extends Building {
    /**
     * @return an instance of a new Block which type is a Base
     */
    public Block getBlock() {
        return new Base();
    }
}
