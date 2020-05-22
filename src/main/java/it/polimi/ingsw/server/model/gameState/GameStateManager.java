package it.polimi.ingsw.server.model.gameState;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Player;

import java.util.ArrayList;

public class GameStateManager {
    private final GameState going;
    private final GameState pause;
    //private final GameState ready;
    private final GameState end;
    private GameState currentState;

    public GameStateManager(ArrayList<Player> players, ArrayList<Player> playersDead) {
        going = new GoingState(players, playersDead, this);
        pause = new PauseState();
        //ready = new ReadyState();
        end = new EndState();
        currentState = null;
    }
    public GameState getCurrentState(){
        return currentState;
    }
    public void start(){
        currentState = going;
    }

    public void startTurn(int indexPlayer){
        currentState.startTurn(indexPlayer);
    }

    public boolean canMove(int indexPlayer){
        return currentState.canMove(indexPlayer);
    }

    public void setBoxReachable(int indexPlayer, int indexWorker){
        currentState.setBoxReachable(indexPlayer, indexWorker);
    }
    public boolean movePlayer(int indexPlayer, int indexWorker, int row, int column, Board board){
        return currentState.movePlayer(indexPlayer, indexWorker, row, column, board);
    }
    public boolean canBuild(int indexPlayer, int indexWorker){
        return currentState.canBuild(indexPlayer, indexWorker);
    }
    public void setBoxBuilding(int indexPlayer, int indexWorker){
        currentState.setBoxBuilding(indexPlayer, indexWorker);
    }
    public boolean buildBlock(int indexPlayer, int indexWorker, int row, int column, Board board){
        return currentState.buildBlock(indexPlayer, indexWorker, row, column, board);
    }
    public void finishTurn(int indexPlayer){
        currentState.finishTurn(indexPlayer);
    }
    public boolean checkWin(int indexPlayer, Box startBox, int indexWorker){
        return currentState.checkWin(indexPlayer, startBox, indexWorker);
    }
    public boolean checkWinAfterBuild(){
        return currentState.checkWinAfterBuild();
    }

    public int getWinner(){
        return currentState.getWinner();
    }

    public void setDeadPlayer(int indexPlayer){
       currentState.setDeadPlayer(indexPlayer);
    }

    public void goEnd(int winner){
        currentState=end;
        currentState.setWinner(winner);
    }
    public void goGoing(){
        currentState=going;
    }
    public void goPause(){
        currentState=pause;
    }
}
