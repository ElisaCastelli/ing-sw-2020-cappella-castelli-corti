package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This class represents a god concrete decorator which implements the Gods that have a worker move effect
 */
public class MoveEffect extends GodDecorator {

    public MoveEffect (God newGod) {
        super(newGod);
    }

    /**
     *
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        if(godName.equals("Apollo")) {
            if(!pos.notWorker()) {
                switchWorkers(worker, pos);
            }
            else {
                return super.moveWorker(worker, pos);
            }
        }
        else if(godName.equals("Artemis")) {
            moveTwice(worker, pos);
        }
    }

    /**
     * This method implements the ability to switch the position with an enemy worker if it is next to an own worker
     * (Apollo ability)
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     */
    public void switchWorkers (Worker worker, Box pos) {
        int counterPos=pos.getCounter();
        Box tempBox=worker.getActualBox();
        if(tempBox.reachable(pos) && counterPos!=4) {
            int heightWorker=worker.getHeight();

            if(isCorrectWorkerMove==1) {
                worker.setActualBox(pos);
                worker.setHeight(heightWorker++);
                //TO DO metodo o qualcosa per selezionare la pedina dell'avversario in modo tale da fare lo switch
            }
            else if (isCorrectWorkerMove==2){
                worker.setActualBox(pos);
                worker.setHeight(counterPos);

            }
            else if (isCorrectWorkerMove==3) {
                worker.setActualBox(pos);

            }
        }
    }

    /**
     * This method implements the ability to move the worker twice, but it must not move back to the initial place
     * (Artemis ability)
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     */
    public void moveTwice (Worker worker, Box pos) {

    }

    @Override
    public boolean moveBlock(Worker worker, Box pos) {
        return super.moveBlock(worker, pos);
    }
}
