package it.polimi.ingsw.server.model.playerState;

import it.polimi.ingsw.server.model.god.*;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

/**
 * Class of the player's state if he is in the game yet and it's his turn
 */
public class Win extends PlayerState {

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
     * @param playerManager Player manager
     */
    public Win(God myGod, PlayerStateManager playerManager) {
        this.myGod = myGod;
        this.playerManager = playerManager;
    }

    /**
     * Check if the player is in a state of victory
     *
     * @return true if the player's state is Win
     */
    @Override
    public boolean amITheWinner() {
        return true;
    }

}
