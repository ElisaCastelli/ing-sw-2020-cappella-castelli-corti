package it.polimi.ingsw.building;

public class InvisibleBlock implements Block {
    private String blockName;

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
}
