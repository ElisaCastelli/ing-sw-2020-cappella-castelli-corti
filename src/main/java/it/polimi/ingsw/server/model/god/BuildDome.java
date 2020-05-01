package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import java.util.ArrayList;

/**
 * This class implements the ability to build a Dome everywhere even the player is not building at the fourth level
 */
public class BuildDome extends GodDecorator {

    public BuildDome(God newGod) {
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
        //todo Bottone dome
    }

    /**
     * This method moves the chosen worker to the new position on the board
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return False if you can do another move; true if the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        return super.moveWorker(worker, pos);
    }

    /**
     * This method is able to build a Dome everywhere
     * @param pos Position on the board where the worker wants to build a dome
     * @return true because the move has done successfully in any case
     */
    @Override
    public boolean moveBlock(Box pos) {
        //todo controllo che vuole costruire una dome non al 4 livello + test
        if (pos.getCounter() != 4) {
            if (pos.getCounter() == 3)
                completeTowers++;
            pos.build(4);
            return true;
        }
        else{
            super.moveBlock(pos);
            if (pos.getCounter() == 4)
                completeTowers++;
            return true;
        }
    }

    /**
     * This method checks if the player wins
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox   Position on the board where the worker arrives
     * @return False if the player doesn't win; true if the player wins
     */
    @Override
    public boolean checkWin(Box initialPos, Box finalBox) {
        return super.checkWin(initialPos, finalBox);
    }
}
