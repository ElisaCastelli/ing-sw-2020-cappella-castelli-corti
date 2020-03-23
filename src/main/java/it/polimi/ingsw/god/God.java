package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

//god interface
public interface God {
    int isCorrectWorkerMove=0;
    boolean isCorrectBlockMove=false;
    void moveWorker(Worker worker, Box pos);
    void moveBlock(Worker worker, Box pos);
    //void checkWin();
}
