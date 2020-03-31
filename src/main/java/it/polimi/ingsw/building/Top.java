package it.polimi.ingsw.building;

import it.polimi.ingsw.building.Block;

public class Top implements Block {
    private int blockIdentifier;
    private String blockName;

    public Top() {
        this.blockIdentifier = 3;
        this.blockName="Top";
    }
    /**
     * @return the int of the identifier of the block
     */
    public int getBlockIdentifier() {
        return blockIdentifier;
    }
    /**
     * set the identifier as an int
     * for now is not used
     * TO DO remember that is not verified the identifier
     * @param blockIdentifier is the identifier for building correctly
     */
    public void setBlockIdentifier(int blockIdentifier) {
        this.blockIdentifier = blockIdentifier;
    }
    /**
     *@return the name of the block
     */
    public String getBlockName() {
        return blockName;
    }
    /**
     * @return this method print out the name of the block you have selected
     */
    @Override
    public String toString() {
        return "Top{" +
                "blockName='" + blockName + '\'' +
                '}';
    }
}
