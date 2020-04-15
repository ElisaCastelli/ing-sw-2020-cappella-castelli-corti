package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This class implements the ability to build twice in the same position
 */
public class BuildInTheSamePosition extends MoveTwice {

    public BuildInTheSamePosition(God newGod) {
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
        if (super.firstTime) {
            super.setPossibleBuild(worker);
        }
        else {
            for (int indexBoxNextTo = 0; indexBoxNextTo < 8; indexBoxNextTo++) {
                Box boxNextTo = worker.getActualBox().getBoxesNextTo().get(indexBoxNextTo);
                if (super.samePosition( boxNextTo) && boxNextTo.getCounter() <= 2) {
                    boxNextTo.setReachable(true);
                }
            }
        }
    }

    /**
     * This method moves the chosen worker to the new position on the board
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return False if you can do another move; true if the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        return super.moveWorker(worker, pos);
    }

    /**
     * This method is able to build twice in the same position, but the second time the player cannot build a dome
     * @param pos Position on the board where the worker builds a building block or two building blocks
     * @return False if you can do another construction; true if the move has done successfully
     */
    @Override
    public boolean moveBlock(Box pos) {
        if ( super.firstTime )
            return super.moveTwice( pos );
        else if ( pos.getCounter() <= 2 && super.samePosition( pos ))
            return super.moveTwice( pos );
        else {
            super.firstTime = true;
            super.oldBoxMove = null;
            return true;
        }
    }

    /**
     * This methods checks if the player win
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox   Position on the board where the worker arrives
     * @return False if the player doesn't win; true if the player wins
     */
    @Override
    public boolean checkWin(Box initialPos, Box finalBox) {
        return super.checkWin(initialPos, finalBox);
    }
}
