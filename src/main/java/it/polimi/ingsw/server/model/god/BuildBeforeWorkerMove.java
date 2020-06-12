package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import java.util.ArrayList;

/**
 * This class implements the ability to build before the worker move: you can build, move a worker at the same level or lower, and then build again.
 */

public class BuildBeforeWorkerMove extends MoveTwice {

    /**
     * This attribute tells if the worker has been moved or not
     */
    private boolean workerMoved = false;

    public BuildBeforeWorkerMove(God newGod) {
        super(newGod);
    }

    @Override
    public void setName(String godName) {
        super.setName(godName);
    }

    @Override
    public void setEffect(ArrayList<String> effects) {
        super.setEffect(effects);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public ArrayList<String> getEffects() {
        return super.getEffects();
    }

    /**
     * This method tells which positions can get reached by a worker
     * @param worker Which worker is the check applied
     */
    @Override
    public void setPossibleMove(Worker worker) {
        if(super.firstTime){
            super.setPossibleMove(worker);
        }else{
            super.setPossibleMove(worker);
            for (int indexBoxNextTo = 0; indexBoxNextTo < 8; indexBoxNextTo++) {
                Box boxNextTo = worker.getActualBox().getBoxesNextTo().get(indexBoxNextTo);
                if (boxNextTo != null && boxNextTo.getCounter() - worker.getHeight() == 1 ){
                    boxNextTo.setReachable(false);
                }
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
     * This method moves the chosen worker to the new position on the board
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return False if you can do another move; true if the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        workerMoved = true;
        return super.moveWorker(worker,pos);
    }

    /**
     * This method implements two cases of the build move: one build before the worker move, one build after the worker move
     * @param pos Position on the board where the worker builds a building block
     * @return False if you can do another construction; true if the move has done successfully
     */
    @Override
    public boolean moveBlock(Box pos) {
        if( super.firstTime && workerMoved ){//basic move
            workerMoved = false;
            super.moveBlock(pos);
            if (pos.getCounter() == 4)
                completeTowers++;
            return true;
        }else if(super.firstTime){//first time of decorator move
            super.moveTwice(pos);
            return true;
        }else{//second time of decorator move
            workerMoved = false;
            return super.moveTwice(pos);
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

    /**
     * @return true because who has this decorator class has the possibility to build before the worker move
     */
    @Override
    public boolean canBuildBeforeWorkerMove() {
        return true;
    }
}
