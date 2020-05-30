package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import java.util.ArrayList;

/**
 * This abstract class handles the different concrete decorators
 */

public abstract class GodDecorator implements God {

    protected final God newGod;
    protected static boolean moveUp;
    protected static int completeTowers;

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
     * This method checks if the player wins
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox   Position on the board where the worker arrives
     * @return False if the player doesn't win; true if the player wins
     */
    @Override
    public boolean checkWin(Box initialPos, Box finalBox) {
        return this.newGod.checkWin( initialPos, finalBox );
    }

    @Override
    public boolean canBuildBeforeWorkerMove() {
        return this.newGod.canBuildBeforeWorkerMove();
    }
}
