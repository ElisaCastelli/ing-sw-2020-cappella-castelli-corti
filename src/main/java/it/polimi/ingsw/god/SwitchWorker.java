package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This class implements the ability to switch the position with an enemy worker
 */
public class SwitchWorker extends NotMoveUp {

    public SwitchWorker(God newGod) {
        super(newGod);
    }

    /**
     * This method labels a box next to the worker as a reachable box even if there is an opponent worker
     * @param worker Which worker is the check applied
     * @return False if there are no positions that can get reached, otherwise return always true
     */
    @Override
    public boolean checkPossibleMove(Worker worker) {
        return super.checkPossibleMove(worker);
    }

    /**
     * This method implements the ability to switch the position with an enemy worker
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return Alwayes true because the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        if ( !pos.notWorker() ){
            Worker opponentWorker = pos.getWorker();
            Box myBox = worker.getActualBox();
            super.moveWorker( opponentWorker, myBox );
            super.moveWorker( worker, pos );
            myBox.setWorker( opponentWorker );
            return true;
        }
        return super.moveWorker(worker, pos);
    }
}
