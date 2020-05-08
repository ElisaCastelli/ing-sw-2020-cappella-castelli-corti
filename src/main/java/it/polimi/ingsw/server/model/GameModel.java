package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameState.GameState;
import it.polimi.ingsw.server.model.god.God;
import it.polimi.ingsw.server.model.playerState.PlayerState;

import java.util.ArrayList;

public interface GameModel {
    Board getBoard();
    int getNPlayers();
    void setNPlayers(int nPlayers);
    void addPlayer(String name, int age);
    ArrayList<God> getCards()throws Exception;
    God getPlayerCard(int indexPlayer);
    ArrayList<God> getTempCard();
    ArrayList<God> getCardUsed();
    void chooseThreeCard(ArrayList<God> threeCard);
    void chooseCard(int playerIndex, int godCard)throws Exception ;
    boolean initializeWorker(int indexPlayer, int indexWorker, Box box);
    GameState getState();
    PlayerState getPlayerState(int indexPlayer);
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
