package it.polimi.ingsw;

public class Dome implements Block {
    private int blockIdentifier;
    private String blockName;

    public Dome() {
        this.blockIdentifier =4;
        this.blockName="Dome";
    }

    public int getBlockIdentifier() {
        return blockIdentifier;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockIdentifier(int blockIdentifier) {
        this.blockIdentifier = blockIdentifier;
    }
    /*@
      @this method print out the name of the block you have selected
      @*/
    public void print(){
        System.out.println("selected:"+this.getBlockName());
    }
}
