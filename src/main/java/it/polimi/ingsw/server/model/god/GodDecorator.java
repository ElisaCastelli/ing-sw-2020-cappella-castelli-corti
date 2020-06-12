package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.building.Block;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import java.util.ArrayList;

/**
 * This abstract class handles the different concrete decorators
 */

public abstract class GodDecorator implements God {

    protected final God newGod;
    /**
     * This attribute indicates if the opponent workers can move up a level
     */
    protected static boolean moveUp;
    /**
     * This attribute counts how many complete towers are on the board
     */
    protected static int completeTowers;
    /**
     * This attribute is the index of the block that the player wants to build
     */
    protected static int indexPossibleBlock;

    public GodDecorator ( God newGod ) {
        this.newGod = newGod;
    }

    public boolean isMoveUp() {
        return moveUp;
    }

    public String getName(){
        return newGod.getName();
    }

    public void setName(String name){
        newGod.setName(name);
    }
    public ArrayList<String> getEffects() {
        return newGod.getEffects();
    }
    public void setEffect(ArrayList<String> effects) {
        newGod.setEffect(effects);
    }

    /**
     * This method tells which positions can get reached by a worker
     * @param worker Which worker is the check applied
     */
    @Override
    public void setPossibleMove(Worker worker) {
        this.newGod.setPossibleMove( worker );
    }

    /**
     * This method tells which positions can get built by a worker
     * @param worker Which worker is the check applied
     */
    @Override
    public void setPossibleBuild(Worker worker) {
        this.newGod.setPossibleBuild( worker );
    }

    /**
     * This method moves the chosen worker to the new position on the board
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return False if you can do another move; true if the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        return this.newGod.moveWorker( worker, pos );
    }

    /**
     * This method builds a building block in a position on the board
     * @param pos Position on the board where the worker builds a building block
     * @return False if you can do another construction; true if the move has done successfully
     */
    @Override
    public boolean moveBlock(Box pos) {
        return this.newGod.moveBlock( pos );
    }

    /**
     * This method checks which block could be built in a box
     *
     * @param box box that is going to be checked
     * @return type of block that can be built
     */
    @Override
    public Block whatCanIBuild(Box box) {
        return this.newGod.whatCanIBuild(box);
    }

    /**
     * This method sets the index of the block that could be built
     * @param possibleBlock index of the block
     */
    @Override
    public void setIndexPossibleBlock(int possibleBlock) {
        indexPossibleBlock = possibleBlock;
    }

    /**
     * This method checks if the player wins
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox   Position on the board where the worker arrives
     * @return False if the player doesn't win; true if the player wins
     */
    @Override
    public boolean checkWin(Box initialPos, Box finalBox) {
        return this.newGod.checkWin( initialPos, finalBox );
    }

    /**
     * This method is used by a God with the ability to build before the worker move
     * @return true if the player has this God, otherwise returns always false
     */
    @Override
    public boolean canBuildBeforeWorkerMove() {
        return this.newGod.canBuildBeforeWorkerMove();
    }
}
