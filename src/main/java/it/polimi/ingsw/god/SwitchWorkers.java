package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

//ability to switch the position with an enemy worker if it is next to an own worker (Apollo)
public class SwitchWorkers extends GodDecorator {

    public SwitchWorkers(God newGod) {
        super(newGod);
    }

    @Override
    public void moveWorker(Worker worker, Box pos) {
        super.moveWorker(worker, pos);
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
                worker.setHeight(heightWorker--);

            }
            else if (isCorrectWorkerMove==3) {
                worker.setActualBox(pos);

            }
        }
    }
}
