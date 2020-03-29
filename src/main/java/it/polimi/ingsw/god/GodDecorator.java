package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This abstract class represents the decorator of the Decorator Pattern which handles the different Gods' abilities
 */
public abstract class GodDecorator implements God {
    protected God newGod;
    public GodDecorator(God newGod) {
        this.newGod=newGod;
    }

    /**
     *
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        return this.newGod.moveWorker(worker, pos);
    }

    /**
     *
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker builds a building block
     */
    @Override
    public boolean moveBlock(Worker worker, Box pos) { return this.newGod.moveBlock(worker, pos); }
}
