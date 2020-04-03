package it.polimi.ingsw.building;

public class BuildingInvisibleBlk extends Building {
    /**
     * @return an instance of a new Block which type is a InvisibleBlock
     */
    public Block getBlock() {
        return new InvisibleBlock();
    }
}
