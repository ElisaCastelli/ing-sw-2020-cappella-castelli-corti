package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import java.util.ArrayList;

/**
 * This class implements the ability to shift an opponent worker in a new position
 */

public class ShiftWorker extends GodDecorator {

    public ShiftWorker(God newGod) {
        super(newGod);
    }

    @Override
    public void setName(String godName) {
        super.setName(godName);
    }

    @Override
    public void setEffect(ArrayList<String> effects) {

    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public ArrayList<String> getEffects() {
        return null;
    }
    /**
     * This method labels a box next to the worker as a reachable box even if there is an opponent worker and checks if the new opponent position belongs to the board, so the worker move surely succeed in case the player chooses this move
     * @param worker Which worker is the check applied
     */
    @Override
    public void setPossibleMove(Worker worker) {
        super.setPossibleMove(worker);

        for (int indexBoxNextTo = 0; indexBoxNextTo < 8; indexBoxNextTo++) {
            Box boxNextTo = worker.getActualBox().getBoxesNextTo().get(indexBoxNextTo);

            if (boxNextTo!=null && !boxNextTo.notWorker() && (worker.getIndexPlayer()!= boxNextTo.getWorker().getIndexPlayer()) && boxNextTo.getCounter() - worker.getHeight() <= 1
                    && (directionControl(worker, boxNextTo) == null || (!directionControl(worker, boxNextTo).notWorker() || directionControl(worker, boxNextTo).getCounter() == 4))){
                boxNextTo.setReachable(false);
            }

            if(boxNextTo!=null){
                System.out.println("è raggiungibile?:"+boxNextTo.isReachable());
            }else{
                System.out.println("è raggiungibile?:"+false);
            }
        }
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
     * This method moves the chosen worker in a position that is occupied by an opponent worker and shift the opponent worker in the same direction of the player move
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return Always true because the move has done successfully
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {
        if ( !pos.notWorker() ){
            Box newEnemyPos = directionControl( worker, pos );
            Worker opponentWorker = pos.getWorker();
            super.moveWorker( worker, pos ); //Scambia i due giocatori adiacenti
            return super.moveWorker( opponentWorker, newEnemyPos); //Sposta il worker nemico nella sua nuova posizione
        }
        return super.moveWorker(worker, pos);
    }

    /**
     * This method builds a building block in a position on the board
     *
     * @param pos Position on the board where the worker builds a building block
     * @return False if you can do another construction; true if the move has done successfully
     */
    @Override
    public boolean moveBlock(Box pos) {
        return super.moveBlock(pos);
    }

    /**
     * This methods checks if the player win
     *
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox   Position on the board where the worker arrives
     * @return False if the player doesn't win; true if the player wins
     */
    @Override
    public boolean checkWin(Box initialPos, Box finalBox) {
        return super.checkWin(initialPos, finalBox);
    }

    /**
     * This method checks own worker direction compared to the opponent worker so the enemy worker moves along the same
     * direction
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to move
     * @return Position where the opponent worker have to move
     */
    public Box directionControl (Worker worker, Box pos) {
        Box workerPos = worker.getActualBox();
        if ( (workerPos.getColumn() - pos.getColumn() == 1) && (workerPos.getRow() - pos.getRow() == 1) ){
            return pos.getBoxesNextTo().get(0); //go up left
        }
        else if ( (workerPos.getColumn() - pos.getColumn() == 1) && (pos.getRow() - workerPos.getRow() == 1) ){
            return pos.getBoxesNextTo().get(5); //go down left
        }
        else if ( workerPos.getColumn() - pos.getColumn() == 1 ){
            return pos.getBoxesNextTo().get(3); //go left
        }
        else if ( (pos.getColumn() - workerPos.getColumn() == 1) && (workerPos.getRow() - pos.getRow() == 1) ){
            return pos.getBoxesNextTo().get(2); //go up right
        }
        else if ( (pos.getColumn() - workerPos.getColumn() == 1) && (pos.getRow() - workerPos.getRow() == 1) ){
            return pos.getBoxesNextTo().get(7); //go down right
        }
        else if ( pos.getColumn() - workerPos.getColumn() == 1 ){
            return pos.getBoxesNextTo().get(4); //go right
        }
        else if ( workerPos.getRow() - pos.getRow() == 1 ){
            return pos.getBoxesNextTo().get(1); //go up
        }
        else {
            return pos.getBoxesNextTo().get(6); //go down
        }
    }
}
