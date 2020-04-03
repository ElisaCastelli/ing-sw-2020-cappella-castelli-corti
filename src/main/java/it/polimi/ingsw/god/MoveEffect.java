package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This class represents a god concrete decorator which implements the Gods that have a worker move effect
 */
public class MoveEffect extends GodDecorator {

    public MoveEffect ( God newGod ) {
        super ( newGod );
    }

    /**
     *
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     * @param godName The God name card
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    @Override
    public int moveWorker ( Worker worker, Box pos, String godName ) {

        if ( godName.equals ( "Apollo" ) ) {
            if ( !pos.notWorker() ) {
                if ( switchWorkers ( worker, pos ) )
                    return 1;
                return 0;
            }
            else {
                return super.moveWorker ( worker, pos, godName );
            }
        }
        else if ( godName.equals ( "Artemis" ) ) {
            if ( super.getLastWorker() == null ){
                if ( super.moveWorker ( worker, pos, godName ) == 1 ) {
                    super.setLastWorker( worker );
                    super.setLastBox( worker.getActualBox() );
                    return 2;
                }
            }
            else {
                Box oldBox = super.getLastBox();
                Worker oldWorker = super.getLastWorker();
                if ( oldBox != pos && oldWorker == worker ) {
                    return super.moveWorker( worker, pos, godName);
                }
            }
            return 0;
        }
        else if ( godName.equals ( "Athena" ) ) {
            if ( pos.getCounter() - worker.getHeight() == 1 )
            {//Capire come tenere conto che una volta che atena sale gli altri giocatori non possono salire durante il loro turno
                worker.setHeight ( pos.getCounter() );
                worker.setActualBox ( pos );
                return 1; //Sbagliato (forse)
            }
            else {
                return moveWorker ( worker, pos, godName );
            }
        }
        else if ( godName.equals ( "Minotaur" ) ) {
            if ( !pos.notWorker() ) {
                if ( shiftWorker ( worker, pos ) )
                    return 1;
                return 0;
            }
            else
            {
                return super.moveWorker ( worker, pos, godName );
            }
        }
        return 0;
    }

    /**
     * This method implements the ability to switch the position with an enemy worker if it is next to an own worker
     * (Apollo ability)
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    public boolean switchWorkers ( Worker worker, Box pos ) {
        int counterPos = pos.getCounter();
        Box workerBox = worker.getActualBox();

        if ( workerBox.reachable ( pos ) && counterPos != 4 ) {
            int heightWorker = worker.getHeight();
            Worker enemyWorker = pos.getWorker();

            if ( super.upDownOrStayAtTheSameLevel ( counterPos, heightWorker ) == 1 ) {
                worker.setActualBox ( pos );
                worker.setHeight ( heightWorker + 1 );
                enemyWorker.setActualBox ( workerBox );
                enemyWorker.setHeight ( counterPos - 1 );
                return true;
            }
            else if ( super.upDownOrStayAtTheSameLevel ( counterPos, heightWorker ) == 2 ){
                worker.setActualBox ( pos );
                worker.setHeight ( counterPos );
                enemyWorker.setActualBox ( workerBox );
                enemyWorker.setHeight ( heightWorker );
                return true;
            }
            else if ( super.upDownOrStayAtTheSameLevel ( counterPos, heightWorker ) == 3 ) {
                worker.setActualBox ( pos );
                enemyWorker.setActualBox ( workerBox );
                return true;
            }
        }
        return false;
    }

    /**
     * This method implements the ability to move the worker into an enemy worker's space, if the opponent worker can be
     * forced one space straight backwards to an unoccupied space at any level (Minotaur ability)
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    public boolean shiftWorker ( Worker worker, Box pos ) {
        int counterPos = pos.getCounter();
        Box workerBox = worker.getActualBox();
        Box newEnemyPosition;

        if ( workerBox.reachable( pos ) && counterPos != 4 ) {
            int heightWorker=worker.getHeight();
            Worker enemyWorker = pos.getWorker();

            if ( super.upDownOrStayAtTheSameLevel ( counterPos, heightWorker ) == 1 ) {
                newEnemyPosition = directionControl ( worker, pos );
                if ( newEnemyPosition.getRow() >= 0 && newEnemyPosition.getRow() <= 4 && newEnemyPosition.getColumn() >=0 && newEnemyPosition.getColumn() <= 4 ) {
                    if ( newEnemyPosition.notWorker() && newEnemyPosition.getCounter() != 4 ) {
                        enemyWorker.setActualBox ( newEnemyPosition );
                        enemyWorker.setHeight ( newEnemyPosition.getCounter() );
                        worker.setActualBox ( pos );
                        worker.setHeight ( heightWorker + 1 );
                        return true;
                    }
                }
                return false;
            }
            else if ( super.upDownOrStayAtTheSameLevel ( counterPos, heightWorker ) == 2 ) {
                newEnemyPosition = directionControl ( worker , pos );
                if ( newEnemyPosition.getRow() >= 0 && newEnemyPosition.getRow() <= 4 && newEnemyPosition.getColumn() >= 0 && newEnemyPosition.getColumn() <= 4 ) {
                    if ( newEnemyPosition.notWorker() && newEnemyPosition.getCounter() !=4 ) {
                        enemyWorker.setActualBox ( newEnemyPosition );
                        enemyWorker.setHeight ( newEnemyPosition.getCounter() );
                        worker.setActualBox ( pos );
                        worker.setHeight ( counterPos );
                        return true;
                    }
                }
                return false;
            }
            else if ( super.upDownOrStayAtTheSameLevel ( counterPos, heightWorker ) == 3 ) {
                newEnemyPosition = directionControl ( worker, pos );
                if ( newEnemyPosition.getRow() >= 0 && newEnemyPosition.getRow() <= 4 && newEnemyPosition.getColumn() >= 0 && newEnemyPosition.getColumn() <= 4 ) {
                    if ( newEnemyPosition.notWorker() && newEnemyPosition.getCounter() != 4) {
                        enemyWorker.setActualBox ( newEnemyPosition );
                        worker.setActualBox ( pos );
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
     * @param godName The God name card
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    @Override
    public int moveBlock ( Worker worker, Box pos, String godName ) {
        return super.moveBlock ( worker, pos, godName );
    }

    /**
     * This method calls the basic checkWin in the Gods class
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox Position on the board where the worker arrives
     * @param godName The God name card
     * @return False if the player doesn't win; true if the player wins
     */
    @Override
    public boolean checkWin ( Box initialPos, Box finalBox, String godName ) {
        return super.checkWin ( initialPos, finalBox, godName );
    }

    /**
     * This method controls the own worker direction so the enemy worker moves at the same direction
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker builds a building block
     * @return Box presenting the new enemy worker position
     */
    public Box directionControl ( Worker worker, Box pos ) {
        Box workerPos = worker.getActualBox();
        int newColumn = pos.getColumn();
        int newRow = pos.getRow();
        if ( pos.getColumn() - workerPos.getColumn() == 1 ) { //va a destra
            newColumn = newColumn + 1;
        }
        else if ( workerPos.getColumn() - pos.getColumn() == 1 ) {//va a sinistra
            newColumn = newColumn - 1;
        }

        if ( pos.getRow() - workerPos.getRow() == 1 ) {//va sotto
            newRow = newRow + 1;
        }
        else if ( workerPos.getRow() - pos.getRow() == 1 ) {//va sopra
            newRow = newRow - 1;
        }
        //pos.setRow(newRow);
        //pos.setColumn(newColumn);
        return pos;
    }

    @Override
    public int upDownOrStayAtTheSameLevel(int counterBuilding, int counterWorker) {
        return super.upDownOrStayAtTheSameLevel(counterBuilding, counterWorker);
    }

    @Override
    public String getGodName() { return null; }
    @Override
    public void setGodName(String newName) {}
    @Override
    public String getDescription() { return null; }
    @Override
    public void setDescription(String newDescription) {}
    @Override
    public String getEffect() { return null; }
    @Override
    public void setEffect(String effect) {}
    @Override
    public int hashCode() { return super.hashCode(); }
    @Override
    public Worker getLastWorker() { return null; }
    @Override
    public void setLastWorker(Worker lastWorker) {}
    @Override
    public Box getLastBox() { return null; }
    @Override
    public void setLastBox(Box lastBox) {}
    @Override
    public String getLastGod() { return null; }
    @Override
    public void setLastGod(String lastGod) {}
}
