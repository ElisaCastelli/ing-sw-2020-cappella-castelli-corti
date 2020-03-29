package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This is God concrete class which implements the basic moves of the workers and of the build
 */
public class Gods implements God {
    /**
     * This attribute is the name of the God
     */
    private String godName;

    /**
     * This constructor instantiates a God with the given godName, isCorrectWorkerMove and isCorrectBlockMove
     * @param godName Name of the God to be instantiated
     */
    public Gods (String godName) {
        this.godName=godName;
    }

    public String getGodName(String godName) {return this.godName=godName;}
    public void setGodName(String godName) {this.godName = godName;}

    /**
     * This method implements the basic worker move: a chosen worker moves in a new position that must be unoccupied
     * (not containing a worker or a dome) and must be next to the worker.
     * The worker may move up a maximum of one level higher, move down any number of levels lower or move along the
     * same level.
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     */
    @Override
    public boolean moveWorker (Worker worker, Box pos) {
        Box boxWorker=worker.getActualBox();
        int heightWorker=worker.getHeight();
        int counterPos=pos.getCounter();

        if (boxWorker.reachable(pos) && pos.notWorker() && counterPos!=4) {
            //first case: the worker moves up a maximum of one level higher;
            //second case: the worker moves down any number of levels lower;
            //third case: the worker moves along the same level.
            if (counterPos-heightWorker==1) {
                    worker.setHeight(heightWorker+1);
                    worker.setActualBox(pos);
                    return true;
            }
            else if (heightWorker-counterPos>=1) {
                    worker.setHeight(counterPos);
                    worker.setActualBox(pos);
                    return true;
            }
            else if (heightWorker==counterPos) {
                    worker.setActualBox(pos);
                    return true;
            }
            //se si prova un caso in cui non entra in nessuna delle tre condizioni significa che la mossa non è valida
            //in quanto sale di troppi livelli
            //checkWin();
        }
        else { return false; }
    }

    /**
     * This method implements the basic block move: a chosen worker builds a block or a dome in a position next to him
     * onto a level of any height and this position must be unoccupied (not containing a worker or a dome)
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker builds a building block
     */
    @Override
    public boolean moveBlock(Worker worker, Box pos) {
        Box boxWorker=worker.getActualBox();
        int counterPos=pos.getCounter();

        if (boxWorker.reachable(pos) && pos.notWorker() && counterPos!=4) {
            pos.build();
            isCorrectBlockMove=true;
        }
        return true; //da sistemare
    }


    public void checkWin() {

    }
}
