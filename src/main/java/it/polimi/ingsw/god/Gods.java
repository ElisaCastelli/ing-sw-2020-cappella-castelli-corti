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
     * This attribute is the worker move type which tells if the worker moves up, moves down or stays at the same level
     */
    private int isCorrectWorkerMove;
    /**
     *
     */
    private boolean isCorrectBlockMove;

    /**
     * This constructor instantiates a God with the given godName, isCorrectWorkerMove and isCorrectBlockMove
     * @param godName Name of the God to be instantiated
     * @param isCorrectWorkerMove Worker move type to be instatiated
     * @param isCorrectBlockMove Block move type to be instatiated
     */
    public Gods (String godName, int isCorrectWorkerMove, boolean isCorrectBlockMove) {
        this.godName=godName;
        this.isCorrectWorkerMove=isCorrectWorkerMove;
        this.isCorrectBlockMove=isCorrectBlockMove;
    }

    /**
     * This method returns the String name of the God
     * @return String name of the God
     */
    public String getName() {return godName;}

    /**
     * This method sets the name og the God
     * @param name Name of the God
     */
    public void setName(String name) {this.godName=name;}

    /**
     * This method implements the basic worker move: a chosen worker moves in a new position that must be unoccupied
     * (not containing a worker or a dome) and must be next to the worker.
     * The worker may move up a maximum of one level higher, move down any number of levels lower or move along the
     * same level.
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     */
    @Override
    public void moveWorker (Worker worker, Box pos) {
        Box boxWorker=worker.getActualBox();
        int heightWorker=worker.getHeight();
        int counterPos=pos.getCounter();
        isCorrectWorkerMove=0;

        if (boxWorker.reachable(pos)) {
            //first case: the worker moves up a maximum of one level higher;
            //second case: the worker moves down any number of levels lower;
            //third case: the worker moves along the same level.
            if (counterPos-heightWorker==1) {
                if (pos.notWorker() /*&& there is no dome*/) {
                    worker.setHeight(heightWorker++);
                    worker.setActualBox(pos);
                }
                isCorrectWorkerMove=1;
            }
            else if (heightWorker-counterPos>=1) {
                if(pos.notWorker()/*&& there is no dome*/) {
                    worker.setHeight(counterPos);
                    worker.setActualBox(pos);
                }
                isCorrectWorkerMove=2;
            }
            else if (heightWorker==counterPos) {
                if (pos.notWorker()/*&& there is no dome*/)
                {
                    worker.setActualBox(pos);
                }
                isCorrectWorkerMove=3;
            }
            //se si prova un caso in cui non entra in nessuna delle tre condizioni significa che la mossa non è valida
            //in quanto sale o scende di troppi livelli
            //checkWin();
        }
    }

    /**
     * This method implements the basic block move: a chosen worker builds a block or a dome in a position next to him
     * onto a level of any height and this position must be unoccupied (not containing a worker or a dome)
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker builds a building block
     */
    @Override
    public void moveBlock(Worker worker, Box pos) {
        Box boxWorker=worker.getActualBox();

        if (boxWorker.reachable(pos) && pos.notWorker() /*&& there is no dome*/) {
            pos.build();
            isCorrectBlockMove=true;
        }
    }
}
