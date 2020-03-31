package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This abstract class represents the decorator of the Decorator Pattern which handles the different Gods' abilities
 */
public abstract class GodDecorator implements God {
    protected God newGod;
    public GodDecorator ( God newGod ) {
        this.newGod = newGod;
    }

    /**
     *
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     * @param godName The God name card
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    @Override
    public boolean moveWorker ( Worker worker, Box pos, String godName ) {
        return this.newGod.moveWorker ( worker, pos, godName );
    }

    /**
     *
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker builds a building block
     * @param godName The God name card
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    @Override
    public boolean moveBlock ( Worker worker, Box pos, String godName ) {
        return this.newGod.moveBlock ( worker, pos, godName );
    }

    /**
     *
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox Position on the board where the worker arrives
     * @param godName The God name card
     * @return
     */
    @Override
    public boolean checkWin ( Box initialPos, Box finalBox, String godName ) {
        return false;
    }
}
