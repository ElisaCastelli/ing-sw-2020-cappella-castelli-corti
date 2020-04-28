package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.gameComponents.Box;
import it.polimi.ingsw.model.gameComponents.Worker;

import java.util.ArrayList;

/**
 * This class implements the ability to move twice the same worker
 */

public class MoveWorkerTwice extends MoveTwice {

    public MoveWorkerTwice(God newGod) {
        super(newGod);
    }

    @Override
    public void setName(String godName) {

    }

    @Override
    public void setEffect(ArrayList<String> effects) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public ArrayList<String> getEffects() {
        return null;
    }

    /**
     * This method tells which positions can get reached by a worker: during the second worker move, the worker cannot move back to the starter position of the first worker move
     * @param worker Which worker is the check applied
     */
    @Override
    public void setPossibleMove(Worker worker) {
        super.setPossibleMove(worker);
        if (!super.firstTime){
            for (int indexBoxNextTo = 0; indexBoxNextTo < 8; indexBoxNextTo++) {
                Box boxNextTo = worker.getActualBox().getBoxesNextTo().get(indexBoxNextTo);
                if (boxNextTo != null && super.samePosition(boxNextTo) )
                    boxNextTo.setReachable(false);
            }
        }
    }

    /**
     * This method tells which positions can get built by a worker
     * @param worker Which worker is the check applied
     */
    @Override
    public void setPossibleBuild(Worker worker) {
        super.setPossibleBuild(worker);
    }

    /**
     * This method implements the ability to move twice the same worker and it doesn't allow to move back at the first move start position
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return False if you can do another move; true if the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        if ( !super.samePosition( pos ))
            return super.moveTwice( worker, pos );
        return false;
    }

    /**
     * This method builds a building block in a position on the board
     * @param pos Position on the board where the worker builds a building block
     * @return False if you can do another construction; true if the move has done successfully
     */
    @Override
    public boolean moveBlock(Box pos) {
        super.moveBlock(pos);
        if (pos.getCounter() == 4)
            completeTowers++;
        return true;
    }

    /**
     * This methods checks if the player wins
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox   Position on the board where the worker arrives
     * @return False if the player doesn't win; true if the player wins
     */
    @Override
    public boolean checkWin(Box initialPos, Box finalBox) {
        return super.checkWin(initialPos, finalBox);
    }
}
