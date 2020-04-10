package it.polimi.ingsw.god;

import it.polimi.ingsw.Worker;
import it.polimi.ingsw.Box;

/**
 * This is God concrete class which implements the basic moves of the workers and of the build
 */
public class BasicGod implements God {

    private String godName;
    private String description;
    private String effect;

    /**
     *
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     * @return
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        Box myBox = worker.getActualBox();
        worker.setActualBox( pos );
        worker.setHeight( pos.getCounter() );
        myBox.clearWorker();
        return true;
    }

    /**
     *
     * @param pos Position on the board where the worker builds a building block
     * @return
     */
    @Override
    public boolean moveBlock(Box pos) {
        pos.build();
        return true;
    }

    /**
     *
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox Position on the board where the worker arrives
     * @return False if the player doesn't win, true if the player wins
     */
    @Override
    public boolean checkWin(Box initialPos, Box finalBox) {
        return finalBox.getCounter() - initialPos.getCounter() == 1 && finalBox.getCounter() == 3;
    }
}
