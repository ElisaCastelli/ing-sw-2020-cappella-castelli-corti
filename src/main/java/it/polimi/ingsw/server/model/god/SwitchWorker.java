package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import java.util.ArrayList;

/**
 * This class implements the ability to switch the position with an enemy worker
 */

public class SwitchWorker extends GodDecorator {
    /**
     * Constructor of the class
     *
     * @param newGod God
     */
    public SwitchWorker(God newGod) {
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
     * This method labels a box next to the worker as a reachable box even if there is an opponent worker
     *
     * @param worker Which worker is the check applied
     */
    @Override
    public boolean setPossibleMove(Worker worker) {

        for (int indexBoxNextTo = 0; indexBoxNextTo < 8; indexBoxNextTo++) {
            Box boxNextTo = worker.getActualBox().getBoxesNextTo().get(indexBoxNextTo);
            if (boxNextTo != null && !boxNextTo.notWorker() && (worker.getIndexPlayer() != boxNextTo.getWorker().getIndexPlayer()) && (boxNextTo.getCounter() - worker.getHeight() <= 1)) {
                boxNextTo.setReachable(true);
            }
        }
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
     * This method implements the ability to switch the position with an enemy worker
     *
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return Always true because the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        if (!pos.notWorker()) {
            Worker opponentWorker = pos.getWorker();
            Box myBox = worker.getActualBox();
            super.moveWorker(opponentWorker, myBox);
            super.moveWorker(worker, pos);
            myBox.setWorker(opponentWorker);
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
        super.moveBlock(pos);
        if (pos.getCounter() == 4)
            completeTowers++;
        return true;
    }

    /**
     * This method checks if the player wins
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
