package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import java.util.ArrayList;

/**
 * This class implements the ability to win if the worker moves down two or more levels lower
 */

public class DownTwoOrMoreLevelsWin extends GodDecorator {
    /**
     * Constructor of the class
     *
     * @param newGod God
     */
    public DownTwoOrMoreLevelsWin(God newGod) {
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
     * This method tells which positions can get reached by a worker
     *
     * @param worker Which worker is the check applied
     */
    @Override
    public boolean setPossibleMove(Worker worker) {
        return super.setPossibleMove(worker);
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
     * This method checks if the worker moves down two or more levels lower, and in this case the player wins, although it calls the basic check win
     *
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox   Position on the board where the worker arrives
     * @return False if the player doesn't win; true if the player wins
     */
    @Override
    public boolean checkWin(Box initialPos, Box finalBox) {
        if (initialPos.getCounter() - finalBox.getCounter() >= 2)
            return true;
        return super.checkWin(initialPos, finalBox);
    }
}
