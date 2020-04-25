package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Box;
import it.polimi.ingsw.model.Worker;

import java.util.ArrayList;

/**
 * This class implements the ability to move the same worker any times you want, but it has to move into a perimeter space.
 */
public class MoveInfinityTimesAlongThePerimeter extends GodDecorator{

    public MoveInfinityTimesAlongThePerimeter(God newGod) {
        super(newGod);
    }

    @Override
    public void setName(String name) {}

    @Override
    public void setEffect(ArrayList<String> effects) {}

    @Override
    public String getName() {
        return null;
    }

    @Override
    public ArrayList<String> getEffects() {
        return null;
    }

    /**
     * This method tells which positions can get reached by a worker
     * @param worker Which worker is the check applied
     */
    @Override
    public void setPossibleMove(Worker worker) {
        super.setPossibleMove(worker);
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
     * This method moves the chosen worker to the new position on the board, if this new position is along the perimeter, the player can move another time the worker
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return False if you can do another move; true if the move has done successfully and the worker cannot do another move
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        if (pos.getBoxesNextTo().contains(null)){
            super.moveWorker( worker, pos);
            return false;
        }
        else
            return super.moveWorker(worker, pos);
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
     * This methods checks if the player win
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox   Position on the board where the worker arrives
     * @return False if the player doesn't win; true if the player wins
     */
    @Override
    public boolean checkWin(Box initialPos, Box finalBox) {
        return super.checkWin(initialPos, finalBox);
    }
}