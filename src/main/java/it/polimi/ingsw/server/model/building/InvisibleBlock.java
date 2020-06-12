package it.polimi.ingsw.server.model.building;

/**
 * This class represents the invisible level of the building
 */
public class InvisibleBlock implements Block {

    /**
     * This attribute is the name of the block
     */
    private final String blockName;

    public InvisibleBlock () {
        this.blockName = "I'm Invisible";
    }

    /**
     *@return the name of the block
     */
    public String getBlockName () {
        return blockName;
    }

    /**
     * @return this method print out the name of the block you have selected
     */
    @Override
    public String toString () {
        return "InvisibleBlock{" +
                "blockName='" + blockName + '\'' +
                '}';
    }

    /**
     * @return the int of the identifier of the block
     */
    @Override
    public int getBlockIdentifier() {
        return -1;
    }
}
