package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;

/**
 * This class implements the ability to win if the worker moves down two or more levels
 */
public class DownTwoOrMoreLevelsWin extends GodDecorator {

    public DownTwoOrMoreLevelsWin(God newGod) {
        super(newGod);
    }

    /**
     * This methods checks if the worker moves down two or more levels, the player wins
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox   Position on the board where the worker arrives
     * @return False if the player doesn't win; true if the player wins
     */
    @Override
    public boolean checkWin(Box initialPos, Box finalBox) {
        if ( initialPos.getCounter() - finalBox.getCounter() >= 2)
            return true;
        return super.checkWin(initialPos, finalBox);
    }
}
