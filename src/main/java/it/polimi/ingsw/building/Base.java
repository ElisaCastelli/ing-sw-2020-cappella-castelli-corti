package it.polimi.ingsw.building;

public class Base implements Block {
    private int blockIdentifier;
    private String blockName;

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
     * set the identifier as an int
     * for now is not used
     * TO DO remember that is not verified the identifier
     * @param blockIdentifier is the identifier for building correctly
     */
    public void setBlockIdentifier(int blockIdentifier) {
        this.blockIdentifier = blockIdentifier;
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
