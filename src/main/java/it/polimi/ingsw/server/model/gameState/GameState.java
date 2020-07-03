package it.polimi.ingsw.server.model.gameState;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Player;

import java.util.ArrayList;

/**
 * Abstract class with common methods for the different states
 */
public abstract class GameState {
    /**
     * Winner setter
     *
     * @param winner integer of the winner
     */

    public void setWinner(int winner) { }

    /**
     * Winner getter
     *
     * @return integer of the winner
     */
    public int getWinner() {
        return -1;
    }

    /**
     * Players dead getter
     *
     * @return array of players
     */
    public ArrayList<Player> getPlayersDead() {
        return null;
    }

    /**
     * Method used to check if the player can build before move
     *
     * @param indexPlayer of the player
     * @return true if it's possible to build before move
     */
    public boolean canBuildBeforeWorkerMove(int indexPlayer) {
        return false;
    }


    /**
     * Method used to check if the player can move
     *
     * @param indexPlayer of the player
     * @return true if it's possible to  move
     */
    public boolean canMove(int indexPlayer) {
        return false;
    }

    /**
     * Method used to check if the player can  move inn special turns
     *
     * @param indexPlayer of the player
     * @param indexWorker of the worker to move
     * @return true if it's possible to  move
     */
    public boolean canMoveSpecialTurn(int indexPlayer, int indexWorker) {
        return false;
    }

    /**
     * Method used to set the possible reachable boxes
     *
     * @param indexPlayer of the player
     * @param indexWorker of the worker to move
     * @return true if is reachable
     */
    public boolean setBoxReachable(int indexPlayer, int indexWorker) {
        return false;
    }

    /**
     * Method to move a worker
     *
     * @param indexPlayer of the player
     * @param indexWorker of the worker to move
     * @param row         to reach with a move
     * @param column      to reach with a move
     * @param board       game field
     * @return true if the move works
     */
    public boolean movePlayer(int indexPlayer, int indexWorker, int row, int column, Board board) {
        return false;
    }

    /**
     * Method used to check if the player can build
     *
     * @param indexPlayer of the player
     * @param indexWorker of the worker moved before build
     * @return true if it's possible to  build
     */
    public boolean canBuild(int indexPlayer, int indexWorker) {
        return false;
    }

    /**
     * Method used to set the possible buildable boxes
     *
     * @param indexPlayer of the player
     * @param indexWorker of the worker moved before build
     * @return true if is reachable
     */
    public boolean setBoxBuilding(int indexPlayer, int indexWorker) {
        return false;
    }

    /**
     * Method to build a block
     *
     * @param indexPlayer of the player
     * @param indexWorker of the worker to move
     * @param row         to reach with a move
     * @param column      to reach with a move
     * @param board       game field
     * @return true if the move works
     */
    public boolean buildBlock(int indexPlayer, int indexWorker, int row, int column, Board board) {
        return false;
    }

    /**
     * Method to set the possible block buildable
     *
     * @param indexPlayer        of the player
     * @param indexPossibleBlock of the block to build
     */
    public void setIndexPossibleBlock(int indexPlayer, int indexPossibleBlock) {
    }

    /**
     * Method to check the victory
     *
     * @param indexPlayer of the player
     * @param startBox    starting position
     * @param indexWorker worker moved
     * @return true if the player has won
     */
    public boolean checkWin(int indexPlayer, Box startBox, int indexWorker) {
        return false;
    }

    /**
     * Method to check the victory after build
     *
     * @return true if the player has won
     */
    public boolean checkWinAfterBuild() {
        return false;
    }

    /**
     * Method to set a dead player
     *
     * @param indexPlayer of the player
     */
    public void setDeadPlayer(int indexPlayer) {
    }
}
