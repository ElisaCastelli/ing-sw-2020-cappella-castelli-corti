package it.polimi.ingsw.server.model.building;

public class Dome implements Block {
    private final int blockIdentifier;
    private final String blockName;

    public Dome() {
        this.blockIdentifier = 4;
        this.blockName = "Dome";
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
        return "Dome{" +
                "blockName='" + blockName + '\'' +
                '}';
    }
}
