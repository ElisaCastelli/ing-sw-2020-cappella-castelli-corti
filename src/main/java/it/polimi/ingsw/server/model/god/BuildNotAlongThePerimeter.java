package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import java.util.ArrayList;

/**
 * This class implements the ability to build twice, but the second time, the worker cannot build in a perimeter space
 */
public class BuildNotAlongThePerimeter extends MoveTwice {
    /**
     * Constructor of the class
     *
     * @param newGod God
     */
    public BuildNotAlongThePerimeter(God newGod) {
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
     * This method checks which positions can get reached by a worker
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
     * This method tells which positions can get built by a worker, if it's the second construction, the player cannot build along the perimeter
     *
     * @param worker Which worker is the check applied
     */
    @Override
    public void setPossibleBuild(Worker worker) {
        super.setPossibleBuild(worker);
        if (!super.firstTime) {
            for (int indexBoxNextTo = 0; indexBoxNextTo < 8; indexBoxNextTo++) {
                Box boxNextTo = worker.getActualBox().getBoxesNextTo().get(indexBoxNextTo);
                if (boxNextTo != null && boxNextTo.getCounter() != 4 && boxNextTo.notWorker() && boxNextTo.getBoxesNextTo().contains(null)) {
                    boxNextTo.setReachable(false);
                    boxNextTo.getPossibleBlock().clear();
                }
            }
        }
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
     * This method builds a building block in a position on the board: the second time, the player cannot build in a perimeter space
     *
     * @param pos Position on the board where the worker builds a building block
     * @return False if you can do another construction; true if the move has done successfully
     */
    @Override
    public boolean moveBlock(Box pos) {
        return super.moveTwice(pos);
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
