package it.polimi.ingsw.server.model.gameState;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Player;

import java.util.ArrayList;

/**
 * This class manages the changes of game state allowing only some functions based on the state
 */
public class GameStateManager {

    /**
     * State used if the game is going
     */
    private final GameState going;

    /**
     * State used if the game is over
     */
    private final GameState end;

    /**
     * Actual game state
     */
    private GameState currentState;

    /**
     * Constructor of the class
     *
     * @param players     array of players
     * @param playersDead array of players dead
     */

    public GameStateManager(ArrayList<Player> players, ArrayList<Player> playersDead) {
        going = new GoingState(players, playersDead, this);
        end = new EndState();
        currentState = null;
    }

    /**
     * Current state getter
     *
     * @return current state
     */

    public GameState getCurrentState() {
        return currentState;
    }

    /**
     * Method to start the game and setting the isGoing state in currentState
     */
    public void start() {
        currentState = going;
    }

    /**
     * Method used to check if the player can build before move
     *
     * @param indexPlayer of the player
     * @return true if it's possible to build before move
     */
    public boolean canBuildBeforeWorkerMove(int indexPlayer) {
        return currentState.canBuildBeforeWorkerMove(indexPlayer);
    }

    /**
     * Method used to check if the player can move
     *
     * @param indexPlayer of the player
     * @return true if it's possible to  move
     */
    public boolean canMove(int indexPlayer) {
        return currentState.canMove(indexPlayer);
    }

    /**
     * Method used to check if the player can  move inn special turns
     *
     * @param indexPlayer of the player
     * @param indexWorker of the worker to move
     * @return true if it's possible to  move
     */
    public boolean canMoveSpecialTurn(int indexPlayer, int indexWorker) {
        return currentState.canMoveSpecialTurn(indexPlayer, indexWorker);
    }

    /**
     * Method used to set the possible reachable boxes
     *
     * @param indexPlayer of the player
     * @param indexWorker of the worker to move
     * @return true if is reachable
     */
    public boolean setBoxReachable(int indexPlayer, int indexWorker) {
        return currentState.setBoxReachable(indexPlayer, indexWorker);
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
        return currentState.movePlayer(indexPlayer, indexWorker, row, column, board);
    }

    /**
     * Method used to check if the player can build
     *
     * @param indexPlayer of the player
     * @param indexWorker of the worker moved before build
     * @return true if it's possible to  build
     */
    public boolean canBuild(int indexPlayer, int indexWorker) {
        return currentState.canBuild(indexPlayer, indexWorker);
    }

    /**
     * Method used to set the possible buildable boxes
     *
     * @param indexPlayer of the player
     * @param indexWorker of the worker moved before build
     * @return true if is reachable
     */
    public boolean setBoxBuilding(int indexPlayer, int indexWorker) {
        return currentState.setBoxBuilding(indexPlayer, indexWorker);
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
        return currentState.buildBlock(indexPlayer, indexWorker, row, column, board);
    }

    /**
     * Method to set the possible block buildable
     *
     * @param indexPlayer        of the player
     * @param indexPossibleBlock of the block to build
     */
    public void setIndexPossibleBlock(int indexPlayer, int indexPossibleBlock) {
        currentState.setIndexPossibleBlock(indexPlayer, indexPossibleBlock);
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
        return currentState.checkWin(indexPlayer, startBox, indexWorker);
    }

    /**
     * Method to check the victory after build
     *
     * @return true if the player has won
     */
    public boolean checkWinAfterBuild() {
        return currentState.checkWinAfterBuild();
    }

    public int getWinner() {
        return currentState.getWinner();
    }

    /**
     * Method to set a dead player
     *
     * @param indexPlayer of the player
     */
    public void setDeadPlayer(int indexPlayer) {
        currentState.setDeadPlayer(indexPlayer);
    }

    /**
     * Method to set the state in Dead
     *
     * @param winner is the index of the winner player
     */
    public void goEnd(int winner) {
        currentState = end;
        currentState.setWinner(winner);
    }
}
