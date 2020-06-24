package it.polimi.ingsw.server.model.playerState;

import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import it.polimi.ingsw.server.model.god.God;

/**
 * This class manages the changes of player state allowing only some functions based on the state
 */
public class PlayerStateManager {
    /**
     * State used if the player is in the game yet and it's his turn
     */
    private final PlayerState isPlaying;
    /**
     * State used if the player is in the game yet but is the turn of an opponent
     */
    private final PlayerState isWaiting;
    /**
     * State used if the player has lost
     */
    private final PlayerState dead;
    /**
     * State used if the player has won
     */
    private final PlayerState win;
    /**
     * Actual state
     */
    private PlayerState currentState;

    /**
     * Constructor of the class
     *
     * @param myGod is the card of the player
     */
    public PlayerStateManager(God myGod) {
        isPlaying = new IsPlaying(myGod, this);
        isWaiting = new IsWaiting(myGod, this);
        dead = new Dead(myGod, this);
        win = new Win(myGod, this);
        currentState = isWaiting;
    }

    /**
     * Current state setter
     *
     * @param newState new state
     */

    public void setCurrentState(PlayerState newState) {
        currentState = newState;
    }

    /**
     * Current state getter
     *
     * @return current state
     */

    public PlayerState getCurrentState() {
        return currentState;
    }

    /**
     * Is playing getter
     *
     * @return true if is playing
     */
    public boolean isPlaying() {
        return currentState == isPlaying;
    }

    /**
     * God setter
     *
     * @param myGod God the player
     */

    public void setMyGod(God myGod) {
        currentState.setMyGod(myGod);
    }

    /**
     * It changes the state in IsPlaying
     */
    public void goPlaying() {
        if (currentState == isWaiting) {
            currentState = isPlaying;
        }
    }

    /**
     * It changes the state in IsWaiting
     */
    public void goWaiting() {
        if (currentState == isPlaying) {
            currentState = isWaiting;
        }
    }

    /**
     * It changes the state in Dead
     */
    public void goDead() {
        if (currentState == isPlaying || currentState == isWaiting) {
            currentState = dead;
        }
    }

    /**
     * It changes the state in Win
     */
    public void goWin() {
        if (currentState == isPlaying) {
            currentState = win;
        }
    }

    /**
     * Method to move a worker
     *
     * @param worker to move
     * @param pos    to reach
     * @return true if the move works
     */
    public boolean moveWorker(Worker worker, Box pos) {
        return currentState.moveWorker(worker, pos);
    }

    /**
     * Method to build a block
     *
     * @param pos where build
     * @return true if the building works
     */
    public boolean moveBlock(Box pos) {
        return currentState.moveBlock(pos);
    }

    /**
     * Method to check the victory
     *
     * @param boxStart starting position
     * @param boxReach position reached
     * @return true if the player has won
     */
    public boolean checkWin(Box boxReach, Box boxStart) {
        return currentState.checkWin(boxReach, boxStart);
    }

    /**
     * Method to check the possibility of build before move
     *
     * @return true if the player can build before move
     */
    public boolean canBuildBeforeWorkerMove() {
        return currentState.canBuildBeforeWorkerMove();
    }

    ;

    /**
     * Method to set the possible boxes reachable
     *
     * @param worker to move
     * @return true if the worker can move
     */
    public boolean setPossibleMove(Worker worker) {
        return currentState.setPossibleMove(worker);
    }

    /**
     * Method to set the possible boxes buildable
     *
     * @param worker moved to build
     */
    public void setPossibleBuild(Worker worker) {
        currentState.setPossibleBuild(worker);
    }

    /**
     * Method to set the possible block buildable
     *
     * @param indexPossibleBlock of the block to build
     */
    public void setIndexPossibleBlock(int indexPossibleBlock) {
        currentState.setIndexPossibleBlock(indexPossibleBlock);
    }

    /**
     * Check if the player is in a state of victory
     *
     * @return true if the player's state is Win
     */
    public boolean amITheWinner() {
        return currentState.amITheWinner();
    }

    /**
     * Check if the player is in a state of dead
     *
     * @return true if the player's state is Dead
     */
    public boolean amIDead() {
        return currentState.amIDead();
    }
}
