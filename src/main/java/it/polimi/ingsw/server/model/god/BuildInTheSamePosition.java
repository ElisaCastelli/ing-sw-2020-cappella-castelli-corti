package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.building.*;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import java.util.ArrayList;

/**
 * This class implements the ability to build twice in the same position
 */

public class BuildInTheSamePosition extends MoveTwice {
    /**
     * Constructor of the class
     *
     * @param newGod God
     */
    public BuildInTheSamePosition(God newGod) {
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
     * Name getter
     *
     * @return name of the card
     */
    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * Effects getter
     *
     * @return array of effects of the card
     */
    @Override
    public ArrayList<String> getEffects() {
        return super.getEffects();
    }

    /**
     * This method tells which positions can get reached by a worker
     *
     * @param worker Which worker is the check applied
     */
    @Override
    public boolean setPossibleMove(Worker worker) {
        if (!super.firstTime)
            super.firstTime = true;
        return super.setPossibleMove(worker);
    }

    /**
     * This method tells which positions can get built by a worker: during the second build move, the worker must build in the same position of the first build move
     *
     * @param worker Which worker is the check applied
     */
    @Override
    public void setPossibleBuild(Worker worker) {
        if (super.firstTime) {
            super.setPossibleBuild(worker);
        } else {
            for (int indexBoxNextTo = 0; indexBoxNextTo < 8; indexBoxNextTo++) {
                Box boxNextTo = worker.getActualBox().getBoxesNextTo().get(indexBoxNextTo);
                if (boxNextTo != null && super.samePosition(boxNextTo) && boxNextTo.getCounter() <= 2) {
                    boxNextTo.setReachable(true);
                    Block block = whatCanIBuild(boxNextTo);
                    if (block != null)
                        boxNextTo.getPossibleBlock().add(block);
                }
            }
        }
    }

    /**
     * This method checks which block could be built in a box
     *
     * @param box box that is going to be checked
     * @return type of block that can be built
     */
    @Override
    public Block whatCanIBuild(Box box) {
        return super.whatCanIBuild(box);
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
        return super.moveWorker(worker, pos);
    }

    /**
     * This method is able to build twice in the same position, but the second time the player cannot build a dome
     *
     * @param pos Position on the board where the worker builds a building block or two building blocks
     * @return False if you can do another construction; true if the move has done successfully
     */
    @Override
    public boolean moveBlock(Box pos) {
        if (super.firstTime && pos.getCounter() <= 1)
            return super.moveTwice(pos);
        else if (super.firstTime) {
            super.moveBlock(pos);
            if (pos.getCounter() == 4)
                completeTowers++;
            return true;
        } else if (super.samePosition(pos))
            return super.moveTwice(pos);
        return false;
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
