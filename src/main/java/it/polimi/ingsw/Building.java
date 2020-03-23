package it.polimi.ingsw;

public class Building {
    private Block[] arrayOfBlocks;

    public Building() {
        arrayOfBlocks=null;
    }

    public Block[] build(int blockIdentif){

        if(blockIdentif == 1){
            arrayOfBlocks[0]=getBlock(blockIdentif);
            arrayOfBlocks[0].print();

        }else if(blockIdentif==2){
            arrayOfBlocks[1]=getBlock(blockIdentif);
            arrayOfBlocks[1].print();

        }else if(blockIdentif ==3){
            arrayOfBlocks[2]=getBlock(blockIdentif);
            arrayOfBlocks[2].print();
        }else{
            arrayOfBlocks[3]=getBlock(blockIdentif);
            arrayOfBlocks[3].print();
        }
        return arrayOfBlocks;
    }

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
