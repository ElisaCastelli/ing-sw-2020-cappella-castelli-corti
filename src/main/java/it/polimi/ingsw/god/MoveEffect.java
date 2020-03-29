package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This class represents a god concrete decorator which implements the Gods that have a worker move effect
 */
public class MoveEffect extends GodDecorator {

    public MoveEffect (God newGod) {
        super(newGod);
    }
    public Gods actualGod;

    /**
     *
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos) {

        if(actualGod.getGodName().equals("Apollo")) {
            if(!pos.notWorker()) {
                return switchWorkers(worker, pos);
            }
            else {
                return super.moveWorker(worker, pos);
            }
        }
        else if(actualGod.getGodName().equals("Artemis")) {
            /*if(){ Capire come fare la doppia mossa sullo stesso worker
                return moveWorker(worker, pos);
            }
            else {
                return moveTwice(worker, pos);
            }*/

        }
        else if (actualGod.getGodName().equals("Minotaur")) {
            if(!pos.notWorker()) {
                return shiftWorker(worker, pos);
            }
            else
            {
                return super.moveWorker(worker, pos);
            }
        }
        return false;
    }

    /**
     * This method implements the ability to switch the position with an enemy worker if it is next to an own worker
     * (Apollo ability)
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    public boolean switchWorkers (Worker worker, Box pos) {
        int counterPos=pos.getCounter();
        Box workerBox=worker.getActualBox();
        if(workerBox.reachable(pos) && counterPos!=4) {
            int heightWorker=worker.getHeight();

            if(actualGod.upDownOrStayAtTheSameLevel(counterPos,heightWorker)==1) {
                worker.setActualBox(pos);
                worker.setHeight(heightWorker+1);
                //TO DO metodo o qualcosa per selezionare la pedina dell'avversario in modo tale da fare lo switch

                return true;
            }
            else if (actualGod.upDownOrStayAtTheSameLevel(counterPos,heightWorker)==2){
                worker.setActualBox(pos);
                worker.setHeight(counterPos);

                return true;
            }
            else if (actualGod.upDownOrStayAtTheSameLevel(counterPos,heightWorker)==3) {
                worker.setActualBox(pos);

                return true;
            }
        }
        return false;
    }

    /**
     * This method implements the ability to move the worker twice, but it must not move back to the initial place
     * (Artemis ability)
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    public boolean moveTwice (Worker worker, Box pos) {
        return false;
    }

    /**
     * This method implements the ability to move the worker into an enemy worker's space, if the opponent worker can be
     * forced one space straight backwards to an unoccupied space at any level (Minotaur ability)
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    public boolean shiftWorker(Worker worker, Box pos) {
        int counterPos=pos.getCounter();
        Box workerBox=worker.getActualBox();
        Box newEnemyPosition; //Devo fare new box?
        if(workerBox.reachable(pos) && counterPos!=4) {
            int heightWorker=worker.getHeight();

            if(actualGod.upDownOrStayAtTheSameLevel(counterPos,heightWorker)==1) {
                newEnemyPosition=directionControl(worker, pos);
                if (newEnemyPosition.getRow()>=0 && newEnemyPosition.getRow()<=4 && newEnemyPosition.getColumn()>=0 && newEnemyPosition.getColumn()<=4) {
                    if (newEnemyPosition.notWorker() && newEnemyPosition.getCounter()!=4) {
                        //TO DO metodo o qualcosa per selezionare la pedina dell'avversario in modo tale da spostarla in newEnemyPosition
                        //Settare poi tutti i counter
                        worker.setActualBox(pos);
                        worker.setHeight(heightWorker+1);

                        return true;
                    }
                }
                return false;
            }
            else if (actualGod.upDownOrStayAtTheSameLevel(counterPos,heightWorker)==2){
                newEnemyPosition=directionControl(worker, pos);
                if (newEnemyPosition.getRow()>=0 && newEnemyPosition.getRow()<=4 && newEnemyPosition.getColumn()>=0 && newEnemyPosition.getColumn()<=4) {
                    if (newEnemyPosition.notWorker() && newEnemyPosition.getCounter()!=4) {
                        //TO DO metodo o qualcosa per selezionare la pedina dell'avversario in modo tale da spostarla in newEnemyPosition
                        //Settare poi tutti i counter
                        worker.setActualBox(pos);
                        worker.setHeight(counterPos);

                        return true;
                    }
                }
                return false;
            }
            else if (actualGod.upDownOrStayAtTheSameLevel(counterPos,heightWorker)==3) {
                newEnemyPosition=directionControl(worker, pos);
                if (newEnemyPosition.getRow()>=0 && newEnemyPosition.getRow()<=4 && newEnemyPosition.getColumn()>=0 && newEnemyPosition.getColumn()<=4) {
                    if (newEnemyPosition.notWorker() && newEnemyPosition.getCounter()!=4) {
                        //TO DO metodo o qualcosa per selezionare la pedina dell'avversario in modo tale da spostarla in newEnemyPosition
                        //Settare poi tutti i counter
                        worker.setActualBox(pos);

                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    /**
     * This method calls the basic moveBlock in the Gods class
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker builds a building block
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    @Override
    public boolean moveBlock(Worker worker, Box pos) {
        return super.moveBlock(worker, pos);
    }

    /**
     * This method controls the own worker direction so the enemy worker moves at the same direction
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker builds a building block
     * @return Box presenting the new enemy worker position
     */
    public Box directionControl(Worker worker, Box pos) {
        Box workerPos=worker.getActualBox();
        int newColumn=pos.getColumn();
        int newRow=pos.getRow();
        if (pos.getColumn()-workerPos.getColumn()==1) { //va a destra
            newColumn=newColumn+1;
        }
        else if (workerPos.getColumn()-pos.getColumn()==1) {//va a sinistra
            newColumn=newColumn-1;
        }

        if (pos.getRow()-workerPos.getRow()==1) {//va sotto
            newRow=newRow+1;
        }
        else if (workerPos.getRow()-pos.getRow()==1) {//va sopra
            newRow=newRow-1;
        }
        pos.setRow(newRow);
        pos.setColumn(newColumn);
        return pos;
    }
}
