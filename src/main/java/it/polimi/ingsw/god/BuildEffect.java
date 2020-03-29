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
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        return super.moveWorker(worker, pos);
    }

    /**
     *
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker builds a building block
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    @Override
    public boolean moveBlock(Worker worker, Box pos) {
        return super.moveBlock(worker, pos);
    }
}
