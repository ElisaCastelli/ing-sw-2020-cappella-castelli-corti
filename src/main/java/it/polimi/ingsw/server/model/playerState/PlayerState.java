package it.polimi.ingsw.server.model.playerState;

import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import it.polimi.ingsw.server.model.god.God;

/**
 * Abstract class with common methods for the different states
 */
public abstract class PlayerState {
    public void setMyGod(God myGod){}

    /**
     * Method to move a worker
     * @param worker to move
     * @param pos to reach
     * @return true if the move works
     */
    public boolean moveWorker (Worker worker, Box pos){ return false;}

    /**
     * Method to build a block
     * @param pos where build
     * @return true if the building works
     */
    public boolean moveBlock(Box pos){ return false;}

    /**
     * Method to check the victory
     * @param boxStart starting position
     * @param boxReach position reached
     * @return true if the player has won
     */
    public boolean checkWin(Box boxReach, Box boxStart){return false;}

    /**
     * Method to check the possibility of build before move
     * @return true if the player can build before move
     */
    public boolean canBuildBeforeWorkerMove(){ return false; }

    /**
     * Method to set the possible boxes reachable
     * @param worker to move
     */
    public boolean setPossibleMove( Worker worker ){return false; }

    /**
     * Method to set the possible boxes buildable
     * @param worker moved to build
     */
    public void setPossibleBuild( Worker worker){ }

    /**
     * Method to set the possible block buildable
     * @param indexPossibleBlock of the block to build
     */
    public void setIndexPossibleBlock( int indexPossibleBlock){}

    /**
     * Check if the player is in a state of victory
     * @return true if the player's state is Win
     */
    public boolean amITheWinner(){return false;}

    /**
     * Check if the player is in a state of dead
     * @return true if the player's state is Dead
     */
    public boolean amIDead() {
        return false;
    }
}
