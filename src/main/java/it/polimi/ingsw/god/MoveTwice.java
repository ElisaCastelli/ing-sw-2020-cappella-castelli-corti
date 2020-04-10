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
