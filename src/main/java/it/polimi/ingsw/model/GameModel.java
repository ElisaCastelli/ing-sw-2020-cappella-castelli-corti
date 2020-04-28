package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameComponents.Board;
import it.polimi.ingsw.model.gameComponents.Box;
import it.polimi.ingsw.model.gameComponents.Player;
import it.polimi.ingsw.model.god.God;

import java.util.ArrayList;

public interface GameModel {

    void setNPlayers(int nPlayers);
    void addPlayer(String name, int age, Game.COLOR color);
    ArrayList<God> getCards()throws Exception;
    void chooseCard(int playerIndex, int godCard)throws Exception ;
    boolean initializeWorker(int indexPlayer, int indexWorker, Box box);
    void startTurn(int indexPlayer);
    boolean canMove(int indexPlayer);
    void setBoxReachable(int indexPlayer, int indexWorker);
    boolean movePlayer(int indexPlayer, int indexWorker, int row, int column);
    boolean canBuild(int indexPlayer, int indexWorker);
    void setBoxBuilding(int indexPlayer, int indexWorker);
    boolean buildBlock(int indexPlayer, int indexWorker, int row, int column);
    void finishTurn(int indexPlayer);
    boolean checkWin(int indexPlayer, Box startBox, int indexWorker);
    boolean checkWinAfterBuild();
    void setWinningPlayer(int indexPlayer);
    void setDeadPlayer(int indexPlayer);
    void setPause();
}
