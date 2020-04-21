package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This class implements the possibility to move twice at the same turn
 */
public abstract class MoveTwice extends GodDecorator {

    public Box oldBoxMove = null;
    public boolean firstTime = true;

    public MoveTwice(God newGod) {
        super(newGod);
    }

    /**
     * This method checks which positions can get reached by a worker
     *
     * @param worker Which worker is the check applied
     */
    @Override
    public void setPossibleMove(Worker worker) {
        super.setPossibleMove(worker);
    }

    /**
     * This method moves the chosen worker to the new position on the board
     *
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return False if you can do another move; true if the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
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
     * This methods checks if the player win
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
     * This method enable the possibility to move twice a worker
     * @param worker Which worker is applied the move
     * @param finalBox Position on the board where the worker wants to go
     * @return False if the player can do another move, true if the player do the second move
     */
    public boolean moveTwice ( Worker worker, Box finalBox ) {
        if ( firstTime ) {
            oldBoxMove = worker.getActualBox();
            super.moveWorker( worker, finalBox );
            firstTime = false;
            return false;
        }
        else {
            super.moveWorker( worker, finalBox );
            oldBoxMove = null;
            firstTime = true;
            return true;
        }
    }

    /**
     * Thi method enable the possibility to build twice
     * @param finalBox Position on the board where the worker builds a building block
     * @return False if the player can do another construction, true if the player do the second construction
     */
    public boolean moveTwice ( Box finalBox ) {
        if ( firstTime ) {
            oldBoxMove = finalBox;
            super.moveBlock( finalBox );
            firstTime = false;
            return false;
        }
        else {
            super.moveBlock( finalBox );
            oldBoxMove = null;
            firstTime = true;
            return true;
        }
    }

    public boolean samePosition ( Box finalBox ){
        return oldBoxMove == finalBox;
    }
}
