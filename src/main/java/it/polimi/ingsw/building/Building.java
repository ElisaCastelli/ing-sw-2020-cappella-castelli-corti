package it.polimi.ingsw.building;

import java.util.ArrayList;
import java.util.Iterator;

public class Building {
    private ArrayList<Block> arrayOfBlocks;

    public Building() {
        arrayOfBlocks=new ArrayList<>();

    }

    public ArrayList<Block> build(int blockIdentif){
        if(blockIdentif == 1){
            arrayOfBlocks.add(getBlock(blockIdentif));
            //arrayOfBlocks.get(0).print();

        }else if(blockIdentif==2){
            arrayOfBlocks.add(getBlock(blockIdentif));
            //arrayOfBlocks.get(1).print();

        }else if(blockIdentif ==3){
            arrayOfBlocks.add(getBlock(blockIdentif));
            //arrayOfBlocks.get(2).print();
        }else{
            arrayOfBlocks.add(getBlock(blockIdentif));
            //arrayOfBlocks.get(3).print();
        }
        return arrayOfBlocks;
    }

    public Block getBlock(int blockIdentifier){
        Block block;
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

    public void print() {
        Iterator<Block> blockIterator=arrayOfBlocks.iterator();

        if(arrayOfBlocks.size()!=0){
            System.out.println("Building:");
                while ((blockIterator.hasNext())) {
                    System.out.println(blockIterator.next() + " ");
                }
        }
    }
    public void clear(){
        if(arrayOfBlocks.size()!=0){
            arrayOfBlocks.clear();
        }

    }
}
