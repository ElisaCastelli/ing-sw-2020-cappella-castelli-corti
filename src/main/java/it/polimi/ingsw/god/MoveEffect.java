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
    public void moveWorker(Worker worker, Box pos) {
        super.moveWorker(worker, pos);
        if(godName.equals("Apollo")) {
            switchWorkers(worker, pos);
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
        if(!pos.notWorker()) {
            Box tempBox=worker.getActualBox();
            int heightWorker=worker.getHeight();
            int counterPos=pos.getCounter();
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
}
