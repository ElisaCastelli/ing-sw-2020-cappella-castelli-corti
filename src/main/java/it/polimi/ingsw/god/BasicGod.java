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

    public void setGodName(String godName) {
        this.godName = godName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    /**
     * This method is a basic check: it verifies if the positions next to the worker are unoccupied (no worker and no dome) and if is possible to move up a maximum of one level higher.
     * @param worker Which worker is the check applied
     * @return False if there are no positions that can get reached, otherwise return always true
     */
    @Override
    public void setPossibleMove(Worker worker) {
        for (int indexBoxNextTo = 0; indexBoxNextTo < 8; indexBoxNextTo++) {
            Box boxNextTo = worker.getActualBox().getBoxesNextTo().get(indexBoxNextTo);
            if ( boxNextTo.notWorker() && boxNextTo.getCounter()!= 4 && (boxNextTo.getCounter() - worker.getHeight() <= 1) ){
                boxNextTo.setReachable(true);
            }
        }
    }

    /**
     * @param worker
     */
    @Override
    public void setPossibleBuild(Worker worker) {
        for (int indexBoxNextTo = 0; indexBoxNextTo < 8; indexBoxNextTo++) {
            Box boxNextTo = worker.getActualBox().getBoxesNextTo().get(indexBoxNextTo);
            if (boxNextTo.getCounter() != 4 && boxNextTo.notWorker()) {
                boxNextTo.setReachable(true);
            }
        }
    }

    /**
     * This method implements the worker move from its start position to its final position
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     * @return Always true because the move succeeded
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        Box myBox = worker.getActualBox();
        worker.setActualBox( pos );
        worker.setHeight( pos.getCounter() );
        pos.setWorker( worker );
        myBox.clearWorker();
        return true;
    }

    /**
     * This method implements the basic block move which builds the correct block in a given position
     * @param pos Position on the board where the worker builds a building block
     * @return Always true because the move succeded
     */
    @Override
    public boolean moveBlock(Box pos) {
        pos.build();
        return true;
    }

    /**
     * This method implements the basic winning rule: if the worker moves up a maximum of one level and it is level 3, the player wins.
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox Position on the board where the worker arrives
     * @return False if the player doesn't win, true if the player wins
     */
    @Override
    public boolean checkWin(Box initialPos, Box finalBox) {
        return finalBox.getCounter() - initialPos.getCounter() == 1 && finalBox.getCounter() == 3;
    }
}
