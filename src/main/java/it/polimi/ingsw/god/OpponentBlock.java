package it.polimi.ingsw.god;

import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

import java.util.ArrayList;

/**
 * This class implements the ability to not allow to move up to the other players
 */

public class OpponentBlock extends GodDecorator {

    public OpponentBlock(God newGod) {
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

    @Override
    public boolean isMoveUp() {
        return super.isMoveUp();
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
     * This method implements a block to the other player if the given worker moves up a maximum of one level
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return Always true because the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        if ( pos.getCounter() - worker.getHeight() == 1)
            super.moveUp = true;
        else
            super.moveUp = false;
        System.out.println("ho impostato il moveup:"+isMoveUp());
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
