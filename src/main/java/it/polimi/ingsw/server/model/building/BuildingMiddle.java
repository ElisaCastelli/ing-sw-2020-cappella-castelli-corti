package it.polimi.ingsw.server.model.building;

/**
 * This class builds the middle level in a building using the factory method
 */

public class BuildingMiddle extends Building {
    /**
     * @return an instance of a new Block which type is a Middle
     */
    public Block getBlock() {
        return new Middle();
    }
}
