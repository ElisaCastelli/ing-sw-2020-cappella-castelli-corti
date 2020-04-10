package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

public class MoveWorkerTwice extends MoveTwice {

    public MoveWorkerTwice(God newGod) {
        super(newGod);
    }

    /**
     *
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
