package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This interface represents the God for the decorator pattern
 */
public interface God {
    /**
     * This attribute is the God name
     */
    String godName="God";
    /**
     * This attribute is the worker move type which tells if the worker moves up, moves down or stays at the same level
     */
    int isCorrectWorkerMove=0;
    /**
     *
     */
    boolean isCorrectBlockMove=false;

    /**
     * This method moves the chosen worker to the new position on the board
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     */
    void moveWorker(Worker worker, Box pos);

    /**
     * This method builds a building block in a position on the board by a chosen worker
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker builds a building block
     */
    void moveBlock(Worker worker, Box pos);
    //void checkWin();
}
