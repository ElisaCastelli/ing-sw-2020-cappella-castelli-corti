package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This class represents a god concrete decorator which implements
 */
public class BuildEffect extends GodDecorator {

    public BuildEffect (God newGod) {super(newGod);}

    /**
     *
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        return super.moveWorker(worker, pos);
    }

    @Override
    public boolean moveBlock(Worker worker, Box pos) {
        return super.moveBlock(worker, pos);
    }
}
