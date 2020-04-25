package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Box;
import it.polimi.ingsw.model.Worker;

import java.util.ArrayList;

/**
 * This class implements the ability to build twice in two different positions
 */

public class OtherPositionToBuild extends MoveTwice {

    public OtherPositionToBuild(God newGod) {
        super(newGod);
    }

    @Override
    public void setName(String godName) {

    }

    @Override
    public void setEffect(ArrayList<String> effects) {

    }

    @Override
    public String getName() {
        return null;
    }
    @Override
    public ArrayList<String> getEffects() {
        return null;
    }
    /**
     * This method tells which positions can get reached by a worker
     * @param worker Which worker is the check applied
     */
    @Override
    public void setPossibleMove(Worker worker) {
        super.setPossibleMove(worker);
    }

    /**
     * This method tells which positions can get built by a worker: during the second build move, the worker cannot build
     * @param worker Which worker is the check applied
     */
    @Override
    public void setPossibleBuild(Worker worker) {
        super.setPossibleBuild(worker);
        if (!super.firstTime) {
            for (int indexBoxNextTo = 0; indexBoxNextTo < 8; indexBoxNextTo++) {
                Box boxNextTo = worker.getActualBox().getBoxesNextTo().get(indexBoxNextTo);
                if (boxNextTo!=null && boxNextTo.getCounter() != 4 && boxNextTo.notWorker() && super.samePosition(boxNextTo)) {
                    boxNextTo.setReachable(false);
                }
                if(boxNextTo!=null){
                    System.out.println("Posso costruire?:"+boxNextTo.isReachable());
                }else{
                    System.out.println("Posso costruire?:"+false);
                }
            }
        }
    }

    /**
     * This method moves the chosen worker to the new position on the board
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return False if you can do another move; true if the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        return super.moveWorker(worker, pos);
    }

    /**
     * This method builds a building block in a position on the board and another building block in another position
     * @param pos Position on the board where the worker builds a building block
     * @return False if you can do another construction; true if the move has done successfully
     */
    @Override
    public boolean moveBlock(Box pos) {
        if ( !super.samePosition( pos ))
            return super.moveTwice( pos );
        return false;
    }

    /**
     * This methods checks if the player wins
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox   Position on the board where the worker arrives
     * @return False if the player doesn't win; true if the player wins
     */
    @Override
    public boolean checkWin(Box initialPos, Box finalBox) {
        return super.checkWin(initialPos, finalBox);
    }
}
