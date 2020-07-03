package it.polimi.ingsw.server.model.playerState;

import it.polimi.ingsw.server.model.god.*;
import it.polimi.ingsw.server.model.gameComponents.Box;

/**
 * Class of the player's state if he is in the game yet  but is the turn of an opponent
 */
public class IsWaiting extends PlayerState {

    /**
     * Player's card
     */
    private God myGod;

    /**
     * Object PlayerStateManager to manage the changes of the state
     */
    private final PlayerStateManager playerManager;

    /**
     * Constructor of the class
     *
     * @param myGod         God of the player
     * @param playerManager manager of the player
     */
    public IsWaiting(God myGod, PlayerStateManager playerManager) {
        this.myGod = myGod;
        this.playerManager = playerManager;
    }

    /**
     * God setter
     *
     * @param myGod God of he player
     */
    public void setMyGod(God myGod) {
        this.myGod = myGod;
    }

    /**
     * Method to check the victory
     *
     * @param boxStart starting position
     * @param boxReach position reached
     * @return true if the player has won
     */
    @Override
    public boolean checkWin(Box boxReach, Box boxStart) {
        boolean win = myGod.checkWin(boxStart, boxReach);
        if (win) {
            playerManager.goWin();
        }
        return win;
    }
}
