package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This class represents a god concrete decorator which implements the Gods that have a win move effect
 */
public class WinCondition extends GodDecorator {

    public WinCondition ( God newGod ) { super ( newGod ); }

    /**
     *
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     * @param godName The God name card
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    @Override
    public int moveWorker ( Worker worker, Box pos, String godName ) {
        if ( godName.equals ( "Pan" ) ) {
            if ( worker.getHeight() - pos.getCounter() >= 2 )
            {
                Box boxWorker = worker.getActualBox();
                int counterPos = pos.getCounter();

                if ( boxWorker.reachable(pos) && pos.notWorker() && counterPos != 4 ) {
                    worker.setHeight ( counterPos );
                    worker.setActualBox ( pos );
                    return 1;
                }
            }
            else
                return super.moveWorker ( worker, pos, godName );
        }
        return 0;
    }

    /**
     *
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
     *
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox Position on the board where the worker arrives
     * @param godName The God name card
     * @return False if the player doesn't win; true if the player wins
     */
    @Override
    public boolean checkWin ( Box initialPos, Box finalBox, String godName ) {
        if( godName.equals ( "Pan" ) ) {
            if ( initialPos.getCounter() - finalBox.getCounter() >= 2 )
                return true;
            else
                return super.checkWin ( initialPos, finalBox, godName );
        }
        return false;
    }

    @Override
    public int upDownOrStayAtTheSameLevel(int counterBuilding, int counterWorker) {
        return super.upDownOrStayAtTheSameLevel(counterBuilding, counterWorker);
    }

    @Override
    public String getGodName() {
        return null;
    }
    @Override
    public void setGodName(String newName) {

    }
    @Override
    public String getDescription() {
        return null;
    }
    @Override
    public void setDescription(String newDescription) {

    }
    @Override
    public String getEffect() {
        return null;
    }
    @Override
    public void setEffect(String effect) {

    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    @Override
    public Worker getLastWorker() {
        return null;
    }
    @Override
    public void setLastWorker(Worker lastWorker) {

    }
    @Override
    public Box getLastBox() {
        return null;
    }
    @Override
    public void setLastBox(Box lastBox) {

    }
    @Override
    public String getLastGod() {
        return null;
    }
    @Override
    public void setLastGod(String lastGod) {

    }
}
