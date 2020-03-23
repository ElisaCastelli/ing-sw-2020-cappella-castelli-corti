package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

public class MoveWorkerTwice extends GodDecorator {

    public MoveWorkerTwice (God newGod) {super(newGod);}

    @Override
    public void moveWorker(Worker worker, Box pos) {
        super.moveWorker(worker, pos);

    }
}
