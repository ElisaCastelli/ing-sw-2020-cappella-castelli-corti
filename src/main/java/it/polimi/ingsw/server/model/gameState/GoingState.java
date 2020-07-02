package it.polimi.ingsw.server.model.gameState;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Player;

import java.util.ArrayList;

/**
 * State used if the game is going
 */
public class GoingState extends GameState {
    /**
     * Players array
     */
    private ArrayList<Player> players;

    /**
     * Players dead array
     */
    private ArrayList<Player> playersDead;

    /**
     * Object GameStateManager to manage the changes of the state
     */
    private final GameStateManager manager;

    /**
     * Constructor of the class
     *
     * @param players     array of player
     * @param playersDead array of player dead
     * @param manager     game state
     */
    public GoingState(ArrayList<Player> players, ArrayList<Player> playersDead, GameStateManager manager) {
        this.players = players;
        this.playersDead = playersDead;
        this.manager = manager;
    }

    /**
     * Players dead getter
     *
     * @return array of players
     */
    public ArrayList<Player> getPlayersDead() {
        return playersDead;
    }

    /**
     * Method used to check if the player can build before move
     *
     * @param indexPlayer of the player
     * @return true if it's possible to build before move
     */
    @Override
    public boolean canBuildBeforeWorkerMove(int indexPlayer) {
        return players.get(indexPlayer).canBuildBeforeWorkerMove();
    }

    /**
     * Method used to check if the player can move
     *
     * @param indexPlayer of the player
     * @return true if it's possible to  move
     */
    @Override
    public boolean canMove(int indexPlayer) {
        return players.get(indexPlayer).checkWorkers();
    }

    /**
     * Method used to check if the player can  move inn special turns
     *
     * @param indexPlayer of the player
     * @param indexWorker of the worker to move
     * @return true if it's possible to  move
     */
    @Override
    public boolean canMoveSpecialTurn(int indexPlayer, int indexWorker) {
        return players.get(indexPlayer).checkWorker(indexWorker - 1);
    }

    /**
     * Method used to set the possible reachable boxes
     *
     * @param indexPlayer of the player
     * @param indexWorker of the worker to move
     */
    @Override
    public boolean setBoxReachable(int indexPlayer, int indexWorker) {
        return players.get(indexPlayer).setPossibleMove(indexWorker - 1);
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
    @Override
    public boolean movePlayer(int indexPlayer, int indexWorker, int row, int column, Board board) {
        Box starterBox = players.get(indexPlayer).getWorkerBox(indexWorker - 1);
        boolean movedPlayer = players.get(indexPlayer).playWorker(indexWorker - 1, board.getBox(row, column));
        starterBox.clearBoxesNextTo();
        return movedPlayer;
    }

    /**
     * Method used to check if the player can build
     *
     * @param indexPlayer of the player
     * @param indexWorker of the worker moved before build
     * @return true if it's possible to  build
     */
    @Override
    public boolean canBuild(int indexPlayer, int indexWorker) {
        return players.get(indexPlayer).checkBuilding(indexWorker - 1);
    }

    /**
     * Method used to set the possible buildable boxes
     *
     * @param indexPlayer of the player
     * @param indexWorker of the worker moved before build
     * @return true if is reachable
     */
    @Override
    public boolean setBoxBuilding(int indexPlayer, int indexWorker) {
        return players.get(indexPlayer).setPossibleBuild(indexWorker - 1);
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
    @Override
    public boolean buildBlock(int indexPlayer, int indexWorker, int row, int column, Board board) {
        Box starterBox = players.get(indexPlayer).getWorkerBox(indexWorker - 1);
        boolean movedBlock = players.get(indexPlayer).playBlock(board.getBox(row, column));
        starterBox.clearBoxesNextTo();
        starterBox.setReachable(false);
        return movedBlock;
    }

    /**
     * Method to set the possible block buildable
     *
     * @param indexPlayer        of the player
     * @param indexPossibleBlock of the block to build
     */
    @Override
    public void setIndexPossibleBlock(int indexPlayer, int indexPossibleBlock) {
        players.get(indexPlayer).setIndexPossibleBlock(indexPossibleBlock);
    }

    /**
     * Method to check the victory
     *
     * @param indexPlayer of the player
     * @param startBox    starting position
     * @param indexWorker worker moved
     * @return true if the player has won
     */
    @Override
    public boolean checkWin(int indexPlayer, Box startBox, int indexWorker) {
        boolean win = players.get(indexPlayer).checkWin(startBox, players.get(indexPlayer).getWorkerBox(indexWorker - 1));
        if (win) {
            int indexClient = players.get(indexPlayer).getIndexClient();
            manager.goEnd(indexClient);
        }
        return win;
    }

    /**
     * Method to check the victory after build
     *
     * @return true if the player has won
     */
    @Override
    public boolean checkWinAfterBuild() {
        boolean win = false;
        for (Player player : players) {
            player.setGod(player.getGod());
            win = player.checkWin(player.getWorkerBox(0), player.getWorkerBox(0));
            if (win) {
                manager.goEnd(player.getIndexClient());
            }
        }
        return win;
    }

    /**
     * Method to set a dead player
     *
     * @param indexPlayer of the player
     */
    @Override
    public void setDeadPlayer(int indexPlayer) {
        players.get(indexPlayer).goDead();
        players.get(indexPlayer).clearWorkers();
        playersDead.add(players.get(indexPlayer));
    }
}
