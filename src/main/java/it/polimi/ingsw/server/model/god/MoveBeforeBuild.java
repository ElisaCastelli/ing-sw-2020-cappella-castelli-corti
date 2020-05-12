package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import java.util.ArrayList;
public class MoveBeforeBuild extends GodDecorator {

    private boolean isWorkerMoved;

    public MoveBeforeBuild (God newGod) {
        super(newGod);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String godName) {
        super.setName(godName);
    }

    @Override
    public void setEffect(ArrayList<String> effects) {

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
     * This method tells which positions can get built by a worker
     * @param worker Which worker is the check applied
     */
    @Override
    public void setPossibleBuild(Worker worker) {
        super.setPossibleBuild(worker);
    }

    /**
     * This method moves the chosen worker to the new position on the board
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return False if you can do another move; true if the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        isWorkerMoved = true;
        return super.moveWorker(worker, pos);
    }

    /**
     * This method does not allow the moveBlock if it is called before the moveWorker
     * @param pos Position on the board where the worker builds a building block
     * @return False if you can do another construction; true if the move has done successfully
     */
    @Override
    public boolean moveBlock(Box pos) {
        if (isWorkerMoved) {
            if ( super.moveBlock(pos) ) {
                isWorkerMoved = false;
                return true;
            }
            else{
                return false;
            }
        }
        return true;
    }

    /**
     * This method checks if the player wins
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox   Position on the board where the worker arrives
     * @return False if the player doesn't win; true if the player wins
     */
    @Override
    public boolean checkWin(Box initialPos, Box finalBox) {
        return super.checkWin(initialPos, finalBox);
    }
}
