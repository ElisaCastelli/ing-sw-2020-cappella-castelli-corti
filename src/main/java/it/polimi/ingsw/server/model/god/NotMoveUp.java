package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import java.util.ArrayList;

/**
 *This class implements the ability to not move up of the opponent workers
 */
public class NotMoveUp extends GodDecorator {

    public NotMoveUp(God newGod) {
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
        super.setPossibleMove(worker);
        for (int indexBoxNextTo = 0; indexBoxNextTo < 8; indexBoxNextTo++) {
            Box boxNextTo = worker.getActualBox().getBoxesNextTo().get(indexBoxNextTo);
            if (boxNextTo != null && boxNextTo.getCounter() - worker.getHeight() == 1 && moveUp){
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
     * This method doesn't allow to move up, if an opponent player uses this ability
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return False if you can't do the move; true if the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
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

    public boolean canMoveUp (Worker worker, Box finalBox) {
        return moveUp || (finalBox.getCounter() - worker.getHeight() != 1);
    }
}
