package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.building.Block;
import it.polimi.ingsw.server.model.building.Dome;
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
    public boolean setPossibleMove(Worker worker) {
        return super.setPossibleMove(worker);
    }

    /**
     * This method tells which positions can get built by a worker
     * @param worker Which worker is the check applied
     */
    @Override
    public void setPossibleBuild(Worker worker) {
        super.setPossibleBuild(worker);
        for (int indexBoxNextTo = 0; indexBoxNextTo < 8; indexBoxNextTo++) {
            Box boxNextTo = worker.getActualBox().getBoxesNextTo().get(indexBoxNextTo);
            if (boxNextTo != null && boxNextTo.getCounter() != 4 && boxNextTo.notWorker()) {
                boxNextTo.setReachable(true);
                if(boxNextTo.getPossibleBlock().size() < 3) {
                    Block block = new Dome();
                    boxNextTo.getPossibleBlock().add(block);
                }
            }
        }
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
        if(indexPossibleBlock == 1){
            if(pos.getCounter() == 3)
                completeTowers++;
            pos.build(4);
        }else{
            super.moveBlock(pos);
            if (pos.getCounter() == 4)
                completeTowers++;
        }
        return true;
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
