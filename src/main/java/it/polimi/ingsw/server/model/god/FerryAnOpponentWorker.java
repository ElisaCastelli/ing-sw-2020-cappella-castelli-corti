package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import java.util.ArrayList;

/**
 * This class implements the ability to force a neighboring opponent worker to the space on the other side of own worker, if that space is unoccupied
 */
public class FerryAnOpponentWorker extends MoveTwice {
    /**
     * Constructor of the class
     *
     * @param newGod God
     */
    public FerryAnOpponentWorker(God newGod) {
        super(newGod);
    }

    /**
     * Name setter
     *
     * @param name name of the card
     */
    @Override
    public void setName(String name) {
        super.setName(name);
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
     * This method checks which positions can get reached by a worker: during the first move, neighboring boxes are reachable even if there is an opponent worker which can be moved if the space where it goes is unoccupied;
     *
     * @param worker Which worker is the check applied
     */
    @Override
    public boolean setPossibleMove(Worker worker) {
        boolean oneReachable = super.setPossibleMove(worker);
        if (super.firstTime) {
            for (int indexBoxNextTo = 0; indexBoxNextTo < 8; indexBoxNextTo++) {
                Box boxNextTo = worker.getActualBox().getBoxesNextTo().get(indexBoxNextTo);

                if (boxNextTo != null && !boxNextTo.notWorker() && (worker.getIndexPlayer() != boxNextTo.getWorker().getIndexPlayer()) &&
                        otherSide(worker, boxNextTo) != null && otherSide(worker, boxNextTo).notWorker() && otherSide(worker, boxNextTo).getCounter() != 4) {
                    boxNextTo.setReachable(true);
                }
            }
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
        if (!super.firstTime)
            super.firstTime = true;
        super.setPossibleBuild(worker);
    }

    /**
     * This method moves the chosen worker to the new position on the board: during the first move, the player can force a neighboring opponent worker to the space on the other side of own worker
     *
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return False if you can do another move; true if the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        if (super.firstTime && !pos.notWorker()) {
            Box newEnemyPos = otherSide(worker, pos);
            Worker opponentWorker = pos.getWorker();
            return super.moveTwice(opponentWorker, newEnemyPos);
        } else if (super.firstTime) {
            return super.moveWorker(worker, pos);
        } else {
            return super.moveTwice(worker, pos);
        }
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

    /**
     * This method tells which is the space of the neighboring opponent worker on the other side of own worker
     *
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to move
     * @return Position where the opponent worker have to move
     */
    public Box otherSide(Worker worker, Box pos) {
        Box workerPos = worker.getActualBox();
        if ((workerPos.getColumn() - pos.getColumn() == 1) && (workerPos.getRow() - pos.getRow() == 1)) {
            return workerPos.getBoxesNextTo().get(7); //new position is down right
        } else if ((workerPos.getColumn() - pos.getColumn() == 1) && (pos.getRow() - workerPos.getRow() == 1)) {
            return workerPos.getBoxesNextTo().get(2); //new position is up right
        } else if (workerPos.getColumn() - pos.getColumn() == 1) {
            return workerPos.getBoxesNextTo().get(4); //new position is right
        } else if ((pos.getColumn() - workerPos.getColumn() == 1) && (workerPos.getRow() - pos.getRow() == 1)) {
            return workerPos.getBoxesNextTo().get(5); //new position is down left
        } else if ((pos.getColumn() - workerPos.getColumn() == 1) && (pos.getRow() - workerPos.getRow() == 1)) {
            return workerPos.getBoxesNextTo().get(0); //new position is up left
        } else if (pos.getColumn() - workerPos.getColumn() == 1) {
            return workerPos.getBoxesNextTo().get(3); //new position is left
        } else if (workerPos.getRow() - pos.getRow() == 1) {
            return workerPos.getBoxesNextTo().get(6); //new position is down
        } else {
            return workerPos.getBoxesNextTo().get(1); //new position is up
        }
    }
}
