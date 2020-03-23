package it.polimi.ingsw.building;

import it.polimi.ingsw.building.Block;

public class Middle implements Block {
    private int blockIdentifier;
    private String blockName;

    public Middle() {
        this.blockIdentifier = 2;
        this.blockName="Middle";
    }

    public int getBlockIdentifier() {
        return blockIdentifier;
    }

    public void setBlockIdentifier(int blockIdentifier) {
        this.blockIdentifier = blockIdentifier;
    }

    public String getBlockName() {
        return blockName;
    }
    /*@
    @this method print out the name of the block you have selected
    @*/

    @Override
    public String toString() {
        return "Middle{" +
                "blockName='" + blockName + '\'' +
                '}';
    }
}
