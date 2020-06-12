package it.polimi.ingsw.server.model.playerState;

import it.polimi.ingsw.server.model.god.*;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

/**
 * Class of the player's state if he is in the game yet  but is the turn of an opponent
 */
public class IsWaiting extends PlayerState{

    /**
     * Player's card
     */
    private God myGod;

    /**
     * Object PlayerStateManager to manage the changes of the state
     */
    private final PlayerStateManager playerManager;

    public IsWaiting(God myGod, PlayerStateManager playerManager){
        this.myGod=myGod;
        this.playerManager=playerManager;
    }
}
