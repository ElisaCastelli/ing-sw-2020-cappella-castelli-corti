package it.polimi.ingsw.model.building;

public class BuildingTop extends Building {
    /**
     * @return an instance of a new Block which type is a Top
     */
    public Block getBlock() {
        return new Top();
    }
}