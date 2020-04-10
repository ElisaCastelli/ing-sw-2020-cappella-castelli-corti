package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

public class ShiftWorker extends MoveTwice {

    public ShiftWorker(God newGod) {
        super(newGod);
    }

    /**
     * This method moves the chosen worker to the new position on the board
     *
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return False if you can do another move; true if the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        return super.moveWorker(worker, pos);
    }
}
