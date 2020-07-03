package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import java.util.ArrayList;

/**
 * This class implements the ability to not allow to move up to the other players
 */

public class OpponentBlock extends GodDecorator {
    /**
     * Constructor of the class
     *
     * @param newGod God
     */
    public OpponentBlock(God newGod) {
        super(newGod);
    }

    /**
     * Name setter
     *
     * @param godName name of the card
     */
    @Override
    public void setName(String godName) {
        super.setName(godName);
    }

    /**
     * Effects setter
     *
     * @param effects array of effects of the card
     */
    @Override
    public void setEffect(ArrayList<String> effects) {
        super.setEffect(effects);
    }


    /**
     * Is Move Up getter
     *
     * @return true if it can move up a building
     */
    @Override
    public boolean isMoveUp() {
        return super.isMoveUp();
    }

    /**
     * This method tells which positions can get reached by a worker
     *
     * @param worker Which worker is the check applied
     */
    @Override
    public boolean setPossibleMove(Worker worker) {
        super.setPossibleMove(worker);
        boolean oneReachable = false;
        for (int indexBoxNextTo = 0; indexBoxNextTo < 8; indexBoxNextTo++) {
            Box boxNextTo = worker.getActualBox().getBoxesNextTo().get(indexBoxNextTo);
            if (boxNextTo != null && boxNextTo.isReachable())
                oneReachable = true;
        }
        return oneReachable;
    }

    /**
     * This method tells which positions can get built by a worker
     *
     * @param worker Which worker is the check applied
     */
    @Override
    public void setPossibleBuild(Worker worker) {
        super.setPossibleBuild(worker);
    }

    /**
     * This method implements a block to the other player if the given worker moves up a maximum of one level
     *
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return Always true because the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        moveUp = pos.getCounter() - worker.getHeight() == 1;
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
        super.moveBlock(pos);
        if (pos.getCounter() == 4)
            completeTowers++;
        return true;
    }

    /**
     * This methods checks if the player wins
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
