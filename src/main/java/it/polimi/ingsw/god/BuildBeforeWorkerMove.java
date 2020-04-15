package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This class implements the ability to build before the worker move: you can build, move a worker at the same level or lower, and then build again.
 */
public class BuildBeforeWorkerMove extends MoveTwice {

    private boolean workerMoved = false;

    public BuildBeforeWorkerMove(God newGod) {
        super(newGod);
    }

    @Override
    public void setGodName(String godName) {

    }

    @Override
    public void setDescription(String description) {

    }

    @Override
    public void setEffect(String effect) {

    }

    @Override
    public String getGodName() {
        return null;
    }

    /**
     * This method checks which positions can get reached by a worker
     *
     * @param worker Which worker is the check applied
     * @return False if there are no positions that can get reached, otherwise return always true
     */
    @Override
    public void setPossibleMove(Worker worker) {
        super.setPossibleMove(worker);
    }

    /**
     * @param worker
     */
    @Override
    public void setPossibleBuild(Worker worker) {
        super.setPossibleBuild(worker);
    }

    //todo Finire commento
    /**
     * This method implements two cases of the worker move because of this ability:
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
     * This method implements two cases of the build move: one build before the worker move, one build after the worker move
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

    /**
     * This methods checks if the player win
     *
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox   Position on the board where the worker arrives
     * @return False if the player doesn't win; true if the player wins
     */
    @Override
    public boolean checkWin(Box initialPos, Box finalBox) {
        return super.checkWin(initialPos, finalBox);
    }
}
