package it.polimi.ingsw.server.model.building;

public class Top implements Block {
    private final int blockIdentifier;
    private final String blockName;

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
