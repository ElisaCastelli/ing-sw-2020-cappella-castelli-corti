package it.polimi.ingsw.server.model.building;

/**
 * This class builds an invisible block in a building using the factory method
 */

public class BuildingInvisibleBlk extends Building {
    /**
     * @return an instance of a new Block which type is a InvisibleBlock
     */
    public Block getBlock() {
        return new InvisibleBlock();
    }
}
