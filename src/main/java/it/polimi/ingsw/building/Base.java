package it.polimi.ingsw.building;

public class Base implements Block {
    private final int blockIdentifier;
    private final String blockName;

    public Base() {
        this.blockIdentifier = 1;
        this.blockName="Base";
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
        return "Base{" +
                "blockName='" + blockName + '\'' +
                '}';
    }
}
