package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;

/**
 * This class implements the ability to build twice in two different positions
 */
public class OtherPositionToBuild extends MoveTwice {

    public OtherPositionToBuild(God newGod) {
        super(newGod);
    }

    /**
     * This method builds a building block in a position on the board and another building block in another position
     * @param pos Position on the board where the worker builds a building block
     * @return False if you can do another construction; true if the move has done successfully
     */
    @Override
    public boolean moveBlock(Box pos) {
        if ( !super.samePosition( pos ))
            return super.moveTwice( pos );
        return false;
    }
}
