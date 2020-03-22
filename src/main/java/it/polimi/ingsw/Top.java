package it.polimi.ingsw;

public class Top implements Block {
    private int blockIdentifier;
    private String blockName;

    public Top() {
        this.blockIdentifier = 3;
        this.blockName="Top";
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
    public void print(){
        System.out.println("selected:"+this.getBlockName());
    }
}
