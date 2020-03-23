package it.polimi.ingsw.building;

public class Base implements Block {
    private int blockIdentifier;
    private String blockName;

    public Base() {
        this.blockIdentifier = 1;
        this.blockName="Base";
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
    /*public void print(){
        System.out.println("selected:"+this.getBlockName());
    }*/

    @Override
    public String toString() {
        return "Base{" +
                "blockName='" + blockName + '\'' +
                '}';
    }
}
