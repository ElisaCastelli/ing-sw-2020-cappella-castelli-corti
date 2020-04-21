package it.polimi.ingsw.building;

import java.util.ArrayList;
import java.util.Iterator;

public class Building {
    private final ArrayList < Block > arrayOfBlocks;

    public Building () {
        arrayOfBlocks = new ArrayList <> () ;
    }
    /**
     * As it is a factory method it build a block based on the size of arrayOfBlocks
     * without asking the gamer to specified the block.
     * This method recall getBlock
     */
    public void build () {
        if( arrayOfBlocks.size ( ) == 0 ) {
            arrayOfBlocks.add ( getBlock (1 ) );
            //arrayOfBlocks.get(0).print();

        }else if( arrayOfBlocks.size ( ) == 1 ) {
            arrayOfBlocks.add ( getBlock (2 ) );
            //arrayOfBlocks.get(1).print();

        } else if ( arrayOfBlocks.size() == 2 ) {
            arrayOfBlocks.add(getBlock(3));
            //arrayOfBlocks.get(2).print();
        } else {
            arrayOfBlocks.add( getBlock (4 ) );
            //arrayOfBlocks.get(3).print();
        }
    }

    /**
     * this method is used by Atlas to build a dome at any case
     * @param domeIdent is set to 4 to identify the Dome
     */
    public void build ( int domeIdent ) {
        arrayOfBlocks.add( getBlock( domeIdent ) );
        while( arrayOfBlocks.size( ) < 4 ) {
            arrayOfBlocks.add( getBlock(5 ) );
        }
    }
    /**
     * @param blockIdentifier is an int passed by the method Build to build the rightful block
     * @return the block created to Build so that can be added to arrayOfBlocks
     */
    public Block getBlock( int blockIdentifier ){
        Block block = null;
        if( blockIdentifier == 1 ){
            BuildingBase baseBuilder = new BuildingBase();
            block = baseBuilder.getBlock();
        }else if( blockIdentifier == 2 ){
            BuildingMiddle middleBuilder = new BuildingMiddle();
            block = middleBuilder.getBlock();
        }else if( blockIdentifier == 3 ){
            BuildingTop topBuilder = new BuildingTop();
            block = topBuilder.getBlock();
        }else if( blockIdentifier == 4 ){
            BuildingDome domeBuilder = new BuildingDome();
            block = domeBuilder.getBlock();
        }else if ( blockIdentifier == 5 ){
            BuildingInvisibleBlk invisibleBlkBuilder = new BuildingInvisibleBlk();
            block = invisibleBlkBuilder.getBlock();
        }
       return block;
    }

    /**
     * //REMEMBER the index is not controlled if is illegal
     * @param pos is an index of the arrayOfBlocks
     * @return the name of the block in the list at the specified index
     */
    public String getBlocksNameInArray ( int pos ) {
        return arrayOfBlocks.get( pos ).toString();
    }
    /**
     * @return the array is a list of the block that was created in a Box
     */
    public ArrayList<Block> getArrayOfBlocks () {
        return arrayOfBlocks;
    }
    /**
     * this method print out the building of a single box
     */
    public void print () {
        Iterator < Block > blockIterator = arrayOfBlocks.iterator();

        if ( arrayOfBlocks.size() != 0 ) {
            System.out.println( "Building:" );
                while ( ( blockIterator.hasNext() ) ) {
                    System.out.println( blockIterator.next() + " " );
                }
        }
    }
    /**
     * this method clear the arrayOfBlock so that the size of the list is equal to zero and the building is !=null
     */
    public void clear () {
        if( arrayOfBlocks.size() != 0 ){
            arrayOfBlocks.clear();
        }

    }
}
