package it.polimi.ingsw.model.building;

public class BuildingMiddle extends Building {
    /**
     * @return an instance of a new Block which type is a Middle
     */
    public Block getBlock() {
        return new Middle();
    }
}
