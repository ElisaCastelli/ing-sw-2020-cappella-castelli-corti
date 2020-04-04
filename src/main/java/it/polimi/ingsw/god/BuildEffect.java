package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This class represents a god concrete decorator which implements the Gods that have a build move effect
 */
public class BuildEffect extends GodDecorator {

    public BuildEffect ( God newGod ) { super ( newGod ); }

    /**
     *
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     * @param godName The God name card
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    @Override
    public int moveWorker ( Worker worker, Box pos, String godName ) {
        return super.moveWorker ( worker, pos, godName );
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
        if ( godName.equals ( "Atlas" ) ) {
        //Capire come dire che si vuole costruire una cupola e non il blocco giusto della sequenza
            if ( pos.notWorker() ) {
                Box boxWorker = worker.getActualBox();
                int counterPos = pos.getCounter();

                if ( boxWorker.reachable(pos) && pos.notWorker() && counterPos != 4 ) {
                    pos.build(4);
                    return 1;
                }
                return 0;
            }
            else {
                return super.moveBlock ( worker, pos, godName );
            }
        }
        else if ( godName.equals ( "Demeter" ) ) {
            int returnMoveBlock;
            if ( super.getLastWorker() == null ){
                returnMoveBlock = super.moveBlock ( worker, pos, godName );
                if ( returnMoveBlock == 1 ) {
                    super.setLastWorker( worker );
                    super.setLastBox( pos );
                    return 2;
                }
            }
            else {
                Box oldBox = super.getLastBox();
                if ( oldBox != pos ) {
                    super.setLastWorker( null );
                    super.setLastBox( null );
                    return super.moveBlock( worker, pos, godName);
                }
            }
            return 0;
         }
        else if ( godName.equals ( "Hephaestus" ) ) {
            int returnMoveBlock;
            if ( super.getLastWorker() == null ){
                returnMoveBlock = super.moveBlock ( worker, pos, godName );
                if ( returnMoveBlock == 1 && pos.getCounter() <= 2 ) {
                    super.setLastWorker( worker );
                    super.setLastBox( pos );
                    return 2;
                }
                else if ( returnMoveBlock == 1 )
                    return 1;
            }
            else {
                Box oldBox = super.getLastBox();
                Worker oldWorker = super.getLastWorker();
                //Forse il worker potrebbe non essere lo stesso
                if ( oldBox == pos && oldWorker == worker ) {
                    super.setLastWorker( null );
                    super.setLastBox( null );
                    return super.moveBlock( worker, pos, godName);
                }
            }
            return 0;
         }
         /*else if ( godName.equals ( "Prometheus" ) ) {
        //Capire come far costruire prima e dopo la mossa di movimento del worker, se quest'ultimo non è salito di livello
            if (  ) {
                return true;
            }
            else {
                return super.moveBlock ( worker, pos, godName );
            }
         }
         */
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
        return super.checkWin ( initialPos, finalBox, godName );
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
