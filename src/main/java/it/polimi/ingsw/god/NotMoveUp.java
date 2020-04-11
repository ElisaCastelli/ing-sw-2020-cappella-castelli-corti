package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 *This class implements the ability to not move up of the opponent workers
 */
public class NotMoveUp extends GodDecorator {

    public NotMoveUp(God newGod) {
        super(newGod);
    }

    //todo il controllo che non può salire va fatto sulla checkPossibleMove, non sul moveWorker (Tenere in conto che noi diamo al giocatore solo le mosse che può fare)
    /**
     * This method doesn't allow to move up, if an opponent player uses this ability
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return False if you can't do the move; true if the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        if ( canMoveUp( worker, pos) )
            return super.moveWorker(worker, pos);
        return false;
    }

    public boolean canMoveUp (Worker worker, Box finalBox) {
        return super.moveUp || (finalBox.getCounter() - worker.getHeight() != 1);
    }
}
