package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This class implements the ability to move twice the same worker
 */
public class MoveWorkerTwice extends MoveTwice {

    public MoveWorkerTwice(God newGod) {
        super(newGod);
    }

    /**
     * This method implements the ability to move twice the same worker and it don't allow to go back at the first move start position
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return False if you can do another move; true if the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        if ( !super.samePosition( pos ))
            return super.moveTwice( worker, pos );
        return super.moveWorker(worker, pos);
    }
}
