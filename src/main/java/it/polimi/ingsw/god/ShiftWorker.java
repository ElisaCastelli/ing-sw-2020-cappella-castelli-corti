package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This class implements the ability to shift an opponent worker in a new position
 */
public class ShiftWorker extends GodDecorator {

    public ShiftWorker(God newGod) {
        super(newGod);
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
     * This method checks own worker direction compared to the opponent worker so the enemy worker moves along the same
     * direction
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to move
     * @return Position where the opponent worker have to move
     */
    public Box directionControl (Worker worker, Box pos) {
//todo Secondo me, questo controllo conviene farlo anche nella checkPossibleMove così controlliamo lì che non ci sia un worker o una cupola nella nuova posizione del nemico, mentre in moveWorker diamo per scontato che la mossa vada a buon fine
        Box workerPos = worker.getActualBox();
        if ( (workerPos.getColumn() - pos.getColumn() == 1) && (workerPos.getRow() - pos.getRow() == 1) ){
            return pos.getBoxesNextTo().get(0); //va in alto a sinistra
        }
        else if ( (workerPos.getColumn() - pos.getColumn() == 1) && (pos.getRow() - workerPos.getRow() == 1) ){
            return pos.getBoxesNextTo().get(6); //va in basso a sinistra
        }
        else if ( workerPos.getColumn() - pos.getColumn() == 1 ){
            return pos.getBoxesNextTo().get(3); //va a sinistra
        }
        else if ( (pos.getColumn() - workerPos.getColumn() == 1) && (workerPos.getRow() - pos.getRow() == 1) ){
            return pos.getBoxesNextTo().get(2); //va in alto a destra
        }
        else if ( (pos.getColumn() - workerPos.getColumn() == 1) && (pos.getRow() - workerPos.getRow() == 1) ){
            return pos.getBoxesNextTo().get(8); //va in basso a destra
        }
        else if ( pos.getColumn() - workerPos.getColumn() == 1 ){
            return pos.getBoxesNextTo().get(5); //va a destra
        }
        else if ( workerPos.getRow() - pos.getRow() == 1 ){
            return pos.getBoxesNextTo().get(1); //va in alto
        }
        else {
            return pos.getBoxesNextTo().get(7); //va in basso
        }
    }
}
