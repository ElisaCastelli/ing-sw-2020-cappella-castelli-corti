package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.building.*;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;

/**
 * This is God concrete class which implements the basic moves of the workers and of the build
 */
public class BasicGod implements God {

    /**
     * This is the God name
     */
    private String name;
    /**
     * This array will contain all the decorator classes that each God is going to use
     */
    private ArrayList<String> effects;

    /**
     * Name setter
     *
     * @param name name of the card
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Effects setter
     *
     * @param effects array of effects of the card
     */
    public void setEffect(ArrayList<String> effects) {
        this.effects = effects;
    }

    /**
     * Name getter
     *
     * @return name of the card
     */
    public String getName() {
        return name;
    }

    /**
     * Effects getter
     *
     * @return array of effects of the card
     */
    public ArrayList<String> getEffects() {
        return effects;
    }

    /**
     * This method tells which positions can get reached by a worker
     *
     * @param worker Which worker is the check applied
     */
    @Override
    public boolean setPossibleMove(Worker worker) {
        for (int indexBoxNextTo = 0; indexBoxNextTo < 8; indexBoxNextTo++) {
            Box boxNextTo = worker.getActualBox().getBoxesNextTo().get(indexBoxNextTo);
            if (boxNextTo != null && boxNextTo.notWorker() && boxNextTo.getCounter() != 4 && (boxNextTo.getCounter() - worker.getHeight() <= 1)) {
                boxNextTo.setReachable(true);
            }
        }
        return true;
    }

    /**
     * This method tells which positions can get built by a worker
     *
     * @param worker Which worker is the check applied
     */
    @Override
    public void setPossibleBuild(Worker worker) {
        for (int indexBoxNextTo = 0; indexBoxNextTo < 8; indexBoxNextTo++) {
            Box boxNextTo = worker.getActualBox().getBoxesNextTo().get(indexBoxNextTo);
            if (boxNextTo != null && boxNextTo.getCounter() != 4 && boxNextTo.notWorker()) {
                boxNextTo.setReachable(true);
                Block block = whatCanIBuild(boxNextTo);
                if (block != null)
                    boxNextTo.getPossibleBlock().add(block);
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
        int sizeArrayBlocks = box.getBuilding().getArrayOfBlocks().size();

        Block block;
        if (sizeArrayBlocks == 0) {
            block = new Base();
        } else if (sizeArrayBlocks == 1) {
            block = new Middle();
        } else if (sizeArrayBlocks == 2) {
            block = new Top();
        } else {
            block = new Dome();
        }
        return block;
    }

    /**
     * This method implements the worker move from its start position to its final position
     *
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return Always true because the move succeeded
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        Box myBox = worker.getActualBox();
        worker.setActualBox(pos);
        worker.setHeight(pos.getCounter());
        pos.setWorker(worker);
        myBox.clearWorker();
        return true;
    }

    /**
     * This method implements the basic block move which builds the correct block in a given position
     *
     * @param pos Position on the board where the worker builds a building block
     * @return Always true because the move succeeded
     */
    @Override
    public boolean moveBlock(Box pos) {
        pos.build();
        return true;
    }

    /**
     * This method sets the index of the block that could be built
     *
     * @param indexPossibleBlock index of the block
     */
    @Override
    public void setIndexPossibleBlock(int indexPossibleBlock) {
    }

    /**
     * This method implements the basic winning rule: if the worker moves up a maximum of one level and it is level 3, the player wins.
     *
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox   Position on the board where the worker arrives
     * @return False if the player doesn't win, true if the player wins
     */
    @Override
    public boolean checkWin(Box initialPos, Box finalBox) {
        return finalBox.getCounter() - initialPos.getCounter() == 1 && finalBox.getCounter() == 3;
    }

    /**
     * This method is used by a God with the ability to build before the worker move
     *
     * @return always false
     */
    @Override
    public boolean canBuildBeforeWorkerMove() {
        return false;
    }
}
