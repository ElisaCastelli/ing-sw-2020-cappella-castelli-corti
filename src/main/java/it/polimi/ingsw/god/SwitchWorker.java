package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This class implements the ability to switch the position with an enemy worker
 */
public class SwitchWorker extends GodDecorator {

    public SwitchWorker(God newGod) {
        super(newGod);
    }

    /**
     * This method labels a box next to the worker as a reachable box even if there is an opponent worker
     * @param worker Which worker is the check applied
     */
    @Override
    public void setPossibleMove(Worker worker) {
        for (int indexBoxNextTo = 0; indexBoxNextTo < 9; indexBoxNextTo++) {
            Box boxNextTo = worker.getActualBox().getBoxesNextTo().get(indexBoxNextTo);
            if (!boxNextTo.notWorker() && (boxNextTo.getCounter() - worker.getHeight() <= 1)){
                boxNextTo.setReachable(true);
            }
        }
        super.setPossibleMove(worker);
    }

    /**
     * @param worker
     */
    @Override
    public void setPossibleBuild(Worker worker) {
        super.setPossibleBuild(worker);
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

    /**
     * This method builds a building block in a position on the board
     *
     * @param pos Position on the board where the worker builds a building block
     * @return False if you can do another construction; true if the move has done successfully
     */
    @Override
    public boolean moveBlock(Box pos) {
        return super.moveBlock(pos);
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
