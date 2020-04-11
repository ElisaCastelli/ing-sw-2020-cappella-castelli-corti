package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;

/**
 * This class implements the ability to build twice in the same position
 */
public class BuildInTheSamePosition extends MoveTwice {

    public BuildInTheSamePosition(God newGod) {
        super(newGod);
    }

    /**
     *
     * @param pos Position on the board where the worker builds a building block
     * @return False if you can do another construction; true if the move has done successfully
     */
    @Override
    public boolean moveBlock(Box pos) {
        if ( super.firstTime )
            return super.moveTwice( pos );
        else if ( pos.getCounter() <= 2 && super.samePosition( pos ))
            return super.moveTwice( pos );
        else {
            super.firstTime = true;
            super.oldBoxMove = null;
            return true;
        }
    }
}