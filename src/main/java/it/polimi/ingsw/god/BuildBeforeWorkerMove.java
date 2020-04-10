package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

public class BuildBeforeWorkerMove extends MoveTwice {

    private boolean workerMoved = false;

    public BuildBeforeWorkerMove(God newGod) {
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
        if ( super.firstTime ) {
            workerMoved = true;
            return super.moveWorker(worker, pos);
        }
        else if ( worker.getHeight() - pos.getCounter() >= 0 ) {
            workerMoved = true;
            return super.moveWorker(worker, pos);
        }
        return false;
    }

    /**
     * This method builds a building block in a position on the board
     *
     * @param pos Position on the board where the worker builds a building block
     * @return False if you can do another construction; true if the move has done successfully
     */
    @Override
    public boolean moveBlock(Box pos) {
        if ( super.firstTime && workerMoved )
            return super.moveBlock( pos );
        else if ( !workerMoved && super.firstTime )
            return super.moveTwice( pos );
        return super.moveTwice( pos );
    }
}
