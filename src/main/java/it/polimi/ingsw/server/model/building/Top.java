package it.polimi.ingsw.server.model.building;

/**
 * This class represents the third level of the building
 */
public class Top implements Block {

    /**
     * This attribute identifies the block
     */
    private final int blockIdentifier;

    /**
     * This attribute is the name of the block
     */
    private final String blockName;

    /**
     * The constructor of the class
     */
    public Top() {
        this.blockIdentifier = 3;
        this.blockName = "Top";
    }

    /**
     * This method return the int of the identifier of the block
     *
     * @return the int of the identifier of the block
     */
    public int getBlockIdentifier() {
        return blockIdentifier;
    }

    /**
     * This method return the name of the block
     *
     * @return the name of the block
     */
    public String getBlockName() {
        return blockName;
    }

    /**
     * To sting method
     *
     * @return this method print out the name of the block you have selected
     */
    @Override
    public String toString() {
        return "Top";
    }
}
