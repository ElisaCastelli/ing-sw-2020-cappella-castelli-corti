package it.polimi.ingsw.model.building;

public class Middle implements Block {
    private final int blockIdentifier;
    private final String blockName;

    public Middle() {
        this.blockIdentifier = 2;
        this.blockName="Middle";
    }
    /**
     * @return the int of the identifier of the block
     */
    public int getBlockIdentifier() {
        return blockIdentifier;
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
        return "Middle{" +
                "blockName='" + blockName + '\'' +
                '}';
    }
}
