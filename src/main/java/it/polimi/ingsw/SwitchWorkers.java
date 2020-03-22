package it.polimi.ingsw;
//ability to switch the position with an enemy worker if it is next to an own worker
public class SwitchWorkers extends GodDecorator {

    public SwitchWorkers(God newGod) {
        super(newGod);
    }

    /*@Override
    public void moveWorker(Worker worker, Box pos) {
        super.moveWorker(worker, pos);
    }*/
}
