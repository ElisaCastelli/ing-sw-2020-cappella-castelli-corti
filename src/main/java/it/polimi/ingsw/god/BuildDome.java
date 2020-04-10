package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;

/**
 * This class implements the ability to build a Dome everywhere even it is not at the fourth level
 */
public class BuildDome extends GodDecorator {

    public BuildDome(God newGod) {
        super(newGod);
    }

    /**
     * This method is able to build a Dome everywhere
     * @param pos Position on the board where the worker builds a building block
     * @return False if you can do another construction; true if the move has done successfully
     */
    @Override
    public boolean moveBlock(Box pos) {
        //todo controllo che vuole costruire una dome non al 4 livello
        if (pos.getCounter() != 4) {
            pos.build(4);
            return true;
        }
        else
            return super.moveBlock(pos);
    }
}
