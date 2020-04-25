package it.polimi.ingsw.model.building;

public class BuildingBase extends Building {
    /**
     * @return an instance of a new Block which type is a Base
     */
    public Block getBlock() {
        return new Base();
    }
}
