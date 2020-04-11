package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This class implements the ability to not allow to move up to the other players
 */
public class OpponentBlock extends GodDecorator {

    public OpponentBlock(God newGod) {
        super(newGod);
    }

    /**
     * This method implements a block to the other player if the given worker moves up a maximum of one level
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return Always true because the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        if ( pos.getCounter() - worker.getHeight() == 1)
            super.moveUp = true;
        else
            super.moveUp = false;
        return super.moveWorker(worker, pos);
    }
}
