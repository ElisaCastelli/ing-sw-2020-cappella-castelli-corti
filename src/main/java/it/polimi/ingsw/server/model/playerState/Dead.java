package it.polimi.ingsw.server.model.playerState;

import it.polimi.ingsw.server.model.god.*;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

/**
 * Class of the player's state if he has lost
 */
public class Dead extends PlayerState{

    /**
     * Player's card
     */
    private God myGod;

    /**
     * Object PlayerStateManager to manage the changes of the state
     */
    private final PlayerStateManager playerManager;

    public Dead(God myGod, PlayerStateManager playerManager){
            this.myGod = myGod;
            this.playerManager = playerManager;
    }

    /**
     * Check if the player is in a state of dead
     * @return true if the player's state is Dead
     */
    @Override
    public boolean amIDead() {
        return true;
    }
}
