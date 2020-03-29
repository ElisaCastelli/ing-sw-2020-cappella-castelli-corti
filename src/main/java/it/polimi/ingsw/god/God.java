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
     * This method moves the chosen worker to the new position on the board
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    boolean moveWorker(Worker worker, Box pos);

    /**
     * This method builds a building block in a position on the board by a chosen worker
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker builds a building block
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    boolean moveBlock(Worker worker, Box pos);
    //void checkWin();
}
