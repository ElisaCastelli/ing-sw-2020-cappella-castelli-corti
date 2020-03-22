package it.polimi.ingsw;

public class Building {

    public Block getBlock(int blockIdentifier){
        Block block =null;
        if(blockIdentifier == 1){
            BuildingBase baseBuilder=new BuildingBase();
            block=baseBuilder.getBlock();
        }else if(blockIdentifier==2){
            BuildingMiddle middleBuilder=new BuildingMiddle();
            block=middleBuilder.getBlock();
        }else if(blockIdentifier ==3){
            BuildingTop topBuilder=new BuildingTop();
            block=topBuilder.getBlock();
        }else{
            BuildingDome domeBuilder=new BuildingDome();
            block=domeBuilder.getBlock();
        }
       return block;
    }




}
