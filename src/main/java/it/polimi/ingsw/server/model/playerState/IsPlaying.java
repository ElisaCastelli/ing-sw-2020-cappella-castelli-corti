package it.polimi.ingsw.server.model.playerState;

import it.polimi.ingsw.server.model.god.*;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

/**
 * Class of the player's state if he is in the game yet and it's his turn
 */
public class IsPlaying extends PlayerState{

    /**
     * Player's card
     */
    private God myGod;

    /**
     * Object PlayerStateManager to manage the changes of the state
     */
    private final PlayerStateManager playerManager;

    public IsPlaying(God myGod, PlayerStateManager playerManager){
        this.myGod = myGod;
        this.playerManager = playerManager;
    }

    public void setMyGod(God myGod) {
        this.myGod = myGod;
    }

    /**
     * Method to move a worker
     * @param worker to move
     * @param pos to reach
     * @return true if the move works
     */
    @Override
    public boolean moveWorker(Worker worker, Box pos){
        return myGod.moveWorker( worker , pos);
    }

    /**
     * Method to build a block
     * @param pos where build
     * @return true if the building works
     */
    @Override
    public boolean moveBlock(Box pos){
        return myGod.moveBlock(pos);
    }

    /**
     * Method to check the victory
     * @param boxStart starting position
     * @param boxReach position reached
     * @return true if the player has won
     */
    @Override
    public boolean checkWin(Box boxReach, Box boxStart) {
        boolean win = myGod.checkWin(boxStart, boxReach);
        if(win){
            playerManager.goWin();
        }
        return win;
    }

    /**
     * Method to check the possibility of build before move
     * @return true if the player can build before move
     */
    @Override
    public boolean canBuildBeforeWorkerMove() {
        return myGod.canBuildBeforeWorkerMove();
    }

    /**
     * Method to set the possible boxes reachable
     * @param worker to move
     */
    @Override
    public void setPossibleMove( Worker worker){
        myGod.setPossibleMove(worker);
    }

    /**
     * Method to set the possible boxes buildable
     * @param worker moved to build
     */
    @Override
    public void setPossibleBuild( Worker worker){
        myGod.setPossibleBuild(worker);
    }

    /**
     * Method to set the possible block buildable
     * @param indexPossibleBlock of the block to build
     */
    @Override
    public void setIndexPossibleBlock(int indexPossibleBlock) {
        myGod.setIndexPossibleBlock(indexPossibleBlock);
    }
}
