package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;

/**
 * This class implements the ability to build a Dome everywhere even the player is not building at the fourth level
 */
public class BuildDome extends GodDecorator {

    public BuildDome(God newGod) {
        super(newGod);
    }

    /**
     * This method is able to build a Dome everywhere
     * @param pos Position on the board where the worker wants to build a dome
     * @return true because the move has done successfully in any case
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
