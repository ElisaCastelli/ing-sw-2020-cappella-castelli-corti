package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

//decorator that implements the different Gods abilities
public abstract class GodDecorator implements God {
    protected God newGod;
    public GodDecorator(God newGod) {
        this.newGod=newGod;
    }

    @Override
    public void moveWorker(Worker worker, Box pos) {
        this.newGod.moveWorker(worker, pos);
    }

    @Override
    public void moveBlock(Worker worker, Box pos) {
        this.newGod.moveBlock(worker, pos);
    }
}
