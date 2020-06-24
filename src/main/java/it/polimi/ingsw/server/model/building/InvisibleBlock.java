package it.polimi.ingsw.server.model.building;

/**
 * This class represents the invisible level of the building
 */
public class InvisibleBlock implements Block {

    /**
     * This attribute is the name of the block
     */
    private final String blockName;

    /**
     * The constructor of the class
     */
    public InvisibleBlock() {
        this.blockName = "I'm Invisible";
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
     * This method return the int of the identifier of the block
     *
     * @return the int of the identifier of the block
     */
    @Override
    public int getBlockIdentifier() {
        return -1;
    }

    /**
     * To sting method
     *
     * @return this method print out the name of the block you have selected
     */
    @Override
    public String toString() {
        return getBlockName();
    }
}
