package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This abstract class handles the different concrete decorators
 */
public abstract class GodDecorator implements God {

    protected God newGod;
    protected boolean moveUp;

    public GodDecorator ( God newGod ) {
        this.newGod = newGod;
    }



    /**
     * This method checks which positions can get reached by a worker
     * @param worker Which worker is the check applied
     * @return False if there are no positions that can get reached, otherwise return always true
     */
    @Override
    public void setPossibleMove(Worker worker) {
        this.newGod.setPossibleMove( worker );
    }

    /**
     * @param worker
     */
    @Override
    public void setPossibleBuild(Worker worker) {
        this.newGod.setPossibleBuild(worker);
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
     * This methods checks if the player win
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox   Position on the board where the worker arrives
     * @return False if the player doesn't win; true if the player wins
     */
    @Override
    public boolean checkWin(Box initialPos, Box finalBox) {
        return this.newGod.checkWin( initialPos, finalBox );
    }
}
