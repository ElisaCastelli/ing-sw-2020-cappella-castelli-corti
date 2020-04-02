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
    public boolean moveWorker ( Worker worker, Box pos, String godName ) {
        return super.moveWorker ( worker, pos, godName );
    }

    /**
     * This method calls the basic moveBlock in the Gods class
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker builds a building block
     * @param godName The God name card
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    @Override
    public boolean moveBlock ( Worker worker, Box pos, String godName ) {
        /*if ( godName.equals ( "Atlas" ) ) {
        //Creare un metodo che mi faccia costruire una cupola ovunque voglia e capire come dire che si vuole costruire una cupola e non il blocco giusto della sequenza
            if (  ) {
                return true;
            }
            else {
                return super.moveBlock ( worker, pos, godName );
            }
        }
        else if ( godName.equals ( "Demeter" ) ) {
        //Capire come costruire due volte, ma non nello stesso posto
            if (  ) {
                return true;
            }
            else {
                return super.moveBlock ( worker, pos, godName );
            }
         }
         else if ( godName.equals ( "Hephaestus" ) ) {
        //Capire come costruire due blocchi nella stessa casella (1-2 o 2-3 livello, no dome)
            if (  ) {
                return true;
            }
            else {
                return super.moveBlock ( worker, pos, godName );
            }
         }
         else if ( godName.equals ( "Prometheus" ) ) {
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
}
