package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This class represents a god concrete decorator which implements the ability to move the worker twice, but it must not
 * move back to the initial place (Artemis ability)
 */
public class MoveWorkerTwice extends GodDecorator {

    public MoveWorkerTwice (God newGod) {super(newGod);}

    /**
     *
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     */
    @Override
    public void moveWorker(Worker worker, Box pos) {
        super.moveWorker(worker, pos);

    }
}
