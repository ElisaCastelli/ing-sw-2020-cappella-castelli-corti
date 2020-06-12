package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.building.*;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import java.util.ArrayList;

/**
 * This class implements the ability to build a block under an own worker
 */
public class BuildABlockUnderItself extends GodDecorator {

    public BuildABlockUnderItself(God newGod) {
        super(newGod);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
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
    public void setPossibleMove(Worker worker) {
        super.setPossibleMove(worker);
    }

    /**
     * This method tells which positions can get built by a worker: the worker space become reachable for the building
     * @param worker Which worker is the check applied
     */
    @Override
    public void setPossibleBuild(Worker worker) {
        super.setPossibleBuild(worker);
        if(worker.getHeight() <= 2){
            worker.getActualBox().setReachable(true);
            Block block = whatCanIBuild(worker.getActualBox());
            if(block != null)
                worker.getActualBox().getPossibleBlock().add(block);
        }
    }

    /**
     * This method checks which block could be built in a box
     * @param box box that is going to be checked
     * @return type of block that can be built
     */
    @Override
    public Block whatCanIBuild(Box box) {
        return super.whatCanIBuild(box);
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
     * This method builds a building block in a position on the board: a worker can build a block under itself
     * @param pos Position on the board where the worker builds a building block
     * @return False if you can do another construction; true if the move has done successfully
     */
    @Override
    public boolean moveBlock(Box pos) {
        if(!pos.notWorker()){
            super.moveBlock(pos);
            pos.getWorker().setHeight(pos.getCounter());
            return true;
        }
        else {
            super.moveBlock(pos);
            if (pos.getCounter() == 4)
                completeTowers++;
            return true;
        }
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
